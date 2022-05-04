package com.example.bloodbank.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun AchievementsScreen(navController: NavHostController){

}

@Preview
@Composable
fun displayAchievementsScreen(){
    AchievementsScreen(navController = rememberNavController())
}