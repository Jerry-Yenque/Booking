package com.froxengine.booking.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.borderDebug( width: Dp): Modifier {
    return this.then(
        Modifier.border(BorderStroke(width = width, color = Color.Blue))
    )
}