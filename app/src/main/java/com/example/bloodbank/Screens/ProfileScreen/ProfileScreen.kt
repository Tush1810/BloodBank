package com.example.bloodbank.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import com.example.bloodbank.R
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloodbank.Screen
import com.example.bloodbank.Screens.ProfileScreen.ProfileScreenViewModel
import com.example.bloodbank.Screens.ProfileScreen.ProfileScreenViewModelFactory
import com.example.bloodbank.ui.theme.primaryDark
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ProfileScreen(
    navController : NavHostController
){
    var viewModel:ProfileScreenViewModel = viewModel(
        factory = ProfileScreenViewModelFactory(LocalContext.current)
    )

    Log.i("Starting in Profile","---------")
    val list=navController.backQueue
    list.forEach {
        it.destination.route?.let { it1 -> Log.i("Entry1111", it1) };
    }
    Log.i("Ending in Profile","---------")

    val coroutineScope= rememberCoroutineScope()
    coroutineScope.launch {
        viewModel.eventFlow.collect { event ->
            when(event){
                is ProfileScreenViewModel.myEvent.NavigateEvent ->{
                    if(event.screen.route == Screen.DashboardScreen.route){
                        navController.navigate(Screen.DashboardScreen.route) {
                            navController.popBackStack()
                        }
                    }
                }
                is ProfileScreenViewModel.myEvent.BackPressedEvent ->{
                    navController.popBackStack()
                }
            }
        }
    }


    var scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            ProfileTopBar(viewModel = viewModel, navController = navController)
        }
    ) {

        var icon=if(viewModel.isPasswordVisibilityOn) painterResource(id = R.drawable.design_ic_visibility)
        else painterResource(id =  R.drawable.design_ic_visibility_off)

        var icon2=if(viewModel.isConfirmPasswordVisibilityOn) painterResource(id = R.drawable.design_ic_visibility)
        else painterResource(id = R.drawable.design_ic_visibility_off)




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

                if(viewModel.logoImageVisibilityOn){
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
                        value = viewModel.name,
                        onValueChange = {
                            viewModel.name=it
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
                        modifier= Modifier
                            .weight(6f)
                            .focusRequester(viewModel.focusRequester.component1())
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
                        modifier = Modifier
                            .weight(6f)
                            .background(primary)
                    ){
                        NormalSpinner(
                            viewModel.listOfSexes,
                            viewModel.selectedSex
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
                        modifier = Modifier
                            .weight(6f)
                            .background(primary)
                    ){
                        NormalSpinner(
                            viewModel.listOfBloodGroups,
                            viewModel.selectedBloodGroup
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
                        value = viewModel.contactNumber,
                        onValueChange = {
                            viewModel.contactNumber=it
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
                        modifier= Modifier
                            .weight(6f)
                            .focusRequester(viewModel.focusRequester.component3())
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
                        value = viewModel.address,
                        onValueChange = {
                            viewModel.address=it
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
                        modifier= Modifier
                            .weight(6f)
                            .focusRequester(viewModel.focusRequester.component2())
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
                        modifier = Modifier
                            .weight(6f)
                            .background(primary)
                    ){
                        NormalSpinner(
                            viewModel.listOfDivisions,
                            viewModel.selectedDivision
                        )
                    }
                }

                if(viewModel.emailFieldVisibilityOn){
                    TextField(
                        value = viewModel.email,
                        onValueChange = {
                            viewModel.email=it
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
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                            .focusRequester(viewModel.focusRequester.component4())
                    )
                }


                if(viewModel.passwordFieldVisibilityOn){
                    TextField(
                        value = viewModel.password,
                        onValueChange = {
                            viewModel.password=it
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
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                            .focusRequester(viewModel.focusRequester.component5()),
                        trailingIcon = {
                            IconButton(onClick = { viewModel.isPasswordVisibilityOn=!viewModel.isPasswordVisibilityOn }) {
                                Icon(painter = icon, contentDescription = "VisibilityIcon")
                            }
                        },
                        visualTransformation =
                        if(viewModel.isPasswordVisibilityOn) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )
                }

                if(viewModel.confirmPasswordFieldVisibilityOn){
                    TextField(
                        value = viewModel.confirmPassword,
                        onValueChange = {
                            viewModel.confirmPassword=it
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
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                            .focusRequester(viewModel.focusRequester.component6()),
                        trailingIcon = {
                            IconButton(onClick = { viewModel.isConfirmPasswordVisibilityOn=
                                !viewModel.isConfirmPasswordVisibilityOn }) {
                                Icon(painter = icon2, contentDescription = "VisibilityIcon")
                            }
                        },
                        visualTransformation =
                        if(viewModel.isConfirmPasswordVisibilityOn) VisualTransformation.None
                        else PasswordVisualTransformation()
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(3.dp)
                        .padding(top = 3.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Checkbox(
                        modifier = Modifier.weight(1f),
                        checked = viewModel.isDonor,
                        onCheckedChange = {
                            viewModel.isDonor=!viewModel.isDonor
                        }
                    )

                    Text(
                        text = viewModel.isDonorText,
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
                            viewModel.btnSignUp()
                        },
                    contentAlignment = Alignment.Center,
                ){
                    Text(
                        text = viewModel.buttonText,
                        color = primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ProfileTopBar(
    viewModel: ProfileScreenViewModel,
    navController: NavHostController
){

    TopAppBar(
        title = { Text(text = viewModel.topBarTitle,
            fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                viewModel.onBackPressed()
            }) {
                Icon(Icons.Filled.ArrowBack, "")
            }
        },
        backgroundColor = primaryDark,
        contentColor = Color.Black
    )
}

@Composable
@Preview
fun displayProfileScreen(){
    ProfileScreen(navController = rememberNavController()
    )
}