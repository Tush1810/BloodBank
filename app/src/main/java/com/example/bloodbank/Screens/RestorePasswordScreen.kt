package com.example.bloodbank.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.ui.theme.primary

@Composable
fun RestorePasswordScreen(navController: NavHostController){
    var newPassword by remember{ mutableStateOf("")}
    var isVisibilityOn by remember{ mutableStateOf(false)}

    var icon=if(isVisibilityOn) painterResource(id = com.example.bloodbank.R.drawable.design_ic_visibility)
    else painterResource(id =  com.example.bloodbank.R.drawable.design_ic_visibility_off)

    Column(
        modifier = Modifier.fillMaxSize().background(primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextField(
            value = newPassword,
            onValueChange = {
                newPassword=it
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier
                .width(300.dp)
                .wrapContentHeight(),
            trailingIcon = {
                IconButton(onClick = { isVisibilityOn=!isVisibilityOn }) {
                    Icon(painter = icon, contentDescription = "VisibilityIcon")
                }
            },
            visualTransformation =
            if(isVisibilityOn) VisualTransformation.None
            else PasswordVisualTransformation()
        )

        Box(
            modifier = Modifier
                .padding(top = 40.dp, bottom = 40.dp)
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
                text = "Reset",
                color = primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@Preview
fun displayRestorePassWordScreen(){
    RestorePasswordScreen(navController = rememberNavController())
}