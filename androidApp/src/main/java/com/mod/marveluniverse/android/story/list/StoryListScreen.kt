@file:OptIn(
    ExperimentalMaterialApi::class, ExperimentalMaterialApi::class,
    ExperimentalMaterialApi::class
)

package com.mod.marveluniverse.android.story.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import com.mod.marveluniverse.android.story.components.StoryListItem
import com.mod.marveluniverse.android.core.components.*
import com.mod.marveluniverse.domain.entites.Sort
import com.mod.marveluniverse.presentation.story.list.StoryListEvent
import com.mod.marveluniverse.presentation.story.list.StoryListState
import kotlinx.coroutines.launch

@Composable
fun StoryListScreen(
    state: StoryListState,
    onEvent: (StoryListEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        state.isRefreshing,
        { onEvent(StoryListEvent.RefreshStories) })
    val searchTextState = remember { mutableStateOf(TextFieldValue("")) }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListScreenTopAppBar(
                title = stringResource(R.string.stories),
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
                onEvent(StoryListEvent.DrawerItemClicked(it))
            }
        },
        floatingActionButton = {
            if (state.isFetchingStories || state.stories.isEmpty()) return@Scaffold

            SortFloatingButton(
                state.sort
            ) {
                onEvent(StoryListEvent.SortStories(if (state.sort == Sort.ASCENDING) Sort.DESCENDING else Sort.ASCENDING))
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
                    enabled = !state.isRefreshing && !state.isFetchingStories,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    onSearch = {
                        if (searchTextState.value.text == state.query) return@SearchView
                        if (searchTextState.value.text.isEmpty()) {
                            onEvent(StoryListEvent.SearchTextCleared)
                        }

                        onEvent(StoryListEvent.SearchStories(query = searchTextState.value.text))
                    },
                    onClear = {
                        if (state.query == null) return@SearchView
                        onEvent(StoryListEvent.SearchTextCleared)
                    }
                )
                if (state.stories.isEmpty()) {
                    if (state.isFetchingStories) {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    } else if (state.error != null) {
                        ErrorView(modifier = Modifier.fillMaxSize())
                    } else {
                        EmptyView(modifier = Modifier.fillMaxSize())
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        itemsIndexed(state.stories) { index, story ->
                            StoryListItem(
                                story = story
                            )

                            if (index >= state.stories.size - 1
                                && !state.isFetchingStories
                            ) {
                                onEvent(StoryListEvent.RequestStories)
                            }
                        }

                        if (state.isFetchingStories) {
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