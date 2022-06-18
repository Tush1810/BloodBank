package com.example.bloodbank.Screens


import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bloodbank.R
import com.example.bloodbank.Screen
import com.example.bloodbank.Screens.DashboardScreen.DashboardScreenViewModel
import com.example.bloodbank.Screens.DashboardScreen.DashboardScreenViewModelFactory
import com.example.bloodbank.Screens.ProfileScreen.ProfileScreenViewModel
import com.example.bloodbank.UiCustomContents.drawLogo
import com.example.bloodbank.ui.theme.colorPrimary
import com.example.bloodbank.ui.theme.primary
import com.example.bloodbank.ui.theme.primaryDark
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DashboardScreen(
    parentNavController:NavHostController
){


    var scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberAnimatedNavController()

    val items = listOf(
        MenuOptions(R.drawable.ic_home,"Home",NavigationItem.HomeScreen.route,0),
        MenuOptions(R.drawable.ic_profile_overview,"Profile",NavigationItem.ProfileScreen.route,1),
        MenuOptions(R.drawable.ic_menu_achievements,"Achievements",NavigationItem.AchievementsScreen.route,2),
        MenuOptions(R.drawable.ic_find_donor,"Find Blood Donor",NavigationItem.FindBloodDonorScreen.route,3),
        MenuOptions(R.drawable.ic_find_hospital,"Find Nearest Hospital",NavigationItem.NearestHospitalScreen.route,4),
        MenuOptions(R.drawable.ic_logout,"Logout",NavigationItem.Logout.route,5)
    )

    Log.i("Starting in DashBoard","---------")
    val list=navController.backQueue
    list.forEach {
        it.destination.route?.let { it1 -> Log.i("Entry1111", it1) };
    }
    Log.i("Ending in DashBoard","---------")


    val viewModel:DashboardScreenViewModel = viewModel(
        factory = DashboardScreenViewModelFactory()
    )

    val coroutineScope= rememberCoroutineScope()
    coroutineScope.launch {
        viewModel.eventFlow.collect { event ->
            when(event){
                is DashboardScreenViewModel.myEvent.NavigateEvent ->{
                    navController.navigate(event.screenRoute) {
                        navController.graph.startDestinationRoute?.let { route ->
                            Log.i("route",route)
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
                is DashboardScreenViewModel.myEvent.NavigateProfileEvent ->{
                    parentNavController.navigate(Screen.ProfileScreen.route)
                }
                is DashboardScreenViewModel.myEvent.CloseSideDrawerEvent ->{
                    scope.launch {
                        scaffoldState.drawerState.animateTo(
                            DrawerValue.Closed,
                            anim = TweenSpec<Float>(durationMillis = 1000)
                        )
                        Log.i("Finished","Finished drawer closing")
                    }
                }
                is DashboardScreenViewModel.myEvent.NavigatePostScreenEvent -> {
                    parentNavController.navigate(Screen.PostScreen.route)
                }
                is DashboardScreenViewModel.myEvent.LogoutEvent -> {
                    parentNavController.navigate(Screen.LoginScreen.route){
                        popUpTo(0)
                    }
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            myAppBar(
                scaffoldState = scaffoldState,
                scope = scope,
                viewModel=viewModel
            )
        },
        drawerContent = {
            DrawerHeader(viewModel.userName,viewModel.userEmail)
            myAppSideDrawer(
                items=items,
                selectedNavigation = viewModel.selectedNavigation,
                onItemClick = { item->
                    viewModel.onItemClick(item)
                }
            )
        },
        floatingActionButton = {
                               floatingActionButton(
                                   onClick = {viewModel.fabClicked()}
                               )
        },
        modifier = Modifier.fillMaxSize()
    ){
        Navigation(
            navController = navController,
            parentNavController = parentNavController,
            viewModel=viewModel
        )
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun myAppBar(
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    viewModel:DashboardScreenViewModel
){
    var title by remember {
        mutableStateOf("hi")
    }
    val currentNavigation=viewModel.selectedNavigation
    if(currentNavigation==0){
        title="Blood Point"
    }else if(currentNavigation==1){
        title="Profile"
    }else if(currentNavigation==2){
        title="Achievements"
    }else if(currentNavigation==3){
        title="Find Blood Donor"
    }else if(currentNavigation==4){
        title="Nearest Hospitals"
    }
    TopAppBar(
        title = { Text(text = title, fontSize = 18.sp) },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.animateTo(
                        DrawerValue.Open,
                        anim = TweenSpec<Float>(durationMillis = 1000)
                    )
                }
            }) {
                Icon(Icons.Filled.Menu, "")
            }
        },
        backgroundColor = primaryDark,
        contentColor = Color.Black
    )
}

@Composable
fun floatingActionButton(onClick:()->Unit){
    FloatingActionButton(
        onClick =  onClick ,
        backgroundColor = colorPrimary
    ) {
        Icon(imageVector = Icons.Filled.Add,"")
    }
}



@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun myAppSideDrawer(
    items:List<MenuOptions>,
    selectedNavigation: Int,
    onItemClick:(MenuOptions)->Unit
){
        for (i in 0..items.size - 1) {
            var item = items.get(i)
            NavOptions(
                item = item,
                selectedNavigation = selectedNavigation,
                onItemClick = onItemClick
            )
        }
}




@Composable
fun NavOptions(
    item:MenuOptions,
    selectedNavigation:Int,
    onItemClick:(MenuOptions)->Unit,
){
    val backGroundColor by animateColorAsState(
        targetValue = if(selectedNavigation==item.index){
            Color.Gray
        }else Color.White,
        animationSpec = tween(1000)
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .background(backGroundColor)
            .clickable(onClick = {
                onItemClick(item)
            }
            )
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = item.imageId),
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(top = 2.dp, bottom = 2.dp),
                contentScale = ContentScale.FillHeight
            )

            Text(
                text = item.text,
                modifier = Modifier
                    .weight(9f)
                    .padding(start = 30.dp),
                textAlign = TextAlign.Start
                )
        }
    }
}

@Composable
fun DrawerHeader(name:String,email:String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(176.dp)
            .background(primary)
    ){
        Image(painter = painterResource(
            id = R.drawable.ic_cover_logo
        ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource
                    (id = R.mipmap.blood_bank_ic_launcher_foreground),
                contentDescription = null)
            Text(
                text = name,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 8.dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF000000),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = email,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                color = Color(0xFF000000)
            )
        }
    }
}

@Composable
@Preview
fun displayNavController(){
    DashboardScreen(parentNavController = rememberNavController())
}

@Composable
@Preview
fun displayDrawerHeader(){

//    DrawerHeader()
}

@Composable
@Preview
fun displayNavOptions(){

}

data class MenuOptions(
    var imageId:Int,
    var text:String,
    var route:String,
    var index:Int
)

sealed class NavigationItem(var route:String){
    object HomeScreen:NavigationItem("home_screen")
    object ProfileScreen:NavigationItem("profile_screen")
    object AchievementsScreen:NavigationItem("achievements_screen")
    object FindBloodDonorScreen:NavigationItem("find_blood_donor_screen")
    object NearestHospitalScreen:NavigationItem("nearest_hospital_screen")
    object Logout:NavigationItem("logout")
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(navController: NavHostController,
               parentNavController: NavHostController,
               viewModel:DashboardScreenViewModel
){
        AnimatedNavHost(
            navController,
            startDestination = NavigationItem.HomeScreen.route,
            enterTransition = {fadeIn(animationSpec = tween(2000), initialAlpha = 0f)},
            exitTransition ={fadeOut(animationSpec = tween(2000), targetAlpha = 0f)}
        ){

            composable(NavigationItem.HomeScreen.route){
                viewModel.selectedNavigation=0
                HomeScreen(navController)

            }

            composable(NavigationItem.AchievementsScreen.route){
                viewModel.selectedNavigation=2
                AchievementsScreen(navController = navController)
            }

            composable(NavigationItem.FindBloodDonorScreen.route){
                viewModel.selectedNavigation=3
                FindBloodDonorScreen(navController = navController)
            }

            composable(NavigationItem.NearestHospitalScreen.route){
                viewModel.selectedNavigation=4
                NearestHospitalScreen(navController = navController)
            }

        }
}



private object RippleIndication : Indication {
    private class RippleIndicationInstance(
        private val isPressed: State<Boolean>
    ) : IndicationInstance {
        override fun ContentDrawScope.drawIndication() {
            drawContent()
            if (isPressed.value) {
                drawRect(color = primary.copy(alpha = 0.3f), size = size)
            }
        }
    }

    @Composable
    override fun rememberUpdatedInstance(interactionSource: InteractionSource): IndicationInstance {
        val isPressed = interactionSource.collectIsPressedAsState()
        return remember(interactionSource) {
            RippleIndication.RippleIndicationInstance(isPressed)
        }
    }
}
