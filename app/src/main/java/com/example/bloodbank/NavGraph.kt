package com.example.bloodbank

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.bloodbank.Screens.*
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun setupNavGraph(navController:NavHostController){
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route,
        enterTransition = { fadeIn(animationSpec = tween(1000), initialAlpha = 0f) },
        exitTransition ={ fadeOut(animationSpec = tween(1000), targetAlpha = 0f) }
    ){
        composable(
            route = Screen.SplashScreen.route
        ){
            SplashScreen(navController = navController)
        }
        
        composable(
            route = Screen.LoginScreen.route
        ){
            LoginScreen(navController = navController)
        }

        composable(
            route = Screen.ProfileScreen.route
        ){
            ProfileScreen(navController = navController)
        }

        composable(
            route = Screen.RestorePasswordScreen.route
        ){
            RestorePasswordScreen(navController = navController)
        }

        composable(
            route = Screen.DashboardScreen.route
        ){
            DashboardScreen(parentNavController = navController)
        }

        composable(
            route = Screen.ProfileScreen.route
        ){
            ProfileScreen(navController = navController)
        }

        composable(
            route=Screen.PostScreen.route
        ){
            PostScreen(navController = navController)
        }
    }
}