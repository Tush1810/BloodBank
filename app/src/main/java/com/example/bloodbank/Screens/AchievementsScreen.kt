package com.example.bloodbank.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.ui.theme.primary
import com.example.bloodbank.ui.theme.primaryDark

@Composable
fun AchievementsScreen(navController: NavHostController){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primary)
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clip(RoundedCornerShape(10.dp))
                    .background(primaryDark),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "Have you donated today?",
                    color = Color.White,
                    modifier = Modifier.padding(10.dp),
                    fontSize = 20.sp
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .wrapContentSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 5.dp)
                        .height(50.dp)
                        .width(120.dp)
                        .clip(
                            RoundedCornerShape(
                                14.dp
                            )
                        )
                        .border(
                            BorderStroke(width = 3.dp, color = Color(0xFFe6e0e0)),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .background(Color(0xFFe9e4e4))
                        .clickable {

                        },
                    contentAlignment = Alignment.Center,
                ){
                    Text(
                        text = "YES",
                        color = primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .height(50.dp)
                        .width(120.dp)
                        .clip(
                            RoundedCornerShape(
                                14.dp
                            )
                        )
                        .border(
                            BorderStroke(width = 3.dp, color = Color(0xFFe6e0e0)),
                            shape = RoundedCornerShape(14.dp)
                        )
                        .background(Color(0xFFe9e4e4))
                        .clickable {

                        },
                    contentAlignment = Alignment.Center,
                ){
                    Text(
                        text = "No",
                        color = primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(top = 60.dp)
                    .wrapContentSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.padding(end=40.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(10.dp))
                            .background(primaryDark),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Total Donated:",
                            color = Color.White,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(primaryDark),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "100 times",
                            color = Color.White,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp
                        )
                    }
                }

                Column() {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .clip(RoundedCornerShape(10.dp))
                            .background(primaryDark),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "Last Donated:",
                            color = Color.White,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .size(150.dp)
                            .clip(CircleShape)
                            .background(primaryDark),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = "20/04/2022",
                            color = Color.White,
                            modifier = Modifier.padding(10.dp),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun displayAchievementsScreen(){
    AchievementsScreen(navController = rememberNavController())
}