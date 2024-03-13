package com.example.mvi.domain.entity

data class UserEntity(
    val userId: String = "",
    val username: String = "",
    val password: String = "",
    val rememberMe: Boolean = false
)
