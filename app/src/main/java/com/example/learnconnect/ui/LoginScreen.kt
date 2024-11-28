package com.example.learnconnect.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learnconnect.R
import com.example.learnconnect.viewModels.LoginViewModel

@Composable
fun LoginScreen(
    onNavigateToRegister: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var buttonColor = colorResource(id = R.color.hint_color)
    var clickable = false
    var isPasswordVisible by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) } // State to control dialog visibility
    var errorMessage by remember { mutableStateOf("") } // State to hold error message

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
                onValueChange = { newEmail ->
                    email = newEmail
                    viewModel.onEmailChange(newEmail)
                },
                label = { Text("Email", color = colorResource(id = R.color.hint_color)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { newPassword ->
                    password = newPassword
                    viewModel.onPasswordChange(newPassword)
                },
                label = { Text("Password", color = colorResource(id = R.color.hint_color)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = if (isPasswordVisible) R.drawable.open_eye else R.drawable.closed_eye),
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(50.dp))
            if (email.isNotEmpty() && password.isNotEmpty()) {
                buttonColor = colorResource(id = R.color.brand_color)
                clickable = true
            } else {
                buttonColor = colorResource(id = R.color.hint_color)
                clickable = false
            }

            Button(
                onClick = {
                    if (clickable) {
                        viewModel.login(
                            onSuccess = {
                                onNavigateToHome()  // Ensure this is correctly navigating
                            },
                            onError = {
                                errorMessage = "User's email address or password is incorrect."
                                showErrorDialog = true // Show the error dialog
                            }
                        )
                    }
                },
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
                Text(
                    text = "Don't have an account? ",
                    color = colorResource(id = R.color.title_color)
                )
                TextButton(onClick = { onNavigateToRegister() }) {
                    Text(
                        text = "Register Now",
                        style = TextStyle(
                            color = colorResource(id = R.color.brand_color),
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }

    // Show error dialog if login fails
    if (showErrorDialog) {
        ErrorDialog(errorMessage = errorMessage) {
            showErrorDialog = false // Dismiss the dialog
        }
    }
}
@Composable
fun ErrorDialog( errorMessage: String,onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Login Failed")
        },
        text = {
            Text(text = errorMessage)
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "OK")
            }
        }
    )
}

