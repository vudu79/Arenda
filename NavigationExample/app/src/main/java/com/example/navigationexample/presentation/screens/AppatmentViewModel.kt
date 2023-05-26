package com.example.navigationexample.presentation.screens

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.repository.AppatmentRepositoryImpl
import com.example.navigationexample.data.repository.ClientsRepositoryImpl
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AppatmentViewModel @Inject constructor(
    private val apartmentRepository: AppatmentRepositoryImpl,
    private val clientRepository: ClientsRepositoryImpl,
    private val daysRepository: DaysRepositoryImpl,

) : ViewModel() {

    var currentApartment = MutableLiveData<Appatment>()
    val allApartments: LiveData<List<Appatment>>


    init {
        allApartments = apartmentRepository.allAppatment
    }

    fun setCurrentAppatment(appatment: Appatment) {
        currentApartment.value = appatment
//        // Log.d("myTag", "щелчок по объекту   -   ${currentApartment.value!!.name}")
    }

    fun insertAppatment(appatment: Appatment) {
        apartmentRepository.insertAppatment(appatment)
    }

    fun deleteAppatment(name: String) {
        apartmentRepository.deleteAppatment(name)
    }
}



