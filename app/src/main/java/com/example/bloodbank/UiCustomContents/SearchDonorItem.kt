package com.example.bloodbank.UiCustomContents


import android.os.Build
import androidx.annotation.RequiresApi
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
import com.example.bloodbank.R
import com.example.bloodbank.ui.theme.colorPrimary
import com.example.bloodbank.ui.theme.primary
import java.time.Instant
import java.util.*

@Composable
fun SearchDonorItem(
    name:String,
    contactNumber:String,
    address:String,
    totalDonation:Int,
    lastDonation:String,
    backGroundColor: Color
) {
    Box(
        modifier = Modifier
            .height(IntrinsicSize.Min)
            .width(IntrinsicSize.Min)
            .background(backGroundColor)
            .clickable {

            },
        contentAlignment = Alignment.Center
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
                    .height(IntrinsicSize.Min)
                    .padding(start = 10.dp)
                    .weight(6f)
            ) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = "Name: ",
                        fontSize = 9.sp
                    )

                    Text(
                        text = name,
                        fontSize = 9.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = "Contact Number: ",
                        fontSize = 9.sp
                    )

                    Text(
                        text = contactNumber,
                        fontSize = 9.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = "Address: ",
                        fontSize = 9.sp
                    )

                    Text(
                        text = address,
                        fontSize = 9.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = "Total Donation: ",
                        fontSize = 9.sp
                    )

                    Text(
                        text = "$totalDonation times",
                        fontSize = 9.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                ) {
                    Text(
                        text = "Last Donation: ",
                        fontSize = 9.sp
                    )

                    Text(

                        text = if(lastDonation==null)
                            "Not yet donated"
                        else{
                            lastDonation.toString()
                            },
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview
fun displaySearchDonorItem(){
    SearchDonorItem("a","a","a",0,
    "", Color(0xFFFFFFFF))
}