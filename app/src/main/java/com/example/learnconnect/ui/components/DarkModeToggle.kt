package com.example.learnconnect.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnconnect.R

@SuppressLint("UseCompatLoadingForDrawables")
@Composable
fun DarkModeToggle(  isDarkTheme: Boolean,
                           changeAppTheme: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Dark Mode",
            modifier = Modifier.padding(bottom = 16.dp),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.width(20.dp))
        Switch(
            checked = isDarkTheme,
            onCheckedChange = {changeAppTheme()  },
            modifier = Modifier.padding(bottom = 16.dp),thumbContent = {
                Icon(
                    painter = if (isDarkTheme) painterResource(id = R.drawable.moon_icon) else painterResource(id = R.drawable.sun_icon) ,
                    contentDescription = if (isDarkTheme) "Dark Theme" else "Light Theme",
                    modifier = Modifier.size(24.dp)
                )
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = colorResource(id = R.color.hint_color),
                uncheckedThumbColor = colorResource(id = R.color.brand_color)
            )
        )
    }

}