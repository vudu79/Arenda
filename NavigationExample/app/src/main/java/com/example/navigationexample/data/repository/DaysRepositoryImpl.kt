package com.example.navigationexample.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.RentalDaysDao
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.entity.RentalDay
import com.example.navigationexample.domain.models.ClientMonk
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
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


    @WorkerThread
    fun fetchAppatmentRentalDaysMap(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: () -> Unit
    ) = flow {
        val rentalDaysList: List<RentalDay> = rentalDaysDao.getAllRentalDays()
        if (rentalDaysList.isEmpty()) {
            onError()

        } else {
            val localDays: Map<String, List<LocalDate>> = rentalDaysList.let { day ->
                day.groupBy(
                    keySelector = { it.appatmentName },
                    valueTransform = { LocalDate.ofEpochDay(it.epochDay) }
                )
            }
            emit(localDays)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)


    @WorkerThread
    fun fetchRentalDayClientMocKMap(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: () -> Unit,
        apartmentName: String
    ) = flow {
        val rentalDaysList: List<RentalDay> = rentalDaysDao.getAppatmentDays(apartmentName)
        if (rentalDaysList.isEmpty()) {
            onError()

        } else {
            val dateClientMap: MutableMap<LocalDate, MutableSet<ClientMonk>> = mutableMapOf()

            rentalDaysList.forEach {
                val localDay = LocalDate.ofEpochDay(it.epochDay)
                Log.d("myTag", "День  - $localDay")
                val clientMonk = ClientMonk(it.clientName, it.appatmentName, it.clientColor)

                if (dateClientMap.containsKey(localDay)) {
                    dateClientMap[localDay]?.add(clientMonk)
                } else {
                    dateClientMap[localDay] = mutableSetOf(clientMonk)
                }
            }
            emit(dateClientMap)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)


    //
//    fun getAppatmentDays(appatmentName: String) {
//        coroutineScope.launch(Dispatchers.Main) {
//            allAppatmentDays.value = asyncFindAppatmentDays(appatmentName).await()
//        }
//
//    }
//
//    private fun asyncFindAppatmentDays(appatmentName: String): Deferred<List<RentalDay>?> =
//        coroutineScope.async(Dispatchers.IO) {
//            return@async rentalDaysDao.getAppatmentDays(appatmentName)
//        }


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

