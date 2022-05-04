package com.example.bloodbank.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(navController: NavHostController){

}

@Preview
@Composable
fun displayHomeScreen(){
    HomeScreen(navController = rememberNavController())
}