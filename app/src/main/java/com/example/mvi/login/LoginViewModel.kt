package com.example.mvi.login

import com.example.mvi.domain.usecases.LoginUseCase
import com.example.mvi.mvi_basic.BaseMVIViewModel.BaseMVIViewModel

internal class LoginViewModel(
    private val loginUseCase: LoginUseCase = LoginUseCase()
) : BaseMVIViewModel<LoginIntent, LoginState, LoginSingleEvent>() {
    override fun initialState(): LoginState = LoginState.LoginForm()

    override val reducer: Reducer<LoginState, LoginIntent>
        get() = LoginReducer()

    override suspend fun handleIntent(
        intent: LoginIntent,
        state: LoginState
    ): LoginIntent? = when(intent) {
        is LoginIntent.UsernameChanged -> null
        is LoginIntent.PasswordChanged -> null
        is LoginIntent.RememberChanged -> null
        is LoginIntent.OnLogin -> {
            val currentUser = loginUseCase(intent.username, intent.password)
            LoginIntent.OnLoginSuccess(currentUser.userId)
        }
        is LoginIntent.OnLoginSuccess -> {
            triggerSingleEvent(LoginSingleEvent.LoginLoaded)
            null
        }

        LoginIntent.OnForgotPassword -> null
        LoginIntent.OnSignup -> null
    }
}

internal class LoginReducer : BaseMVIViewModel.Reducer<LoginState, LoginIntent> {
    override fun reduce(
        state: LoginState,
        intent: LoginIntent
    ): LoginState = when (intent) {
        is LoginIntent.UsernameChanged -> {
            require(state is LoginState.LoginForm) { "Exception" }
            val newUser = state.userEntity.copy(
                username = intent.value
            )
            state.copy(
                userEntity = newUser
            )
        }

        is LoginIntent.PasswordChanged -> {
            require(state is LoginState.LoginForm) { "Exception" }
            val newUser = state.userEntity.copy(
                password = intent.value
            )
            state.copy(
                userEntity = newUser
            )
        }

        is LoginIntent.RememberChanged -> {
            require(state is LoginState.LoginForm) { "Exception" }
            val newUser = state.userEntity.copy(
                rememberMe = intent.value

            )
            state.copy(
                userEntity = newUser
            )
        }

        is LoginIntent.OnLogin -> {
            require(state is LoginState.LoginForm) { "Exception" }
//            val newUser = state.userEntity.copy(
//                username = intent.username,
//                password = intent.password,
//                rememberMe = intent.rememberMe
//
//            )
//            state.copy(
//                userEntity = newUser
//            )
            state
        }

        is LoginIntent.OnLoginSuccess -> {
            require(state is LoginState.LoginForm) { "Exception" }
            val newUser = state.userEntity.copy(
                userId = intent.userId

            )
            state.copy(
                userEntity = newUser
            )
        }
        LoginIntent.OnForgotPassword -> TODO()
        LoginIntent.OnSignup -> TODO()
    }
}