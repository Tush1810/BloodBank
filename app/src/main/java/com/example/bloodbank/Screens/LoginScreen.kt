package com.example.bloodbank.Screens

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.R
import com.example.bloodbank.UiCustomContents.CircularBackground
import com.example.bloodbank.UiCustomContents.drawLogo
import com.example.bloodbank.UiCustomContents.signinButton
import com.example.bloodbank.ui.theme.primary

@Composable
fun LoginScreen(navController: NavHostController){
    var emailText by remember {
        mutableStateOf("")
    }
    var passwordText by remember {
        mutableStateOf("")
    }

    var isVisibilityOn by remember{ mutableStateOf(false) }

    var icon=if(isVisibilityOn) painterResource(id = R.drawable.design_ic_visibility)
    else painterResource(id = R.drawable.design_ic_visibility_off)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primary)
            .verticalScroll(rememberScrollState())
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier= Modifier
                    .padding(top = 20.dp)
                    .width(IntrinsicSize.Min)
                    .height(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                drawLogo(R.mipmap.blood_bank_icon_round)
                Row(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .weight(1.0f)
                            .clickable {

                            },
                        contentDescription = null,
                        painter = painterResource(id = R.drawable.facebook_circle)
                    )
                    Image(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp)
                            .weight(1.0f)
                            .clickable {

                            },
                        contentDescription = null,
                        painter = painterResource(id = R.drawable.twitter_512)
                    )
                }
            }

            TextField(value = emailText
                , onValueChange = {
                    emailText=it
                },
                placeholder = {
                    Text("Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )

            TextField(value = passwordText
                , onValueChange = {
                    passwordText=it
                },
                placeholder = {
                    Text("Password")
                },
                trailingIcon = {
                    IconButton(onClick = { isVisibilityOn=!isVisibilityOn }) {
                        Icon(painter = icon, contentDescription = "VisibilityIcon")
                    }
                },
                visualTransformation =
                if(isVisibilityOn) VisualTransformation.None
                else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )


            Box(
                modifier = Modifier
                    .padding(top = 20.dp)
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
                    text = "Sign In",
                    color = primary,
                    fontWeight = FontWeight.Bold
                )
            }


            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(20.dp)
                    .width(IntrinsicSize.Max)
                    .wrapContentHeight(),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(
                    color = Color.White,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    text = "Create an account"
                )
            }


            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .wrapContentHeight(),
                colors = ButtonDefaults.textButtonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(
                    color = Color.White,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(),
                    text = "Forgot Password?"
                )
            }


        }
    }
}

@Composable
@Preview
fun displayLoginScreen(){
    LoginScreen(navController = rememberNavController())
}
