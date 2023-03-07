package com.mod.marveluniverse.android.core.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.mod.marveluniverse.android.R

@Composable
fun ListScreenTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onNavigationClicked: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onNavigationClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_drawer),
                    contentDescription = null
                )
            }
        }
    )
}