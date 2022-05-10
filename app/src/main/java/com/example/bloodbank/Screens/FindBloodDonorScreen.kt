package com.example.bloodbank.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
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
                    .height(IntrinsicSize.Min)
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


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Choose your State:",
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
                            "Delhi",
                            "Punjab",
                            "Haryana"
                        )
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(top=40.dp)
                    .height(50.dp)
                    .width(120.dp)
                    .clip(
                        RoundedCornerShape(
                            14.dp
                        )
                    )
                    .background(primaryDark)
                    .clickable {

                    },
                contentAlignment = Alignment.Center,
            ){
                Text(
                    text = "Search",
                    color = Color.White
                )
            }

        }
    }
}

@Preview
@Composable
fun displayFindBloodDonorScreen(){
    FindBloodDonorScreen(navController = rememberNavController())
}