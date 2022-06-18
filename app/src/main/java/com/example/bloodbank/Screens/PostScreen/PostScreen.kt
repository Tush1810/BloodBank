package com.example.bloodbank.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.Screen
import com.example.bloodbank.Screens.PostScreen.PostScreenViewModel
import com.example.bloodbank.Screens.PostScreen.PostScreenViewModelFactory
import com.example.bloodbank.UiCustomContents.NormalSpinner
import com.example.bloodbank.ui.theme.primary
import com.example.bloodbank.ui.theme.primaryDark
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PostScreen(navController: NavHostController){

    var viewModel: PostScreenViewModel = viewModel(
        factory = PostScreenViewModelFactory()
    )

    val coroutineScope= rememberCoroutineScope()
    val context= LocalContext.current
    coroutineScope.launch {
        viewModel.eventFlow.collect { event ->
            when(event){
                is PostScreenViewModel.myEvent.NoUserFoundEvent ->{
                    navController.navigate(Screen.LoginScreen.route){
                        popUpTo(0)
                    }
                }
                is PostScreenViewModel.myEvent.PopBackStackEvent ->{
                    navController.popBackStack()
                }
                is PostScreenViewModel.myEvent.ToastEvent->{
                    Toast.makeText(context,event.mssg,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primary)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Contact Number :",
                    modifier = Modifier.wrapContentSize()
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
                        .weight(1f)
                        .padding(start = 10.dp, end = 20.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Address :",
                    modifier = Modifier.wrapContentSize()
                )
                TextField(
                    value = viewModel.location,
                    onValueChange = {
                        viewModel.location=it
                    },
                    placeholder = {
                        Text(text = "Enter location of Place")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White
                    ),
                    modifier= Modifier
                        .weight(1f)
                        .padding(start = 10.dp, end = 20.dp)
                )
            }
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
                        viewModel.listOfBloodGroups,
                        viewModel.selectedBloodGroup
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
                    text = "Choose Division:",
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
                        viewModel.listOfDivisions,
                        viewModel.selectedDivision
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 40.dp)
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
                    text = "Post",
                    color = Color.White
                )
            }

        }
    }
}

@Composable
@Preview
fun postScreenPreview(){
    PostScreen(navController = rememberNavController())
}