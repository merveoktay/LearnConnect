package com.example.learnconnect.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.learnconnect.R

@Composable
fun DownloadIcon() {
    val context = LocalContext.current

    val brandColor = remember {
        Color(ContextCompat.getColor(context, R.color.brand_color))
    }
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = "Download",
            tint = brandColor.copy(alpha = 0.7f),
            modifier = Modifier.size(48.dp)
        )

    }
}