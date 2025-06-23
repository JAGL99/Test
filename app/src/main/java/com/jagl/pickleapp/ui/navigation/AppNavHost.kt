package com.jagl.pickleapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.jagl.pickleapp.features.character_detail.CharacterDetailScreen
import com.jagl.pickleapp.features.character_detail.CharacterDetailViewModel
import com.jagl.pickleapp.features.episode_detail.EpisodeDetailScreen
import com.jagl.pickleapp.features.episode_detail.EpisodeDetailViewModel
import com.jagl.pickleapp.features.home.HomeScreen
import com.jagl.pickleapp.features.login.google_login.GoogleLoginScreen
import com.jagl.pickleapp.features.login.login.LoginScreen
import com.jagl.pickleapp.features.login.restore_password.RestorePasswordScreen
import com.jagl.pickleapp.features.login.signup.SignupScreen
import com.jagl.pickleapp.ui.splash.SplashScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("splash") { inclusive = true }
                    }
                },
                onNavigateToGoogleLogin = {
                    navController.navigate("google_login") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }
        composable("login") {
            LoginScreen(
                onNavigateToForgotPassword = {
                    navController.navigate("restore_password")
                },
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToSignup = {
                    navController.navigate("signup")
                }
            )
        }
        composable("google_login") {
            GoogleLoginScreen(
                onNavigateToHome = {
                    navController.navigate("home") {
                        popUpTo("google_login") { inclusive = true }
                    }
                }
            )
        }
        composable("restore_password") {
            RestorePasswordScreen(onNavigateToLogin = { navController.popBackStack() })
        }
        composable("signup") {
            SignupScreen(onNavigateToLogin = { navController.popBackStack() })
        }
        composable("home") {
            HomeScreen(
                onNavigateToDetail = { id ->
                    navController.navigate("episode_detail/$id")
                }
            )
        }
        composable(
            "episode_detail/{${EpisodeDetailViewModel.EPISODE_ID}}",
            arguments = listOf(navArgument(EpisodeDetailViewModel.EPISODE_ID) {
                type = NavType.LongType
            })
        ) {
            EpisodeDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onNavigateToCharacterDetail = { characterId ->
                    navController.navigate("character_detail/$characterId")
                }
            )
        }
        composable(
            "character_detail/{${CharacterDetailViewModel.CHARACTER_ID}}",
            arguments = listOf(navArgument(CharacterDetailViewModel.CHARACTER_ID) {
                type = NavType.LongType
            })
        ) {
            CharacterDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}