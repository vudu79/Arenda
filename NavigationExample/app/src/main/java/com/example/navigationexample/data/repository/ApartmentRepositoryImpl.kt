package com.example.navigationexample.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.ApartmentDao
import com.example.navigationexample.data.entity.Apartment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


class ApartmentRepositoryImpl @Inject constructor(private val apartmentDao: ApartmentDao) {

    var allApartmentsLD = MutableLiveData<List<Apartment>>()

    var currentApartment = MutableLiveData<String>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun setCurrentApartmentInRepository(apartment: String) {
        currentApartment.value = apartment
    }

    fun getApartmentByName(name: String): Apartment {
        return apartmentDao.getApartmentByName(name)
    }

    fun insertApartment(newappatment: Apartment) {
        coroutineScope.launch(Dispatchers.IO) {
            apartmentDao.insertApartment(newappatment)
        }
    }

    fun deleteApartment(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            apartmentDao.deleteApartment(name)
        }
    }

    fun getAllApartments() {
        coroutineScope.launch(Dispatchers.Main) {
            allApartmentsLD.value = asyncFindAllApartments().await()
        }
    }

    private fun asyncFindAllApartments(): Deferred<List<Apartment>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async apartmentDao.getAllApartment()
        }

}