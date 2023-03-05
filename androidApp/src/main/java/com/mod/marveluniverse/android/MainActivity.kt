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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mod.marveluniverse.android.comic.list.AndroidComicListViewModel
import com.mod.marveluniverse.android.comic.list.ComicListScreen
import com.mod.marveluniverse.android.core.Routes
import com.mod.marveluniverse.presentation.comic.list.ComicListEvent
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

            ComicListScreen(state = state, onEvent = viewModel::onEvent)
        }
    }
}