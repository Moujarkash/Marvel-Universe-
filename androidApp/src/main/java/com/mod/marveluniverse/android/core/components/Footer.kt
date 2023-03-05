package com.mod.marveluniverse.android.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mod.marveluniverse.android.R

@Composable
fun Footer(
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.height(20.dp)) {
        Text(
            text = stringResource(R.string.copyright_message),
            style = MaterialTheme.typography.caption.copy(fontSize = 12.sp),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}