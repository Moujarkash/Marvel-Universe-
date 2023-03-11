package com.mod.marveluniverse.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mod.marveluniverse.android.character.list.AndroidCharacterListViewModel
import com.mod.marveluniverse.android.character.list.CharacterListScreen
import com.mod.marveluniverse.android.comic.list.AndroidComicListViewModel
import com.mod.marveluniverse.android.comic.list.ComicListScreen
import com.mod.marveluniverse.android.core.Routes
import com.mod.marveluniverse.android.creator.list.AndroidCreatorListViewModel
import com.mod.marveluniverse.android.creator.list.CreatorListScreen
import com.mod.marveluniverse.android.event.list.AndroidEventListViewModel
import com.mod.marveluniverse.android.event.list.EventListScreen
import com.mod.marveluniverse.android.series.list.AndroidSeriesListViewModel
import com.mod.marveluniverse.android.series.list.SeriesListScreen
import com.mod.marveluniverse.android.story.list.AndroidStoryListViewModel
import com.mod.marveluniverse.android.story.list.StoryListScreen
import com.mod.marveluniverse.domain.entites.ResourceType
import com.mod.marveluniverse.presentation.character.list.CharacterListEvent
import com.mod.marveluniverse.presentation.comic.list.ComicListEvent
import com.mod.marveluniverse.presentation.creator.list.CreatorListEvent
import com.mod.marveluniverse.presentation.event.list.EventListEvent
import com.mod.marveluniverse.presentation.series.list.SeriesListEvent
import com.mod.marveluniverse.presentation.story.list.StoryListEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RootApp()
                }
            }
        }
    }
}

@Composable
fun RootApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.COMIC_LIST
    ) {
        composable(route = Routes.COMIC_LIST) {
            val viewModel = hiltViewModel<AndroidComicListViewModel>()
            val state by viewModel.state.collectAsState()

            ComicListScreen(state = state, onEvent = { event ->
                when (event) {
                    is ComicListEvent.DrawerItemClicked -> navigateToResourceScreen(navController, event.resourceType)
                    else -> viewModel.onEvent(event)
                }
            })
        }

        composable(route = Routes.CHARACTER_LIST) {
            val viewModel = hiltViewModel<AndroidCharacterListViewModel>()
            val state by viewModel.state.collectAsState()

            CharacterListScreen(state = state, onEvent = { event ->
                when (event) {
                    is CharacterListEvent.DrawerItemClicked -> navigateToResourceScreen(navController, event.resourceType)
                    else -> viewModel.onEvent(event)
                }
            })
        }

        composable(route = Routes.CREATOR_LIST) {
            val viewModel = hiltViewModel<AndroidCreatorListViewModel>()
            val state by viewModel.state.collectAsState()

            CreatorListScreen(state = state, onEvent = { event ->
                when (event) {
                    is CreatorListEvent.DrawerItemClicked -> navigateToResourceScreen(navController, event.resourceType)
                    else -> viewModel.onEvent(event)
                }
            })
        }

        composable(route = Routes.EVENT_LIST) {
            val viewModel = hiltViewModel<AndroidEventListViewModel>()
            val state by viewModel.state.collectAsState()

            EventListScreen(state = state, onEvent = { event ->
                when (event) {
                    is EventListEvent.DrawerItemClicked -> navigateToResourceScreen(navController, event.resourceType)
                    else -> viewModel.onEvent(event)
                }
            })
        }

        composable(route = Routes.SERIES_LIST) {
            val viewModel = hiltViewModel<AndroidSeriesListViewModel>()
            val state by viewModel.state.collectAsState()

            SeriesListScreen(state = state, onEvent = { event ->
                when (event) {
                    is SeriesListEvent.DrawerItemClicked -> navigateToResourceScreen(navController, event.resourceType)
                    else -> viewModel.onEvent(event)
                }
            })
        }

        composable(route = Routes.STORY_LIST) {
            val viewModel = hiltViewModel<AndroidStoryListViewModel>()
            val state by viewModel.state.collectAsState()

            StoryListScreen(state = state, onEvent = { event ->
                when (event) {
                    is StoryListEvent.DrawerItemClicked -> navigateToResourceScreen(navController, event.resourceType)
                    else -> viewModel.onEvent(event)
                }
            })
        }
    }
}

private fun navigateToResourceScreen(navController: NavController, resourceType: ResourceType) {
    when (resourceType) {
        ResourceType.CHARACTER -> navController.navigate(Routes.CHARACTER_LIST) {
            popUpTo(0)
        }
        ResourceType.COMIC -> navController.navigate(Routes.COMIC_LIST) {
            popUpTo(0)
        }
        ResourceType.CREATOR -> navController.navigate(Routes.CREATOR_LIST) {
            popUpTo(0)
        }
        ResourceType.EVENT -> navController.navigate(Routes.EVENT_LIST) {
            popUpTo(0)
        }
        ResourceType.SERIES -> navController.navigate(Routes.SERIES_LIST) {
            popUpTo(0)
        }
        ResourceType.STORY -> navController.navigate(Routes.STORY_LIST) {
            popUpTo(0)
        }
    }
}