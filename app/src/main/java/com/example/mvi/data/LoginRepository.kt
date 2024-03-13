package com.example.mvi.data

import com.example.mvi.domain.entity.UserEntity
import kotlinx.coroutines.delay

object LoginRepository {
    private val users = listOf(
        UserEntity(
            userId = "__1",
            username = "user1",
            password = "pass1",
            rememberMe = true
        ),
        UserEntity(
            userId = "__2",
            username = "user2",
            password = "pass2",
            rememberMe = true
        ),
        UserEntity(
            userId = "__3",
            username = "user3",
            password = "pass3",
            rememberMe = true
        ),
        UserEntity(
            userId = "__4",
            username = "user4",
            password = "pass4",
            rememberMe = true
        ),
        UserEntity(
            userId = "__5",
            username = "user5",
            password = "pass5",
            rememberMe = true
        ),
    )

    suspend fun getUserByUsernamePassword(username: String, password: String) : UserEntity {
        delay(1000)

        return users.find { it.username == username && it.password == password } ?: UserEntity()
    }
}