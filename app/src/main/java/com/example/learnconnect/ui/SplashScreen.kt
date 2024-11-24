package com.example.learnconnect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnconnect.R

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.surface_color))
    ) {
        // Logo Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                Image(
                    painter = painterResource(id= R.drawable.brand_logo_light_big),
                    contentDescription = "Logo",
                    modifier = Modifier.size(90.dp),
                    contentScale = ContentScale.Fit
                )

            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Learn Connect",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.title_color)
                )
            )

        }
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Next",
            tint = colorResource(id = R.color.title_color),
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
                .padding(15.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}