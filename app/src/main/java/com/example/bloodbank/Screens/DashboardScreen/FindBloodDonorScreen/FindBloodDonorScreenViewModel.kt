package com.example.bloodbank.Screens.DashboardScreen.FindBloodDonorScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bloodbank.Models.DonorData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FindBloodDonorScreenViewModel: ViewModel {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var fdb:FirebaseDatabase
    private lateinit var donorRef:DatabaseReference

    var listOfBloodGroups=listOf(
        "A+",
        "A-",
        "B+",
        "B-",
        "AB+",
        "AB-",
        "O+",
        "O-"
    )
    var selectedBloodGroupIndex= mutableStateOf(0)

    var listOfDivisions=listOf("Delhi", "Punjab")
    var selectedDivisionsIndex= mutableStateOf(0)

    var donorsList by mutableStateOf(mutableListOf<DonorData>())


    sealed class myEvent(){
        data class ToastEvent(val mssg: String):myEvent()
    }

    private val eventChannel= Channel<myEvent>()
    public val eventFlow = eventChannel.receiveAsFlow()

    private fun triggerToastEvent(mssg: String)= viewModelScope.launch{
        eventChannel.send(myEvent.ToastEvent(mssg))
    }

    constructor(){
        mAuth=FirebaseAuth.getInstance()
        fdb= FirebaseDatabase.getInstance()
        firebaseUser=mAuth.currentUser!!
        donorRef=fdb.getReference("donors")
    }

    fun btnClicked(){
//        pd.show()
        donorsList.clear()
        val qpath=donorRef.child(listOfDivisions.get(selectedDivisionsIndex.value))
            .child(listOfBloodGroups.get(selectedBloodGroupIndex.value))

        qpath.addListenerForSingleValueEvent(
            object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (singleDonor in snapshot.children) {
                            val donorData = singleDonor.getValue(DonorData::class.java)!!
                            donorsList.add(donorData)
                        }
                    } else {
                        triggerToastEvent("Database is empty now !")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("User",error.message)
                }
            }
        )
    }
}

class FindBloodDonorScreenViewModelFactory() :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = FindBloodDonorScreenViewModel() as T
}