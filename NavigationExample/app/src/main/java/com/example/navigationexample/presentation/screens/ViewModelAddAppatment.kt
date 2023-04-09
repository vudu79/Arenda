package com.example.navigationexample.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class ViewModelAddAppatment : ViewModel() {
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var password by mutableStateOf("")
    var mobileNumber by mutableStateOf("")
    var mobileCountryCode by mutableStateOf("")
    var dateOfBirth by mutableStateOf("")

}