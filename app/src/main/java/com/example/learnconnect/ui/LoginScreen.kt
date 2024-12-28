package com.example.learnconnect.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learnconnect.utils.PreferencesManager
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
    var buttonColor = MaterialTheme.colorScheme.primary
    var clickable = false
    var isPasswordVisible by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val userId by viewModel.id.collectAsState()
    val context =LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.brand_logo_light_small),
                contentDescription = "Logo",
                modifier = Modifier.size(60.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Learn Connect",
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { newEmail ->
                    email = newEmail
                    viewModel.onEmailChange(newEmail)
                },
                label = { Text("Email", color = MaterialTheme.colorScheme.secondary) },
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
                label = { Text("Password", color = MaterialTheme.colorScheme.secondary) },
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
                buttonColor = MaterialTheme.colorScheme.primary
                clickable = true
            } else {
                buttonColor = MaterialTheme.colorScheme.secondary
                clickable = false
            }

            Button(
                onClick = {
                    if (clickable) {
                        viewModel.login(
                            onSuccess = {
                                onNavigateToHome()
                            },
                            onError = {
                                errorMessage = "User's email address or password is incorrect."
                                showErrorDialog = true
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = MaterialTheme.colorScheme.onBackground
                )
                TextButton(onClick = { onNavigateToRegister() }) {
                    Text(
                        text = "Register Now",
                        style = TextStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }

    if (showErrorDialog) {
        ErrorDialog(errorMessage = errorMessage) {
            showErrorDialog = false
        }
    }
    if(viewModel.isResultTrue()){
        Log.d("RESULT DEGERI", viewModel.isResultTrue().toString())
        PreferencesManager.saveUserId(context = context, userId = userId)
    }


}

@Composable
fun ErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
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
