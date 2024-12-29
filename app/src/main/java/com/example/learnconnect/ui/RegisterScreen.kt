package com.example.learnconnect.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.learnconnect.R
import com.example.learnconnect.viewModels.RegisterViewModel

@Composable
fun RegisterScreen(onNavigateToLogin: () -> Unit,viewModel: RegisterViewModel = hiltViewModel() ) {


    Scaffold(
        topBar = { RegisterTopBar(onNavigateToLogin) },
        bottomBar = {},
        content = { paddingValues ->
            RegisterContent(
                onNavigateToLogin = onNavigateToLogin,
                viewModel = viewModel,
                modifier = Modifier.padding(paddingValues) // paddingValues ile içeriği düzenler
            )
        }
    )

}
@Composable
fun RegisterContent(onNavigateToLogin: () -> Unit,viewModel:RegisterViewModel, modifier: Modifier = Modifier,
)
{
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var buttonColor = MaterialTheme.colorScheme.secondary
    var clickable = false
    var isPasswordVisible by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.fillMaxHeight().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(100.dp))

                    OutlinedTextField(
                        value = username,
                        onValueChange = { newUsername ->
                            username = newUsername
                            viewModel.onUsernameChange(username)
                        },
                        label = { Text("Full Name", color = MaterialTheme.colorScheme.onSurface) },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                    )
            Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = email,
                        onValueChange = { newEmail ->
                            email = newEmail
                            viewModel.onEmailChange(newEmail)
                        },
                        label = { Text("Email", color = MaterialTheme.colorScheme.onSurface) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
            Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { newPassword ->
                            password = newPassword
                            viewModel.onPasswordChange(newPassword)
                        },
                        label = { Text("Password", color = MaterialTheme.colorScheme.onSurface) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp),
                        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(
                                onClick = { isPasswordVisible = !isPasswordVisible }
                            ) {
                                Icon(
                                    painter = painterResource(id = if (isPasswordVisible) R.drawable.open_eye else R.drawable.closed_eye),
                                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password"
                                )
                            }
                        }
                    )

            Spacer(modifier = Modifier.height(16.dp))
            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                buttonColor = MaterialTheme.colorScheme.primary
                clickable = true
            } else {
                buttonColor = MaterialTheme.colorScheme.secondary
                clickable = false
            }
            Button(
                onClick = {
                    if (clickable) {
                        viewModel.register(
                            username=username,
                            email=email,
                            password=password,
                            onSuccess= { onNavigateToLogin() },
                            onError= {  errorMessage = "User's email address is already exists."
                                showErrorDialog = true }

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
                    text = "REGISTER",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "By registering you agree to ", color = MaterialTheme.colorScheme.onBackground)
            Text(
                text = "Terms & Conditions",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = "and", color = MaterialTheme.colorScheme.onBackground)
            Text(
                text = "Privacy Policy",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(text = "of the Learn Connect", color = MaterialTheme.colorScheme.onBackground)
        }
    if (showErrorDialog) {
        RegisterErrorDialog(errorMessage = errorMessage) {
            showErrorDialog = false
        }
    }
}
@Composable
fun RegisterErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTopBar(onNavigateToProfile: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        title = {
            Text(
                text = "Register to Learn Connect",
                color = MaterialTheme.colorScheme.onSecondary)
        },
        navigationIcon = {
            IconButton(onClick = { onNavigateToProfile() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(60.dp)
                )
            }
        },
        actions = {}
    )
}
