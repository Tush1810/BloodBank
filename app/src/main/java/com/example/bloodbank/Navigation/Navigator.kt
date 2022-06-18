package com.example.bloodbank.Navigation

import com.example.bloodbank.Screen
import kotlinx.coroutines.flow.MutableStateFlow

class Navigator {
    var destination: MutableStateFlow<NavigationDestination>
    = MutableStateFlow(Screen.LoginScreen)

    fun navigate(destination: NavigationDestination) {
        this.destination.value = destination
    }
}