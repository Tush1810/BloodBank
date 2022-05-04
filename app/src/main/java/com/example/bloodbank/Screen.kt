package com.example.bloodbank

sealed class Screen(var route:String){
    object SplashScreen:Screen("splash_screen")
    object LoginScreen:Screen("login_screen")
    object ProfileScreen:Screen("profile_screen")
    object RestorePasswordScreen:Screen("restore_password_screen")
    object DashboardScreen:Screen("restore_password_screen")
}
