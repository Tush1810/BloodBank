package com.example.bloodbank.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.ui.theme.primary

@Composable
fun HomeScreen(navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primary)
    ){

    }
}

@Preview
@Composable
fun displayHomeScreen(){
    HomeScreen(navController = rememberNavController())
}