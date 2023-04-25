package com.example.navigationexample.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.RentalDaysDao
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.entity.RentalDay
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject


class DaysRepositoryImpl @Inject constructor(private val rentalDaysDao: RentalDaysDao) {


    val allClientDays = MutableLiveData<List<RentalDay>>()
    var allAppatmentDays = MutableLiveData<List<RentalDay>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertOneDay(newDay: RentalDay) {
        coroutineScope.launch(Dispatchers.IO) {
            rentalDaysDao.insertDay(newDay)
        }
    }

    fun insertClientDays(client: Client) {
        val startDay = LocalDate.ofEpochDay(client.inDate!!)
        val endDay = LocalDate.ofEpochDay(client.outDate!!)
        val clientPeriod = listDaysBetween(startDay, endDay)

        coroutineScope.launch(Dispatchers.IO) {
            clientPeriod.forEach {
                rentalDaysDao.insertDay(
                    RentalDay(
                        it.toEpochDay(),
                        client.clientColor,
                        client.name,
                        client.appatment_name
                    )
                )
            }
        }
    }


    fun deleteClientDays(clientName: String) {
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


    fun getAppatmentDays(appatmentName: String): List<RentalDay>? {
        coroutineScope.launch(Dispatchers.Main) {
            allAppatmentDays.value =  asyncFindAppatmentDays(appatmentName).await()

        }
        Log.d("myTag", "сработал allAppatmentDays - -   ${allAppatmentDays.value}")
        return allAppatmentDays.value
    }

    private fun asyncFindAppatmentDays(appatmentName: String): Deferred<List<RentalDay>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async rentalDaysDao.getAppatmentDays(appatmentName)
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