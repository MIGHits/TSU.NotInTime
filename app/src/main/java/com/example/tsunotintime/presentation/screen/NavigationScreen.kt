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
                toRegistration = { navController.navigate(Route.RegistrationScreen.route) },
                authorize = {
                    navController.navigate(Route.RequestScreen.route) {
                        popUpTo(Route.WelcomeScreen.route) { inclusive = true }
                    }
                },
                viewModel = koinViewModel()
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
                        popUpTo(Route.WelcomeScreen.route) { inclusive = true }
                    }
                })
        }
        composable(Route.ProfileScreen.route) {
            ProfileScreen(koinViewModel(), back = {
                navController.navigate(Route.RequestScreen.route) {
                    popUpTo(Route.RequestScreen.route) { inclusive = true }
                }
            }, logoutAction = {
                navController.navigate(Route.LoginScreen.route) {
                    popUpTo(0) { inclusive = true }
                }
            }, authViewModel = koinViewModel())
        }
        composable(Route.RequestScreen.route) {
            RequestScreen(
                koinViewModel(),
                toProfile = {
                    navController.navigate(Route.ProfileScreen.route) {}
                },
                toAddScreen = {
                    navController.navigate(Route.RequestAdd.route) {}
                }, authViewModel = koinViewModel(),
                toLogin = {
                    navController.navigate(Route.LoginScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Route.RequestAdd.route) {
            AddRequestScreen(
                back = { navController.navigateUp() },
                viewModel = koinViewModel(),
                authViewModel = koinViewModel(),
                toLogin = {
                    navController.navigate(Route.LoginScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                })
        }
    }
}