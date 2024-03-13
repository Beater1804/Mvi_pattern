package com.example.mvi.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mvi.login.LoginScreenHolder
import com.example.mvi.signup.SignupScreenHolder

@Composable
fun NavGraph(navController: NavHostController, startDestination: String) {
    NavHost(navController = navController, startDestination = startDestination) {
        addLoginScreenHolder(navController = navController, navGraphBuilder = this)
        addSignupScreenHolder(navController = navController, navGraphBuilder = this)

    }
}

fun addLoginScreenHolder(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.Login.route) {
        LoginScreenHolder(navController = navController)
    }
}

fun addSignupScreenHolder(navController: NavHostController, navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.composable(NavRoute.Signup.route) {
        SignupScreenHolder(navController = navController)
    }
}