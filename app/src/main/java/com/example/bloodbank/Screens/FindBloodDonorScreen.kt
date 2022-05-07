package com.example.bloodbank.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.UiCustomContents.NormalSpinner
import com.example.bloodbank.ui.theme.primary
import com.example.bloodbank.ui.theme.primaryDark

@Composable
fun FindBloodDonorScreen(navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primary)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Choose Blood Group:",
                    modifier = Modifier.wrapContentSize()
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(primary)
                        .padding(start = 10.dp, end = 20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(primaryDark)
                ) {
                    NormalSpinner(
                        listOf(
                            "A+",
                            "A-",
                            "B+",
                            "B-",
                            "AB+",
                            "AB-",
                            "O+",
                            "O-"
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun displayFindBloodDonorScreen(){
    FindBloodDonorScreen(navController = rememberNavController())
}