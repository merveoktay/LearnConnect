package com.example.learnconnect.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnconnect.R
import com.example.learnconnect.ViewModels.RegisterState
import com.example.learnconnect.ViewModels.RegisterViewModel

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    val registerState by viewModel.registerState.observeAsState()
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var buttonColor= colorResource(id = R.color.hint_color)
    var clickable=false
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.surface_color))
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Next",
            tint = colorResource(id = R.color.title_color),
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.TopStart)
                .padding(top = 15.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Register to Learn Connect",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.title_color)
                ),
                modifier = Modifier
                    .align(Alignment.Start)

            )
            Spacer(modifier = Modifier.height(100.dp))

            OutlinedTextField(
                value = username,
                onValueChange = {username=it},
                label = { Text("Full Name", color = colorResource(id = R.color.hint_color)) },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email=it},
                label = { Text("Email", color = colorResource(id = R.color.hint_color)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {password=it},
                label = { Text("Password", color = colorResource(id = R.color.hint_color)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {confirmPassword=it},
                label = { Text("Confirm Password", color = colorResource(id = R.color.hint_color)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp) ,
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(75.dp))
            if(username!=null &&email!=null && password!=null) {
                buttonColor= colorResource(id = R.color.brand_color)
                clickable=true
            }
            else{
                buttonColor= colorResource(id = R.color.hint_color)
                clickable=false
            }
            Button(
                onClick = { if(clickable){} },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text(
                    text = "REGISTER",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "By registering you agree to ", color = Color(0xFF888888))
            Text(
                text = "Terms & Conditions",
                style = TextStyle(
                    color = Color(0xFFE53935),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = "and", color = Color(0xFF888888))
            Text(
                text = "Privacy Policy",
                style = TextStyle(
                    color = Color(0xFFE53935),
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = "of the Learn Connect", color = Color(0xFF888888))


        }
        when (registerState) {
            is RegisterState.Loading -> CircularProgressIndicator()
            is RegisterState.Success -> Text("Registration successful!", color = Color.Green)
            is RegisterState.Error -> Text(
                (registerState as RegisterState.Error).message,
                color = Color.Red
            )
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    //RegisterScreen()
}