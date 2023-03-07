@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterialApi::class
)

package com.mod.marveluniverse.android.comic.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.mod.marveluniverse.android.R
import com.mod.marveluniverse.android.comic.components.ComicListItem
import com.mod.marveluniverse.android.core.components.*
import com.mod.marveluniverse.presentation.comic.list.ComicListEvent
import com.mod.marveluniverse.presentation.comic.list.ComicListState
import kotlinx.coroutines.launch

@Composable
fun ComicListScreen(
    state: ComicListState,
    onEvent: (ComicListEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        state.isRefreshing,
        { onEvent(ComicListEvent.RequestComics(isRefresh = true)) })
    val searchTextState = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListScreenTopAppBar(
                title = stringResource(R.string.comics),
                onNavigationClicked = {
                    scope.launch {
                        scaffoldState.drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            )
        },
        drawerContent = {
            AppDrawer {
                onEvent(ComicListEvent.DrawerItemClicked(it))
            }
        },
        bottomBar = {
            Footer(modifier = Modifier.fillMaxWidth())
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                SearchView(
                    state = searchTextState,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    onSearch = {
                        if (searchTextState.value.text == state.query) return@SearchView
                        if (searchTextState.value.text.isEmpty()) {
                            onEvent(ComicListEvent.SearchTextCleared)
                        }

                        onEvent(ComicListEvent.SearchComics(query = searchTextState.value.text))
                    },
                    onClear = {
                        if (state.query == null) return@SearchView
                        onEvent(ComicListEvent.SearchTextCleared)
                    }
                )
                if (state.comics.isEmpty()) {
                    if (state.isFetchingComics) {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    }

                    if (state.error != null) {
                        ErrorView(modifier = Modifier.fillMaxSize())
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState),
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
                }
            }
            PullRefreshIndicator(
                state.isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}