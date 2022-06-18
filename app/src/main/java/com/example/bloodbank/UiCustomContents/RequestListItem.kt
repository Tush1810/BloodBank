package com.example.bloodbank.UiCustomContents

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloodbank.Models.CustomerUserData
import com.example.bloodbank.R
import com.example.bloodbank.ui.theme.primary

@Composable
fun RequestListItem(post:CustomerUserData,isPrimaryBackground:Boolean) {
    Box(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
            .background(if(isPrimaryBackground) Color(0xFFC13F31) else Color(0xFFFFFFFF))
            .clickable {

            }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_blood_transfusion),
                contentDescription = null,
                modifier = Modifier
                    .height(70.dp)
                    .width(70.dp)
                    .weight(2.7f)
            )

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 5.dp , top = 5.dp, bottom = 5.dp)
                    .weight(6f)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = post.bloodGroup+" ",
                        color = Color(0xFFF8F8F8),
                        fontSize = 12.sp
                    )

                    Text(
                        text = "Blood Donor !",
                        fontSize = 9.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(6f)
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        Text(
                            text = "Contact Number: ",
                            fontSize = 9.sp
                        )

                        Text(
                            text = post.contact,
                            fontSize = 9.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        Text(
                            text = "Requested by: ",
                            fontSize = 9.sp
                        )

                        Text(
                            text = post.name,
                            fontSize = 9.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        Text(
                            text = "Posted: ",
                            fontSize = 9.sp
                        )

                        Text(
                            text = post.time+", "+post.date,
                            fontSize = 9.sp
                        )
                    }
                    Row(
                        modifier = Modifier
                            .wrapContentSize()
                    ) {
                        Text(
                            text = "From: ",
                            fontSize = 9.sp
                        )

                        Text(
                            text = post.address+", "+post.division,
                            fontSize = 9.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun displayRequestListItem(){
    RequestListItem(
        CustomerUserData("","","",""
        ,"","","",),
        true
    )
}