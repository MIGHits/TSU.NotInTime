package com.example.tsunotintime.navigation

sealed class Route(val route: String) {
    data object WelcomeScreen : Route(route = "welcome_screen")
    data object RegistrationScreen : Route(route = "registration_screen")
    data object LoginScreen : Route(route = "login_screen")
    data object ProfileScreen : Route(route = "profile_screen")
    data object RequestScreen : Route(route = "request_screen")
    data object RequestAdd : Route(route = "request_details")
}