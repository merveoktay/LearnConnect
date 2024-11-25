package com.example.learnconnect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learnconnect.R
import com.example.learnconnect.ViewModels.LoginState
import com.example.learnconnect.ViewModels.LoginViewModel

@Composable
fun LoginScreen(viewModel: LoginViewModel) {
    val loginState by viewModel.loginState.observeAsState()
    var email by remember{mutableStateOf("")}
    var password by remember { mutableStateOf("")   }
    var  buttonColor= colorResource(id = R.color.hint_color)
    var clickable=false

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.surface_color)) // Arka plan rengi
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

                Image(
                    painter = painterResource(id = R.drawable.brand_logo_light_small), // Logonuzun kaynak dosyası
                    contentDescription = "Logo",
                    modifier = Modifier.size(60.dp)
                )

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = "Learn Connect",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.title_color)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = {email=it},
                label = { Text("Email", color = colorResource(id = R.color.hint_color)) },
                modifier = Modifier
                    .fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {password=it},
                label = { Text("Password", color = colorResource(id = R.color.hint_color)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "Forgot Password?",
                    style = TextStyle(
                        color = Color(0xFFE53935),
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.clickable { /* Forgot password işlemi */ }
                )
            }

            Spacer(modifier = Modifier.height(50.dp))
            if(email!=null && password!=null) {
                buttonColor= colorResource(id = R.color.brand_color)
                clickable=true
            }
            else{
                buttonColor= colorResource(id = R.color.hint_color)
                clickable=false
            }
            Button(
                onClick = { if(clickable){}},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = buttonColor)
            ) {
                Text(
                    text = "LOGIN",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Don't have an account? ", color = colorResource(id = R.color.title_color))
                Text(
                    text = "Register Now",
                    style = TextStyle(
                        color = colorResource(id = R.color.brand_color),
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        when(loginState){
            is LoginState.Loading-> CircularProgressIndicator()
            is LoginState.Success-> Text(text = "Login Successful",color= Color.Green)
            is LoginState.Error-> Text(text = (loginState as LoginState.Error).message, color = Color.Red)
            else ->{}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
   // LoginScreen()
}
