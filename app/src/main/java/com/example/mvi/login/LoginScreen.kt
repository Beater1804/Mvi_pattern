package com.example.mvi.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mvi.navigation.NavRoute
import com.example.mvi.util.extension.popUpToTop
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
internal fun LoginScreenHolder(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewmodel: LoginViewModel = viewModel()
) {
    val context = LocalContext.current

    val state by viewmodel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewmodel.singleEvent
            .onEach {
                when (it) {
                    LoginSingleEvent.LoginLoaded -> {
                        Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
            .collect()
    }

    LoginScreen(
        modifier = modifier,
        state = state,
        onUsernameChanged = { viewmodel.sendIntent(LoginIntent.UsernameChanged(it)) },
        onPasswordChanged = { viewmodel.sendIntent(LoginIntent.PasswordChanged(it)) },
        onRememberMeChanged = { viewmodel.sendIntent(LoginIntent.RememberChanged(it)) },
        onForgotPassword = { viewmodel.sendIntent(LoginIntent.OnForgotPassword) },
        onLogin = { username, password, rememberMe ->
            viewmodel.sendIntent(LoginIntent.OnLogin(username, password, rememberMe))
        },
        onSignUp = {
            navController.navigate(NavRoute.Signup.route) {
                popUpToTop(navController)
            }
        },
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: LoginState,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRememberMeChanged: (Boolean) -> Unit,
    onForgotPassword: () -> Unit,
    onLogin: (userName: String, password: String, rememberMe: Boolean) -> Unit,
    onSignUp: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when (state) {
            LoginState.Initial -> TODO()
            is LoginState.LoginForm -> LoginFormComponent(
                state = state,
                onUsernameChanged = onUsernameChanged,
                onPasswordChanged = onPasswordChanged,
                onRememberMeChanged = onRememberMeChanged,
                onForgotPassword = onForgotPassword,
                onLogin = onLogin,
                onSignUp = onSignUp
            )
        }
    }
}

@Composable
fun LoginFormComponent(
    state: LoginState.LoginForm,
    onUsernameChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onRememberMeChanged: (Boolean) -> Unit,
    onLogin: (userName: String, password: String, rememberMe: Boolean) -> Unit,
    onForgotPassword: () -> Unit,
    onSignUp: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BasicTextField(
            value = state.userEntity.username,
            onValueChange = { onUsernameChanged(it) },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.White, RectangleShape)
                        .padding(PaddingValues(8.dp))
                ) {
                    if (state.userEntity.username.isEmpty()) {
                        Text(
                            text = "Username",
                            style = TextStyle.Default.copy(color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(
            value = state.userEntity.password,
            onValueChange = { onPasswordChanged(it) },
            maxLines = 1,
            singleLine = true,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .border(2.dp, Color.Black, RoundedCornerShape(8.dp)),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .background(Color.White, RectangleShape)
                        .padding(PaddingValues(8.dp))
                ) {
                    if (state.userEntity.password.isEmpty()) {
                        Text(
                            text = "Password",
                            style = TextStyle.Default.copy(color = Color.Gray)
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(
                checked = state.userEntity.rememberMe,
                onCheckedChange = { onRememberMeChanged(it) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Remember Me")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(onClick = { onForgotPassword() }) {
                Text("Forgot password")
            }
        }

        ElevatedButton(onClick = {
            onLogin(
                state.userEntity.username,
                state.userEntity.password,
                state.userEntity.rememberMe
            )
        }) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        ElevatedButton(onClick = { onSignUp() }) {
            Text("Signup")
        }
    }
}