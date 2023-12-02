package com.example.navigationexample.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.AppatmentDao
import com.example.navigationexample.data.entity.Appatment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


class ApartmentRepositoryImpl @Inject constructor(private val apartmentDao: AppatmentDao) {

    var allApartmentsLD = MutableLiveData<List<Appatment>>()

    val allApartmentNamesList = allApartmentsLD.value?.map { it.name }

    var currentApartment = MutableLiveData<String>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun setCurrentApartmentInRepository(apartment: String) {
        currentApartment.value = apartment
    }

    fun getApartmentByName(name: String): Appatment {
        return apartmentDao.getApartmentByName(name)
    }

    fun insertApartment(newappatment: Appatment) {
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

    private fun asyncFindAllApartments(): Deferred<List<Appatment>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async apartmentDao.getAllApartment()
        }

}