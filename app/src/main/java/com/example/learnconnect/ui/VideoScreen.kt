package com.example.learnconnect.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun  VideoScreen() {
    Surface(
    modifier = Modifier
    .clickable { }
    .wrapContentSize(),
    color = Color.LightGray,
    shape = RoundedCornerShape(16.dp)
    ){Text(text = "This is the video screen.")}
}