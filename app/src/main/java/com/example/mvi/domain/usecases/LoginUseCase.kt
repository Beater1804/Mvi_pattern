package com.example.mvi.domain.usecases

import com.example.mvi.data.LoginRepository
import com.example.mvi.domain.entity.UserEntity

internal class LoginUseCase {
    suspend operator fun invoke(username: String, password: String): UserEntity {
        return LoginRepository.getUserByUsernamePassword(username, password)
    }
}