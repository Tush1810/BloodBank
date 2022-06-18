package com.example.bloodbank.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.Screens.DashboardScreen.Home.HomeScreenViewModel
import com.example.bloodbank.Screens.DashboardScreen.Home.HomeScreenViewModelFactory
import com.example.bloodbank.UiCustomContents.RequestListItem
import com.example.bloodbank.ui.theme.primary

@Composable
fun HomeScreen(navController: NavHostController){
    var viewModel:HomeScreenViewModel = viewModel(
        factory = HomeScreenViewModelFactory(LocalContext.current)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primary)
    ){
        LazyColumn(
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            itemsIndexed(items = viewModel.postLists) { index,post->
                RequestListItem(post = post, isPrimaryBackground = (index%2==0))
            }
        }
    }
}

@Preview
@Composable
fun displayHomeScreen(){
    HomeScreen(navController = rememberNavController())
}