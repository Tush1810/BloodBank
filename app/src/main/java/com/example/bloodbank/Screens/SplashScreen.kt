package com.example.bloodbank.Screens


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.R
import com.example.bloodbank.Screen
import com.example.bloodbank.UiCustomContents.CircularBackground
import com.example.bloodbank.ui.theme.primary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController
){
    var id by remember {
        mutableStateOf(0)
    }
    var repeating by remember {
        mutableStateOf(false)
    }

    var resourceId by remember {
        mutableStateOf(R.drawable._01)
    }
    
    LaunchedEffect(key1 = true){
        while(true){
            id++;
            if(id==31&&!repeating){
                id=1
                repeating=true
            }
            if(id==31) break;
            if(id==1){ resourceId=R.drawable._01 }
            else if(id==2){ resourceId=R.drawable._02 }
            else if(id==3){ resourceId=R.drawable._03 }
            else if(id==4){ resourceId=R.drawable._04 }
            else if(id==5){ resourceId=R.drawable._05 }
            else if(id==6){ resourceId=R.drawable._06 }
            else if(id==7){ resourceId=R.drawable._07 }
            else if(id==8){ resourceId=R.drawable._08 }
            else if(id==9){ resourceId=R.drawable._09 }
            else if(id==10){ resourceId=R.drawable._10 }
            else if(id==11){ resourceId=R.drawable._11 }
            else if(id==12){ resourceId=R.drawable._12 }
            else if(id==13){ resourceId=R.drawable._13 }
            else if(id==14){ resourceId=R.drawable._14 }
            else if(id==15){ resourceId=R.drawable._15 }
            else if(id==16){ resourceId=R.drawable._16 }
            else if(id==17){ resourceId=R.drawable._17 }
            else if(id==18){ resourceId=R.drawable._18 }
            else if(id==19){ resourceId=R.drawable._19 }
            else if(id==20){ resourceId=R.drawable._20 }
            else if(id==21){ resourceId=R.drawable._21 }
            else if(id==22){ resourceId=R.drawable._22 }
            else if(id==23){ resourceId=R.drawable._23 }
            else if(id==24){ resourceId=R.drawable._24 }
            else if(id==25){ resourceId=R.drawable._25 }
            else if(id==26){ resourceId=R.drawable._26 }
            else if(id==27){ resourceId=R.drawable._27 }
            else if(id==28){ resourceId=R.drawable._28 }
            else if(id==29){ resourceId=R.drawable._29 }
            else if(id==30){ resourceId=R.drawable._30 }
            Log.i("Here","Id Changed")
            delay(30)
        }
        navController.popBackStack()
        navController.navigate(
            Screen.LoginScreen.route
        )
    }

    

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Log.i("Here","Image Changed")

        Box(
            modifier = Modifier
                .background(Color.Transparent),
            contentAlignment = Alignment.Center
        ){
            CircularBackground(shape = CircleShape, size = 120.dp)
            Image(
                painter = painterResource(id = resourceId),
                contentDescription = null
            )
        }

        Text(
            text = "Blood Point",
            modifier = Modifier
                .padding(top = 15.dp),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 48.sp
        )
        Text(
            text = "Donate Blood, Save Life",
            fontSize = 24.sp
        )

    }
}

@Composable
@Preview
fun DisplaySplashScreen(){
    SplashScreen(navController = rememberNavController())
}
