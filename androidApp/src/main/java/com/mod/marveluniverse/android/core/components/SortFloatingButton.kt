package com.mod.marveluniverse.android.core.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mod.marveluniverse.android.R
import com.mod.marveluniverse.domain.entites.Sort

@Composable
fun SortFloatingButton(
    sort: Sort,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier.size(60.dp),
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        onClick = onClick) {
        val resourceId =
            if (sort == Sort.ASCENDING) R.drawable.sort_asc else R.drawable.sort_des
        Icon(
            painter = painterResource(id = resourceId),
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(30.dp)
        )
    }
}