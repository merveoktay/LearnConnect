package com.example.learnconnect.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun CourseCards(
    imageUrl: String,
    courseName: String,
    courseId: Int,
    onNavigateToCourse: (Int) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onNavigateToCourse(courseId)
            }
    ) {
        Column {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = courseName,
                modifier = Modifier
                    .padding(start = 20.dp, top = 8.dp, bottom = 8.dp, end = 10.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
