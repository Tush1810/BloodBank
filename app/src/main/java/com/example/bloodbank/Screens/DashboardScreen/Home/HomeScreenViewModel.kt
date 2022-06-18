package com.example.bloodbank.Screens.DashboardScreen.Home

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bloodbank.Models.CustomerUserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeScreenViewModel():ViewModel() {

    private lateinit var mAuth:FirebaseAuth
    private lateinit var donorRef:DatabaseReference
    var postLists by mutableStateOf(mutableListOf<CustomerUserData>())


    private lateinit var context: Context

    constructor(context: Context) : this() {
        this.context=context

        donorRef=FirebaseDatabase.getInstance().getReference()
        postLists= mutableListOf()

        mAuth= FirebaseAuth.getInstance()

        var allPosts = donorRef.child("posts")
//        pd.show()
        allPosts.addListenerForSingleValueEvent(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if(snapshot.exists()){
                        for(singlePost in snapshot.children){
                            var customerUserData=singlePost.getValue(CustomerUserData::class.java)
                            postLists.add(customerUserData!!)
                        }
                    }else{
                        Toast.makeText(
                            context,
                            "Database is empty now!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("User", error.message)
                }
            }
        )
    }
}

class HomeScreenViewModelFactory(private val context:Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = HomeScreenViewModel(context) as T
}