package com.mod.marveluniverse.android.character.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mod.marveluniverse.android.R
import com.mod.marveluniverse.android.core.components.AppDrawer
import com.mod.marveluniverse.android.core.components.Footer
import com.mod.marveluniverse.android.core.components.ListScreenTopAppBar
import com.mod.marveluniverse.presentation.character.list.CharacterListEvent
import kotlinx.coroutines.launch

@Composable
fun CharacterListScreen(
    onEvent: (CharacterListEvent) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ListScreenTopAppBar(
                title = stringResource(R.string.characters),
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
                onEvent(CharacterListEvent.DrawerItemClicked(it))
            }
        },
        bottomBar = {
            Footer(modifier = Modifier.fillMaxWidth())
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)) {

        }
    }
}