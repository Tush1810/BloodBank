package com.example.bloodbank.Screens.DashboardScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bloodbank.Models.UserData
import com.example.bloodbank.R
import com.example.bloodbank.Screen
import com.example.bloodbank.Screens.MenuOptions
import com.example.bloodbank.Screens.NavigationItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class DashboardScreenViewModel:ViewModel {

    var selectedNavigation by mutableStateOf(0)
    var userName:String by mutableStateOf("")
    var userEmail:String by mutableStateOf("")



    sealed class myEvent(){
        data class NavigateEvent(val screenRoute: String):myEvent()
        data class NavigateProfileEvent(val screen:NavigationItem
                    =NavigationItem.ProfileScreen):myEvent()
        data class NavigatePostScreenEvent(val screen:Screen
                                        =Screen.PostScreen):myEvent()
        data class LogoutEvent(val screen:Screen
                                           =Screen.PostScreen):myEvent()
        data class CloseSideDrawerEvent(var isOpen:Boolean=true):myEvent()
    }

    private val eventChannel= Channel<myEvent>()
    public val eventFlow = eventChannel.receiveAsFlow()

    private fun triggerNavigateEvent(route:String)= viewModelScope.launch{
        triggerCloseSideDrawerEvent()
        eventChannel.send(myEvent.NavigateEvent(route))
    }

    private fun triggerNavigateProfileEvent() = viewModelScope.launch {
        triggerCloseSideDrawerEvent()
        eventChannel.send(myEvent.NavigateProfileEvent())
    }
    private fun triggerCloseSideDrawerEvent() = viewModelScope.launch {
        eventChannel.send(myEvent.CloseSideDrawerEvent())
    }
    private fun triggerNavigatePostScreenEvent() = viewModelScope.launch {
        eventChannel.send(myEvent.NavigatePostScreenEvent())
    }
    private fun triggerLogoutEvent() = viewModelScope.launch {
        eventChannel.send(myEvent.LogoutEvent())
    }



    private lateinit var mAuth:FirebaseAuth
    private lateinit var db:FirebaseDatabase
    private lateinit var curUser:FirebaseUser
    private lateinit var usersDbRef:DatabaseReference

    constructor(){
        mAuth=FirebaseAuth.getInstance()
        curUser= mAuth.currentUser!!
        db=FirebaseDatabase.getInstance()
        usersDbRef=db.getReference("users")

        val singleUser = usersDbRef.child(curUser.uid)
//        pd.show()

        singleUser.addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    userName=snapshot.getValue(UserData::class.java)!!.Name
                    userEmail=curUser.email!!
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("User",error.message)
                }

            }
        )
    }

    fun onItemClick(menuOptions: MenuOptions){
        if(menuOptions.route.equals(NavigationItem.ProfileScreen.route)){
            triggerNavigateProfileEvent()
        }else if(menuOptions.route.equals(NavigationItem.Logout.route)){
            mAuth.signOut()
            triggerLogoutEvent()
        }else{
            selectedNavigation=menuOptions.index
            triggerNavigateEvent(menuOptions.route)
        }
    }

    fun fabClicked(){
        triggerNavigatePostScreenEvent()
    }

}

class DashboardScreenViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = DashboardScreenViewModel() as T
}