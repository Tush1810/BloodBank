package com.example.bloodbank

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController
import com.example.bloodbank.ui.theme.BloodBankTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController



class MainActivity : ComponentActivity() {
    private lateinit var navController:NavHostController

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BloodBankTheme {
                navController= rememberAnimatedNavController()
                setupNavGraph(navController)
            }
        }
    }
}
