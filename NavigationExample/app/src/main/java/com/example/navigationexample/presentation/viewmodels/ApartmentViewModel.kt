package com.example.navigationexample.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Apartment
import com.example.navigationexample.data.repository.ApartmentRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val apartmentRepository: ApartmentRepositoryImpl,
    ) : ViewModel() {

    var currentApartment = MutableLiveData<Apartment>()
    var allApartments: LiveData<List<Apartment>>


    init {
        allApartments = apartmentRepository.allApartmentsLD
    }

    fun getAllApartments(){
        apartmentRepository.getAllApartments()
    }

    fun setCurrentApartment(apartment: Apartment) {
        currentApartment.value = apartment
        apartmentRepository.setCurrentApartmentInRepository(apartment.name)
    }

    fun insertApartment(apartment: Apartment) {
        apartmentRepository.insertApartment(apartment)
    }

    fun deleteApartment(name: String) {
        apartmentRepository.deleteApartment(name)
    }
}



