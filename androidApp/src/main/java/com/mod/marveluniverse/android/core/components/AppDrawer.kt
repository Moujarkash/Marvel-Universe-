@file:OptIn(ExperimentalMaterialApi::class)

package com.mod.marveluniverse.android.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mod.marveluniverse.android.R
import com.mod.marveluniverse.domain.entites.ResourceType

@Composable
fun AppDrawer(
    modifier: Modifier = Modifier,
    onItemClick: (ResourceType) -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.marvel_header),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )
        ListItem(
            modifier = Modifier
                .clickable {
                    onItemClick(ResourceType.COMIC)
                },
            text = { Text(text = stringResource(id = R.string.comics)) }
        )
        Divider()
        ListItem(
            modifier = Modifier
                .clickable {
                    onItemClick(ResourceType.CHARACTER)
                },
            text = { Text(text = stringResource(R.string.characters)) }
        )
        Divider()
        ListItem(
            modifier = Modifier
                .clickable {
                    onItemClick(ResourceType.CREATOR)
                },
            text = { Text(text = stringResource(R.string.creators)) }
        )
        Divider()
        ListItem(
            modifier = Modifier
                .clickable {
                    onItemClick(ResourceType.EVENT)
                },
            text = { Text(text = stringResource(R.string.events)) }
        )
        Divider()
        ListItem(
            modifier = Modifier
                .clickable {
                    onItemClick(ResourceType.SERIES)
                },
            text = { Text(text = stringResource(R.string.series)) }
        )
        Divider()
        ListItem(
            modifier = Modifier
                .clickable {
                    onItemClick(ResourceType.STORY)
                },
            text = { Text(text = stringResource(R.string.stories)) }
        )
    }
}