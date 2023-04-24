package com.example.navigationexample.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.RentalDaysDao
import com.example.navigationexample.data.entity.RentalDay
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject


class DaysRepositoryImpl @Inject constructor(private val rentalDaysDao: RentalDaysDao) {


    val allClientDays = MutableLiveData<List<RentalDay>>()
    val allAppatmentDays = MutableLiveData<List<RentalDay>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertClient(newDay: RentalDay) {
        coroutineScope.launch(Dispatchers.IO) {
            rentalDaysDao.insertDay(newDay)
        }
    }

    fun deleteClient(clientName: String) {
        coroutineScope.launch(Dispatchers.IO) {
            rentalDaysDao.deleteClientDays(clientName)
        }
    }

    fun getClientDays(appatmentName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            allClientDays.value = asyncFindClientDays(appatmentName).await()
        }
    }

    private fun asyncFindClientDays(clientName: String): Deferred<List<RentalDay>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async rentalDaysDao.getClientDays(clientName)
        }


    fun getAppatmentDays(appatmentName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            allAppatmentDays.value = asyncFindAppatmentDays(appatmentName).await()
        }
    }

    private fun asyncFindAppatmentDays(appatmentName: String): Deferred<List<RentalDay>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async rentalDaysDao.getAllAppatmentDays(appatmentName)
        }


    fun listDaysBetween(
        startDate: LocalDate? = null,
        endDate: LocalDate? = null
    ): MutableList<LocalDate> {
        val listDays: MutableList<LocalDate> = mutableListOf()
        val numOfDaysBetween = (ChronoUnit.DAYS.between(startDate, endDate)).toInt()
        (0..numOfDaysBetween).forEach {
            val date: LocalDate? = startDate?.plusDays(it.toLong())
            if (date != null) {
                listDays.add(date)
            }
        }
        return listDays
    }


}