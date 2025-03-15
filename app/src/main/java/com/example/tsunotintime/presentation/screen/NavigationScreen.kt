package com.example.tsunotintime.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tsunotintime.domain.entity.RequestModel
import com.example.tsunotintime.data.models.RequestStatus
import com.example.tsunotintime.navigation.Route
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavigationScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Route.WelcomeScreen.route,
        modifier = modifier
    ) {
        composable(Route.WelcomeScreen.route) {
            WelcomeScreen(
                toLogin = { navController.navigate(Route.LoginScreen.route) },
                toRegistration = { navController.navigate(Route.RegistrationScreen.route) }
            )
        }
        composable(Route.LoginScreen.route) {
            LoginScreen(
                loginViewModel = koinViewModel(),
                back = {
                    navController.navigate(Route.WelcomeScreen.route) {
                        popUpTo(Route.WelcomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                toRegistration = { navController.navigate(Route.RegistrationScreen.route) },
                onSuccess = {
                    navController.navigate(Route.RequestScreen.route) {
                        popUpTo(Route.WelcomeScreen.route) { inclusive = true }
                    }
                })
        }
        composable(Route.RegistrationScreen.route) {
            RegisterScreen(
                koinViewModel(),
                back = {
                    navController.navigate(Route.WelcomeScreen.route) {
                        popUpTo(Route.WelcomeScreen.route) {
                            inclusive = true
                        }
                    }
                },
                toLogin = { navController.navigate(Route.LoginScreen.route) },
                onSuccess = {
                    navController.navigate(Route.RequestScreen.route) {
                        navController.navigate(Route.RequestScreen.route) {
                            popUpTo(Route.WelcomeScreen.route) { inclusive = true }
                        }
                    }
                })
        }
        composable(Route.ProfileScreen.route) {
            ProfileScreen(koinViewModel(), back = {
                navController.navigate(Route.RequestScreen.route) {
                    popUpTo(Route.WelcomeScreen.route) { inclusive = true }
                }
            }, logoutAction = {
                navController.navigate(Route.WelcomeScreen.route) {
                    popUpTo(Route.WelcomeScreen.route) { inclusive = true }
                }
            })
        }
        composable(Route.RequestScreen.route) {
            RequestScreen(
                koinViewModel(),
                toProfile = {
                    navController.navigate(Route.ProfileScreen.route) {
                        launchSingleTop
                    }
                })
        }
        composable(Route.RequestAdd.route) {
            AddRequestScreen()
        }
    }
}