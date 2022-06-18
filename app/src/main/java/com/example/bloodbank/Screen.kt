package com.example.bloodbank

import com.example.bloodbank.Navigation.NavigationDestination

sealed class Screen(override var route:String):NavigationDestination{
    object SplashScreen:Screen("splash_screen")
    object LoginScreen:Screen("login_screen")
    object ProfileScreen:Screen("profile_screen")
    object RestorePasswordScreen:Screen("restore_password_screen")
    object DashboardScreen:Screen("dashboard_screen")
    object PostScreen:Screen("post_screen")
}
