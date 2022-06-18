package com.example.bloodbank.Screens.DashboardScreen.Achievements

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bloodbank.Models.DonorData
import com.example.bloodbank.Models.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit

class AchievementsScreenViewModel :ViewModel {
    var totalDonation by mutableStateOf("0 times")
    var lastDate by mutableStateOf("01/01/2001")
    var donateInfo by mutableStateOf("Have you donated Today?")
    var nextDonate by mutableStateOf("")
    var nextDonateVisibility by mutableStateOf(false)
    var yesNoVisibility by mutableStateOf(true)
    var isNotADonor by mutableStateOf(false)

    // sex spinner
    var listOfSexes = listOf("Male", "Female")

    // blood group spinner
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

    //division spinner
    var listOfDivisions = listOf("Delhi", "Punjab")

    lateinit var userRef: DatabaseReference
    lateinit var donorRef: DatabaseReference
    lateinit var mAuth: FirebaseAuth
    lateinit var dbUser: FirebaseDatabase


    private var getBg:Int =-1
    private var getDiv:Int =-1
    private var totalDonated:Int =-1


    var totDay: Int? =null
    var curDay: Int? =null
    var curMonth: Int? =null
    var curYear: Int? =null
    var day: Int? =null
    var month: Int? =null
    var year: Int? =null
    var calendar: Calendar? =null
    var canYesBeClicked=false


    sealed class myEvent(){
        data class ToastEvent(val mssg: String):myEvent()
        data class NavigateToHomeScreenEvent(val b:Boolean=true):myEvent()
    }

    private val eventChannel= Channel<myEvent>()
    public val eventFlow = eventChannel.receiveAsFlow()

    private fun triggerToastEvent(mssg: String)= viewModelScope.launch{
        eventChannel.send(myEvent.ToastEvent(mssg))
    }
    private fun triggerNavigateToHomeScreenEvent()= viewModelScope.launch{
        eventChannel.send(myEvent.NavigateToHomeScreenEvent())
    }



    constructor(){

        mAuth= FirebaseAuth.getInstance()
        dbUser = FirebaseDatabase.getInstance()
        userRef = dbUser.getReference("users")
        donorRef = dbUser.getReference("donors")
        mAuth = FirebaseAuth.getInstance()

        var userQ=userRef.child(mAuth.currentUser!!.uid)

        try {
            userQ.addListenerForSingleValueEvent(object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        Log.i("here","here")
                        if(snapshot.exists()){
                            val userData=snapshot.getValue(UserData::class.java)
                            getDiv = userData!!.Division
                            getBg = userData!!.BloodGroup

                            val donorQ = donorRef
                                .child(listOfDivisions[getDiv])
                                .child(listOfBloodGroups[getBg])
                                .child(mAuth.currentUser!!.uid)

                            donorQ.addListenerForSingleValueEvent(object: ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if(snapshot.exists()){
                                        val donorData = snapshot.getValue(DonorData::class.java)
                                        totalDonated=donorData!!.TotalDonate
                                        if(donorData!!.TotalDonate==0){
                                            lastDate = "01/01/2001"
                                            totalDonation="Have Never donated yet !"
                                        }else{
                                            lastDate=donorData.LastDonate
                                            totalDonation=donorData.TotalDonate.toString()+" times"
                                        }

                                        totDay=0
                                        if(lastDate.length!=0){
                                            var cnt = 0
                                            var tot = 0
                                            for(i in 0..lastDate.length-1){
                                                if(cnt==0&&lastDate[i]=='/'){
                                                    day = tot
                                                    tot=0
                                                    cnt++
                                                }else if(cnt==1 && lastDate[i]=='/'){
                                                    cnt++
                                                    month=tot
                                                    tot=0
                                                }else{
                                                    tot=tot*10+(lastDate[i]-'0')
                                                }
                                            }
                                            var monthInString="0"
                                            if(month!! <10){
                                                monthInString+=month!!.toString()
                                            }else monthInString=month!!.toString()

                                            var dayInString="0"
                                            if(day!!<10){
                                                dayInString+=day!!.toString()
                                            }else dayInString=day!!.toString()
                                            val sdf=SimpleDateFormat("MM/dd/yyyy",Locale.ENGLISH)
                                            val dateBefore = sdf.parse(month.toString()+"/"+day+"/"+tot)
                                            year=tot


                                            val dateAfter=sdf.parse(sdf.format(Date()))

                                            val timeDiff:Long=Math.abs(dateAfter.time-dateBefore.time)
                                            val daysDiff=TimeUnit.DAYS.convert(timeDiff,TimeUnit.MILLISECONDS)


                                            try{
                                                if(daysDiff>120){
                                                    canYesBeClicked=true
                                                    donateInfo="Have you donated Today?"
                                                    nextDonateVisibility=false
                                                    yesNoVisibility=true

                                                    curDay=calendar!!.get(Calendar.DAY_OF_MONTH)
                                                    curMonth=calendar!!.get(Calendar.MONTH)+1
                                                    curYear=calendar!!.get(Calendar.YEAR)


                                                }else{
                                                    donateInfo="Next donation available in :"
                                                    nextDonateVisibility=true
                                                    yesNoVisibility=false
                                                    nextDonate="${(120-daysDiff!!)} days"
                                                }
                                            }catch (e:Exception){
                                                e.printStackTrace()
                                            }
                                        }
                                    }else{
                                        isNotADonor=true
                                        triggerToastEvent("Update your profile to be a donor first")
                                    }
//                                    pd.dismiss()
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
                        }else{
                            triggerToastEvent("You are not a user. "+ listOfDivisions[getDiv]+
                                    " , "+listOfBloodGroups[getBg])
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.i("User",error.message)
                    }
                }
            )
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    public fun yesClicked(){
        if(!canYesBeClicked) return

        calendar= Calendar.getInstance(TimeZone.getDefault())
        curDay=calendar!!.get(Calendar.DAY_OF_MONTH)
        curMonth=calendar!!.get(Calendar.MONTH)+1
        curYear=calendar!!.get(Calendar.YEAR)

        donorRef.child(listOfDivisions[getDiv])
            .child(listOfBloodGroups[getBg])
            .child(mAuth.currentUser!!.uid)
            .child("LastDonate")
            .setValue(curDay!!.toString() + "/"+curMonth!!+"/"+curYear!!)

        donorRef.child(listOfDivisions[getDiv])
            .child(listOfBloodGroups[getBg])
            .child(mAuth.currentUser!!.uid)
            .child("TotalDonate")
            .setValue(totalDonated+1)

        triggerToastEvent("Data updated")
        triggerNavigateToHomeScreenEvent()
    }
}

class AchievementsScreenViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = AchievementsScreenViewModel() as T
}