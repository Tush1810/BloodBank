package com.example.bloodbank.Screens.ProfileScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.bloodbank.Models.UserData
import com.example.bloodbank.Screen
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel() : ViewModel() {
    var name by mutableStateOf("")

    @OptIn(ExperimentalComposeUiApi::class)
    val focusRequester = FocusRequester.Companion.FocusRequesterFactory
    var contactNumber by mutableStateOf("")
    var address by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var buttonText by mutableStateOf("Register")

    var isPasswordVisibilityOn by mutableStateOf(false)
    var isConfirmPasswordVisibilityOn by mutableStateOf(false)

    var isDonor by mutableStateOf(false)
    var isDonorText by mutableStateOf("Mark this to be a donor")

    // sex spinner
    var listOfSexes = listOf("Male", "Female")
    var selectedSex = mutableStateOf(0)

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
    var selectedBloodGroup = mutableStateOf(0)

    //division spinner
    var listOfDivisions = listOf("Delhi", "Punjab")
    var selectedDivision = mutableStateOf(0)

    var emailFieldVisibilityOn by mutableStateOf(true)
    var passwordFieldVisibilityOn by mutableStateOf(true)
    var confirmPasswordFieldVisibilityOn by mutableStateOf(true)
    var logoImageVisibilityOn by mutableStateOf(true)
    var topBarTitle by mutableStateOf("Sign Up")

    lateinit var mAuth: FirebaseAuth
    lateinit var dbRef: DatabaseReference
    lateinit var donorRef: DatabaseReference
    lateinit var dbUser: FirebaseDatabase

    private var isUpdated = true



    private lateinit var context: Context

    sealed class myEvent(){
        data class NavigateEvent(val screen:Screen):myEvent()
        data class BackPressedEvent(val pressed:Boolean=true):myEvent()
    }

    private val eventChannel= Channel<myEvent>()
    public val eventFlow = eventChannel.receiveAsFlow()

    private fun triggerNavigateEvent(screen:Screen)= viewModelScope.launch{
        eventChannel.send(myEvent.NavigateEvent(screen))
    }

    private fun triggerBackPressedEvent() = viewModelScope.launch {
        eventChannel.send(myEvent.BackPressedEvent())
    }



    constructor(context: Context) : this() {

        this.context = context

        dbUser = FirebaseDatabase.getInstance()
        dbRef = dbUser.getReference("users")
        donorRef = dbUser.getReference("donors")
        mAuth = FirebaseAuth.getInstance()

        if (mAuth.getCurrentUser() != null) {
            emailFieldVisibilityOn = false
            passwordFieldVisibilityOn = false
            confirmPasswordFieldVisibilityOn = false
            buttonText = "Update Profile"
            topBarTitle = "Profile"
            logoImageVisibilityOn = false
            isUpdated = false

            var Profile = dbRef.child(mAuth.currentUser!!.uid)
            Log.i("id",mAuth.currentUser!!.uid)
            val listener = object : ValueEventListener {
                override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                    val userData: UserData? = dataSnapshot.getValue(UserData::class.java)
                    if (userData != null) {
//                        pd.show()
                        name = userData.Name
                        address = userData.Address
                        selectedSex.value = userData.Gender
                        selectedBloodGroup.value = userData.BloodGroup
                        selectedDivision.value = userData.Division
                        contactNumber = userData.Contact
                        val donor: Query =
                            donorRef.child(listOfDivisions.get(selectedDivision.value))
                                .child(listOfBloodGroups.get(selectedBloodGroup.value))
                                .child(mAuth.currentUser!!.uid)
                        val listener2=object : ValueEventListener {
                            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    isDonor = true
                                    isDonorText = "Unmark this to leave from donors"
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Your are not a donor! Be a donor and save life by donating blood.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
//                                pd.dismiss()
                            }

                            override fun onCancelled(@NonNull databaseError: DatabaseError) {
                                Log.d("User", databaseError.message)
                            }
                        }
                        donor.addListenerForSingleValueEvent(listener2)
                        donor.removeEventListener(listener2)
                    }

                }

                override fun onCancelled(@NonNull databaseError: DatabaseError) {
                    Log.d("User", databaseError.message)
                }
            }
            Profile.addListenerForSingleValueEvent(listener)

//            Profile.removeEventListener(listener)

        } else {
//            pd.dismiss()
        }
    }


    @OptIn(ExperimentalComposeUiApi::class)
    public fun btnSignUp() {
        try {
            if (name.length <= 2) {
                showError("Name")
                focusRequester.component1().requestFocus()
            } else if (contactNumber.length < 10) {
                showError("Contact Number")
                focusRequester.component3().requestFocus()
            } else if (contactNumber.length < 10) {
                showError("Address")
                focusRequester.component2().requestFocus()
            } else {
                if (isUpdated) {
                    if (email.length == 0) {
                        showError("Email Id")
                        focusRequester.component4().requestFocus()
                    } else if (password.length < 5) {
                        showError("Password")
                        focusRequester.component5().requestFocus()
                    } else if (!password.equals(confirmPassword)) {
                        Toast.makeText(
                            context, "Password did not match", Toast.LENGTH_LONG
                        ).show()
                        focusRequester.component6().requestFocus()
                    } else {
//                    pd.show()
                        mAuth.createUserWithEmailAndPassword(
                            email,
                            password
                        ).addOnCompleteListener(
//                            context,
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Registration failed! try agian.",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    Log.v("error", task.exception!!.message!!)
                                } else {
                                    var id = task.result.user!!.uid
                                    dbRef.child(id).child("Name").setValue(name)
                                    dbRef.child(id).child("Gender")
                                        .setValue(selectedSex.value)
                                    dbRef.child(id).child("Contact").setValue(contactNumber)
                                    dbRef.child(id).child("BloodGroup")
                                        .setValue(selectedBloodGroup.value)
                                    dbRef.child(id).child("Division")
                                        .setValue(selectedDivision.value)
                                    dbRef.child(id).child("Address").setValue(address)

                                    if (isDonor) {
                                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                                            .child(id)
                                            .child("UID")
                                            .setValue(id)
                                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                                            .child(id)
                                            .child("LastDonate")
                                            .setValue("Don't donate yet!")
                                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                                            .child(id)
                                            .child("TotalDonate")
                                            .setValue(0)
                                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                                            .child(id)
                                            .child("Name")
                                            .setValue(name)
                                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                                            .child(id)
                                            .child("Contact")
                                            .setValue(contactNumber)
                                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                                            .child(id)
                                            .child("Address")
                                            .setValue(address)
                                    }

                                    Toast.makeText(
                                        context, "Welcome, your account has been Created!",
                                        Toast.LENGTH_SHORT
                                    )
                                    triggerNavigateEvent(Screen.DashboardScreen)
//                                pd.dismiss()
                                }

                            }
                        )
                    }

                } else {
                    var id = mAuth.currentUser!!.uid
                    dbRef.child(id).child("Name").setValue(name)
                    dbRef.child(id).child("Gender").setValue(selectedSex.value)
                    dbRef.child(id).child("Contact").setValue(contactNumber)
                    dbRef.child(id).child("BloodGroup")
                        .setValue(selectedBloodGroup.value)
                    dbRef.child(id).child("Division")
                        .setValue(selectedDivision.value)
                    dbRef.child(id).child("Address").setValue(address)

                    if (isDonor) {
                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                            .child(id)
                            .child("UID")
                            .setValue(id)
                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                            .child(id)
                            .child("LastDonate")
                            .setValue("Don't donate yet!")
                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                            .child(id)
                            .child("TotalDonate")
                            .setValue(0)
                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                            .child(id)
                            .child("Name")
                            .setValue(name)
                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                            .child(id)
                            .child("Contact")
                            .setValue(contactNumber)
                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                            .child(id)
                            .child("Address")
                            .setValue(address)
                    } else {
                        donorRef.child(listOfDivisions.get(selectedDivision.value))
                            .child(listOfBloodGroups.get(selectedBloodGroup.value))
                            .child(id)
                            .removeValue()
                    }

                    Toast.makeText(
                        context, "Welcome, your account has been Created!",
                        Toast.LENGTH_LONG
                    )
                    triggerNavigateEvent(Screen.DashboardScreen)
//                                pd.dismiss()

                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun showError(error: String) {
        Toast.makeText(context, "Please, Enter a valid " + error, Toast.LENGTH_LONG).show()
    }

    public fun onBackPressed() {
        triggerBackPressedEvent()
    }
}

class ProfileScreenViewModelFactory(private val context:Context) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = ProfileScreenViewModel(context) as T
}