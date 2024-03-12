package com.example.mvi.login

import com.example.mvi.mvi_basic.BaseMVIViewModel.BaseMVIViewModel

class LoginViewModel(

) : BaseMVIViewModel<LoginIntent, LoginState, LoginSingleEvent>() {
    override fun initialState(): LoginState = LoginState.LoginForm()

    override val reducer: Reducer<LoginState, LoginIntent>
        get() = LoginReducer()

    override suspend fun handleIntent(
        intent: LoginIntent,
        state: LoginState
    ): LoginIntent? = when(intent) {
        is LoginIntent.UsernameChanged -> TODO()
        is LoginIntent.PasswordChanged -> TODO()
        is LoginIntent.RememberChanged -> TODO()
        is LoginIntent.OnLogin -> TODO()
        LoginIntent.OnForgotPassword -> TODO()
        LoginIntent.OnSignup -> TODO()
    }
}

internal class LoginReducer : BaseMVIViewModel.Reducer<LoginState, LoginIntent> {
    override fun reduce(
        state: LoginState,
        intent: LoginIntent
    ): LoginState = when (intent) {
        is LoginIntent.UsernameChanged -> {
            require(state is LoginState.LoginForm) { "Exception" }
            state.copy(
                username = intent.value
            )
        }

        is LoginIntent.PasswordChanged -> {
            require(state is LoginState.LoginForm) { "Exception" }
            state.copy(
                password = intent.value
            )
        }

        is LoginIntent.RememberChanged -> {
            require(state is LoginState.LoginForm) { "Exception" }
            state.copy(
                rememberMe = intent.value
            )
        }

        is LoginIntent.OnLogin -> {
            require(state is LoginState.LoginForm) { "Exception" }
            state.copy(
                username = intent.username,
                password = intent.password,
                rememberMe = intent.rememberMe
            )
        }

        LoginIntent.OnForgotPassword -> TODO()
        LoginIntent.OnSignup -> TODO()
    }
}