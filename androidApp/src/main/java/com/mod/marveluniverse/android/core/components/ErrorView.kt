package com.mod.marveluniverse.android.core.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.mod.marveluniverse.android.R

@Composable
fun ErrorView(
    modifier: Modifier = Modifier,
    errorMessage: String? = null,
) {
    val message = errorMessage ?: stringResource(R.string.error_message)

    Box(modifier = modifier) {
        Text(
            text = message,
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}