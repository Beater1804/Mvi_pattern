package com.example.mvi.navigation

sealed class NavRoute(val route: String) {
    object Login : NavRoute("login")

    object Signup : NavRoute("signup")

}