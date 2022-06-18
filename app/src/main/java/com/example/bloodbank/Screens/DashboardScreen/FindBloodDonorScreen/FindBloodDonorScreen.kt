package com.example.bloodbank.Screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.Screens.DashboardScreen.FindBloodDonorScreen.FindBloodDonorScreenViewModel
import com.example.bloodbank.Screens.DashboardScreen.FindBloodDonorScreen.FindBloodDonorScreenViewModelFactory
import com.example.bloodbank.UiCustomContents.NormalSpinner
import com.example.bloodbank.UiCustomContents.SearchDonorItem
import com.example.bloodbank.ui.theme.primary
import com.example.bloodbank.ui.theme.primaryDark
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("UnrememberedMutableState", "CoroutineCreationDuringComposition")
@Composable
fun FindBloodDonorScreen(navController: NavHostController){

    var viewModel:FindBloodDonorScreenViewModel= viewModel(factory =
            FindBloodDonorScreenViewModelFactory()
    )

    var context= LocalContext.current

    val coroutineScope= rememberCoroutineScope()
    coroutineScope.launch {
        viewModel.eventFlow.collect { event ->
            when(event){
                is FindBloodDonorScreenViewModel.myEvent.ToastEvent ->{
                    Toast.makeText(context,event.mssg,Toast.LENGTH_LONG).show()
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
                .fillMaxWidth(),
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
                        viewModel.listOfBloodGroups,
                        viewModel.selectedBloodGroupIndex
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
                        viewModel.listOfDivisions,
                        viewModel.selectedDivisionsIndex
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
                               viewModel.btnClicked()
                    },
                contentAlignment = Alignment.Center,
            ){
                Text(
                    text = "Search",
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(primary)
            ){
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    itemsIndexed(items = viewModel.donorsList) { index,post->
                        SearchDonorItem(
                            name = post.Name,
                            contactNumber = post.Contact,
                            address = post.Address,
                            totalDonation = post.TotalDonate,
                            lastDonation = post.LastDonate,
                            backGroundColor = if(index%2==0) Color(0xFFC13F31)
                                            else Color(0xFFFFFFFF)
                        )
                    }
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