package com.example.navigationexample.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationexample.data.entity.Apartment
import com.example.navigationexample.data.repository.ApartmentRepositoryImpl
import com.example.navigationexample.presentation.screens.common.ScaffoldSet
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ApartmentViewModel @Inject constructor(
    private val apartmentRepository: ApartmentRepositoryImpl,
    ) : ViewModel() {

    var currentApartment = MutableLiveData<Apartment>()
    var allApartments: LiveData<List<Apartment>>

    private var _scaffoldSettings = MutableLiveData<ScaffoldSet>(ScaffoldSet())
    var scaffoldSettings: LiveData<ScaffoldSet> = _scaffoldSettings

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

    fun setScaffoldSettings(top: Boolean, bottom:Boolean, fab: Boolean){
        _scaffoldSettings.value?.isTopBarActive = top
        _scaffoldSettings.value?.isBottomBarActive = bottom
        _scaffoldSettings.value?.isFABActive = fab
    }
}



