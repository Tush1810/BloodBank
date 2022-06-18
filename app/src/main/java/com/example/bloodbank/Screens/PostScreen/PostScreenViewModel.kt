package com.example.bloodbank.Screens.PostScreen

import com.example.bloodbank.Models.UserData
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.*

class PostScreenViewModel: ViewModel {

    sealed class myEvent(){
        data class ToastEvent(var mssg:String):myEvent()
        data class PopBackStackEvent(var b:Boolean=true):myEvent()
        data class NoUserFoundEvent(var b:Boolean=true):myEvent()
    }

    private val eventChannel= Channel<myEvent>()
    public val eventFlow = eventChannel.receiveAsFlow()

    private fun triggerToastEvent(mssg: String)= viewModelScope.launch{
        eventChannel.send(myEvent.ToastEvent(mssg))
    }

    private fun triggerPopBackStackEvent() = viewModelScope.launch {
        eventChannel.send(myEvent.PopBackStackEvent())
    }

    private fun triggerNoUserFoundEvent() = viewModelScope.launch{
        triggerToastEvent("No User Found")
        eventChannel.send(myEvent.NoUserFoundEvent())
    }


    var contactNumber by mutableStateOf("")
    var location by mutableStateOf("")
    var selectedBloodGroup = mutableStateOf(0)
    var listOfBloodGroups = listOf(
        "A+",
        "A-",
        "B+",
        "B-",
        "AB+",
        "AB-",
        "O+",
        "O-"
    )
    var listOfDivisions = listOf("Delhi", "Punjab")
    var selectedDivision = mutableStateOf(0)

    var cal:Calendar
    lateinit var uid:String
    lateinit var time:String
    lateinit var date:String
    lateinit var mAuth: FirebaseAuth
    lateinit var fdb:FirebaseDatabase
    lateinit var db_posts:DatabaseReference


    constructor(){
        cal=Calendar.getInstance()
        var day=cal.get(Calendar.DAY_OF_MONTH)
        var month=cal.get(Calendar.MONTH)
        var year=cal.get(Calendar.YEAR)
        var hour=cal.get(Calendar.HOUR)
        var min=cal.get(Calendar.MINUTE)

        month++
        time=""
        date=""
        var ampm="AM"

        if(cal.get(Calendar.AM_PM)==1){
            ampm="PM"
        }
        if(hour<10){
            time="0"
        }
        time+=hour
        time+=":"
        time+=min
        time+=" "
        time+=ampm

        date=day.toString()+"/"+month+"/"+year

        val cur_user=FirebaseAuth.getInstance().currentUser

        if(cur_user==null){
            triggerNoUserFoundEvent()
        }else{
            uid=cur_user.uid
        }

        mAuth= FirebaseAuth.getInstance()
        fdb= FirebaseDatabase.getInstance()
        db_posts=fdb.getReference("posts")
    }

    fun btnClicked() {
        try {
            var findName = fdb.getReference("users").child(uid)
            if (contactNumber.length == 0) {
                triggerToastEvent("Enter your contact number!")
            } else if (location.length == 0) {
                triggerToastEvent("Enter your contact number!")
            } else {
                findName.addListenerForSingleValueEvent(object : ValueEventListener {

                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val userData: UserData? = snapshot.getValue(UserData::class.java)
                            db_posts.child(uid).child("Name").setValue(userData!!.Name)
                            db_posts.child(uid).child("Contact").setValue(contactNumber);
                            db_posts.child(uid).child("Address").setValue(location);
                            db_posts.child(uid).child("Division").setValue(
                                listOfDivisions
                                    .get(selectedDivision.value)
                            );
                            db_posts.child(uid).child("BloodGroup").setValue(
                                listOfBloodGroups
                                    .get(selectedBloodGroup.value)
                            );
                            db_posts.child(uid).child("Time").setValue(time);
                            db_posts.child(uid).child("Date").setValue(date);
                            triggerToastEvent("Your post has been created successfully")
                            triggerPopBackStackEvent()
                        } else {
                            triggerToastEvent("Database error occured.")
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }
}

class PostScreenViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PostScreenViewModel() as T
}