package com.example.learnconnect.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.learnconnect.R

@Composable
fun PlayIconWithCircle() {
    val context = LocalContext.current

    val brandColor = remember {
        Color(ContextCompat.getColor(context, R.color.brand_color))
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(64.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                color = brandColor.copy(alpha = 0.7f),
                style = Stroke(width = 4.dp.toPx())
            )
        }

        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Play Icon",
            tint = brandColor.copy(alpha = 0.7f),
            modifier = Modifier.size(48.dp)
        )
    }
}