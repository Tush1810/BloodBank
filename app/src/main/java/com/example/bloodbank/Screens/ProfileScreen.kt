package com.example.bloodbank.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bloodbank.UiCustomContents.drawLogo
import com.example.bloodbank.ui.theme.primary
import androidx.navigation.*
import androidx.navigation.compose.*
import com.example.bloodbank.UiCustomContents.*
import com.example.bloodbank.*
import com.example.bloodbank.R
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.*
import androidx.compose.ui.text.font.*

@Composable
fun ProfileScreen(navController : NavHostController){
    var name by remember {
        mutableStateOf("")
    }
    var contactNumber by remember {
        mutableStateOf("")
    }
    var address by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    var isVisibilityOn by remember{ mutableStateOf(false) }
    var isVisibilityOn2 by remember{ mutableStateOf(false) }

    var icon=if(isVisibilityOn) painterResource(id = R.drawable.design_ic_visibility)
    else painterResource(id =  R.drawable.design_ic_visibility_off)

    var icon2=if(isVisibilityOn) painterResource(id = R.drawable.design_ic_visibility)
    else painterResource(id = R.drawable.design_ic_visibility_off)

    var isChecked by remember{ mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primary)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .padding(
                        top = 30.dp,
                        bottom = 40.dp
                    ),
                contentAlignment = Alignment.Center
            ){
                drawLogo(R.mipmap.blood_bank_icon_round)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Name:",
                modifier = Modifier.weight(2f))
                TextField(
                    value = name,
                    onValueChange = {
                        name=it
                    },
                    placeholder = {
                        Text(text = "Full Name")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White
                    ),
                    modifier=Modifier.weight(6f)
                )
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Sex:",
                    modifier = Modifier.weight(2f)
                )
                Box(
                    modifier = Modifier.weight(6f).background(primary)
                ){
                    NormalSpinner(
                            listOf("Male","Female")
                        )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Blood Group:",
                    modifier = Modifier.weight(2f)
                )
                Box(
                    modifier = Modifier.weight(6f).background(primary)
                ){
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
                    .wrapContentHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Contact No:",
                    modifier = Modifier.weight(2f)
                )
                TextField(
                    value = contactNumber,
                    onValueChange = {
                        contactNumber=it
                    },
                    placeholder = {
                        Text(text = "Contact Number")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White
                    ),
                    modifier=Modifier.weight(6f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Address:",
                    modifier = Modifier.weight(2f)
                )
                TextField(
                    value = address,
                    onValueChange = {
                        address=it
                    },
                    placeholder = {
                        Text(text = "Address")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White
                    ),
                    modifier=Modifier.weight(6f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Division",
                    modifier = Modifier.weight(2f)
                )
                Box(
                    modifier = Modifier.weight(6f).background(primary)
                ){
                    NormalSpinner(
                        listOf("India","Nepal")
                    )
                }
            }

            TextField(
                value = email,
                onValueChange = {
                    email=it
                },
                placeholder = {
                    Text(text = "Email")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White
                ),
                modifier=Modifier.fillMaxWidth().padding(2.dp)
            )

            TextField(
                value = password,
                onValueChange = {
                    password=it
                },
                placeholder = {
                    Text(text = "Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White
                ),
                modifier=Modifier.fillMaxWidth().padding(2.dp),
                trailingIcon = {
                    IconButton(onClick = { isVisibilityOn=!isVisibilityOn }) {
                        Icon(painter = icon, contentDescription = "VisibilityIcon")
                    }
                },
                visualTransformation =
                if(isVisibilityOn) VisualTransformation.None
                else PasswordVisualTransformation()
            )

            TextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword=it
                },
                placeholder = {
                    Text(text = "Confirm Password")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White
                ),
                modifier=Modifier.fillMaxWidth().padding(2.dp),
                trailingIcon = {
                    IconButton(onClick = { isVisibilityOn2=!isVisibilityOn2 }) {
                        Icon(painter = icon2, contentDescription = "VisibilityIcon")
                    }
                },
                visualTransformation =
                if(isVisibilityOn2) VisualTransformation.None
                else PasswordVisualTransformation()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(3.dp)
                    .padding(top=3.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Checkbox(
                    modifier = Modifier.weight(1f),
                    checked = isChecked,
                    onCheckedChange = {
                        isChecked=!isChecked
                    }
                )

                Text(
                    text = "Mark this to be a donor",
                    modifier = Modifier.weight(9f),
                    textAlign = TextAlign.Start
                )
            }

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
                    text = "Register",
                    color = primary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}

@Composable
@Preview
fun displayProfileScreen(){
    ProfileScreen(navController = rememberNavController()  )
}