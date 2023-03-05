@file:OptIn(ExperimentalMaterialApi::class)

package com.mod.marveluniverse.android.comic.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mod.marveluniverse.android.R
import com.mod.marveluniverse.android.comic.components.ComicListItem
import com.mod.marveluniverse.android.core.components.ErrorView
import com.mod.marveluniverse.android.core.components.Footer
import com.mod.marveluniverse.android.core.components.LoadingView
import com.mod.marveluniverse.presentation.comic.list.ComicListEvent
import com.mod.marveluniverse.presentation.comic.list.ComicListState

@Composable
fun ComicListScreen(
    state: ComicListState,
    onEvent: (ComicListEvent) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        state.isRefreshing,
        { onEvent(ComicListEvent.RequestComics(isRefresh = true)) })

    LaunchedEffect(Unit) {
        if (state.comics.isEmpty()) {
            onEvent(ComicListEvent.RequestComics())
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.comics)) }
            )
        },
        bottomBar = {
            Footer(modifier = Modifier.fillMaxWidth())
        }
    ) { paddingValues ->
        if (state.comics.isEmpty()) {
            if (state.isFetchingComics) {
                LoadingView(modifier = Modifier.fillMaxSize())
            }

            if (state.error != null) {
                ErrorView(modifier = Modifier.fillMaxSize())
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    itemsIndexed(state.comics) { index, comic ->
                        ComicListItem(
                            comic = comic
                        )

                        if (index >= state.comics.size - 1
                            && !state.isFetchingComics
                        ) {
                            onEvent(ComicListEvent.RequestComics())
                        }
                    }

                    if (state.isFetchingComics) {
                        item {
                            LoadingView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                        }
                    }

                    if (state.error != null) {
                        item {
                            ErrorView(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(80.dp)
                            )
                        }

                    }
                }

                PullRefreshIndicator(state.isRefreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
            }

        }
    }
}