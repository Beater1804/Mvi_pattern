package com.example.mvi.login

import com.example.mvi.mvi_basic.BaseMVIContract.MVIIntent
import com.example.mvi.mvi_basic.BaseMVIContract.MVISingleEvent
import com.example.mvi.mvi_basic.BaseMVIContract.MVIState

sealed interface LoginIntent : MVIIntent {
    data class UsernameChanged(val value: String) : LoginIntent

    data class PasswordChanged(val value: String) : LoginIntent

    data class RememberChanged(val value: Boolean) : LoginIntent

    data class OnLogin(val username: String, val password: String, val rememberMe: Boolean) : LoginIntent

    data object OnForgotPassword : LoginIntent

    data object OnSignup : LoginIntent
}

sealed interface LoginState : MVIState {
    data object Initial : LoginState

    data class LoginForm(
        val username: String = "",
        val password: String = "",
        val rememberMe: Boolean = false
    ) : LoginState
}

sealed interface LoginSingleEvent : MVISingleEvent {
    data object ShowInstruction : LoginSingleEvent
}
