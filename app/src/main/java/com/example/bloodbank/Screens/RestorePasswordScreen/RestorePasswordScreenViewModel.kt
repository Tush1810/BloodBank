package com.example.bloodbank.Screens.RestorePasswordScreen

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bloodbank.Screen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class RestorePasswordScreenViewModel(): ViewModel() {
    var registeredEmail by mutableStateOf("")
    var isVisibilityOn by mutableStateOf(false)

    sealed class myEvent(){
        data class BackPressedEvent(val pressed:Boolean=true):myEvent()
    }

    private val eventChannel= Channel<myEvent>()
    public val eventFlow = eventChannel.receiveAsFlow()

    private fun triggerBackPressedEvent() = viewModelScope.launch {
        eventChannel.send(myEvent.BackPressedEvent())
    }

    lateinit var mAuth: FirebaseAuth
    private lateinit var context: Context

    constructor(context:Context):this(){
        this.context=context
        mAuth= FirebaseAuth.getInstance()
    }

    fun btnClicked(){
        if(TextUtils.isEmpty(registeredEmail)){
            Toast.makeText(context,"Email Required!",Toast.LENGTH_SHORT).show();
        }else{
//            pd.show()
            mAuth.sendPasswordResetEmail(registeredEmail)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            context,
                            "We have sent an email to $registeredEmail. Please check your email.",
                            Toast.LENGTH_LONG
                        ).show()
                        triggerBackPressedEvent()
                    } else {
                        Toast.makeText(
                            context,
                            "Sorry, There is something went wrong. please try again some time later.",
                            Toast.LENGTH_LONG
                        ).show()
                        registeredEmail=""
                    }
//                    pd.dismiss()
                }
        }
    }
}

class RestorePasswordScreenViewModelFactory(private val context:Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = RestorePasswordScreenViewModel(context) as T
}