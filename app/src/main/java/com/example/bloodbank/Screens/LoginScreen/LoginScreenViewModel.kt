package com.example.bloodbank.Screens.LoginScreen

import android.content.Context
import android.util.Log
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

class LoginScreenViewModel():ViewModel() {
    var emailText by mutableStateOf("")
    var passwordText by mutableStateOf("")
    var isVisibilityOn by mutableStateOf(false)

    sealed class myEvent(){
        data class navigateEvent(val screen:Screen):myEvent()
    }

    private val eventChannel= Channel<myEvent>()
    public val eventFlow = eventChannel.receiveAsFlow()

    private fun triggerNavigateEvent(screen:Screen)= viewModelScope.launch{
        eventChannel.send(myEvent.navigateEvent(screen))
    }


    lateinit var mAuth:FirebaseAuth


    private lateinit var context: Context

    constructor(context:Context) : this() {
        this.context=context
        mAuth= FirebaseAuth.getInstance()
        if(mAuth.currentUser!=null){
            triggerNavigateEvent(Screen.DashboardScreen)
        }
    }




    public fun signInClicked(){
        try {
            if (passwordText.length > 0 && emailText.length > 0) {
//                pd.show()
                mAuth.signInWithEmailAndPassword(emailText, passwordText)
                    .addOnCompleteListener(){ task ->
                        if (!task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Authentication Failed",
                                    Toast.LENGTH_LONG
                                ).show()
                            Log.v("error", task.exception!!.message!!)
                        } else {
                            triggerNavigateEvent(Screen.DashboardScreen)
                        }
                    }

            } else {
                Toast.makeText(
                    context,
                    "Please fill all the field.",
                    Toast.LENGTH_LONG
                ).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    public fun signUpClicked(){
        triggerNavigateEvent(Screen.ProfileScreen)
    }

    public fun resetPassword(){
        triggerNavigateEvent(Screen.RestorePasswordScreen)
    }


}

class LoginScreenViewModelFactory(private val context:Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = LoginScreenViewModel(context) as T
}