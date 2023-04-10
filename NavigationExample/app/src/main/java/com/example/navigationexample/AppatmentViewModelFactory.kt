package com.example.navigationexample

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.navigationexample.presentation.screens.AppatmentViewModel


class AppatmentViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AppatmentViewModel(application) as T
    }
}