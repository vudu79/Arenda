package com.example.navigationexample.presentation.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.repository.ApartmentRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val apartmentRepository: ApartmentRepositoryImpl,
    ) : ViewModel() {

    var currentApartment = MutableLiveData<Appatment>()
    var allApartments: LiveData<List<Appatment>>


    init {
//        allApartments = apartmentRepository.allApartmentsLiveData
        allApartments = apartmentRepository.allApartmentsLD
    }

    fun getAllApartments(){
        apartmentRepository.getAllApartments()
    }
    fun setCurrentApartment(apartment: Appatment) {
        currentApartment.value = apartment
        apartmentRepository.setCurrentApartmentInRepository(apartment.name)
    }

    fun insertAppatment(apartment: Appatment) {
        apartmentRepository.insertApartment(apartment)
    }

    fun deleteAppatment(name: String) {
        apartmentRepository.deleteApartment(name)
    }
}



