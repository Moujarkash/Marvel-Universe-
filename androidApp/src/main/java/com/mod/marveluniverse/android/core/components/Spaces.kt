package com.mod.marveluniverse.android.core.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpace(modifier: Modifier = Modifier, dp: Dp = 8.dp) {
    Spacer(modifier = modifier.height(dp))
}

@Composable
fun HorizontalSpace(modifier: Modifier = Modifier, dp: Dp = 8.dp) {
    Spacer(modifier = Modifier.width(dp))
}
