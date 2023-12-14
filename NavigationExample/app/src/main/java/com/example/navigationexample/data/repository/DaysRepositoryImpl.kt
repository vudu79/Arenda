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


class DaysRepositoryImpl @Inject constructor(
    private val rentalDaysDao: RentalDaysDao,
    private val clientsRepositoryImpl: ClientsRepositoryImpl
) {


    var allAppatmentDays = MutableLiveData<List<RentalDay>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)




    fun insertClientDays(client: Client, clientId: Long) {
        val startDay = LocalDate.ofEpochDay(client.inDate)
        val endDay = LocalDate.ofEpochDay(client.outDate)
        val clientPeriod = listDaysBetween(startDay, endDay)
//        val startDayEpoch = startDay.toEpochDay()
//        val endDayEpoch = endDay.toEpochDay()
        coroutineScope.launch(Dispatchers.IO) {
            val allApartmentRentalDate = rentalDaysDao.getAppatmentDays(client.appatmentName)
            val allLocalDays = allApartmentRentalDate.map {
                LocalDate.ofEpochDay(it.epochDay)
            }
            clientPeriod.forEach {
                val isStart: Boolean = if (it == startDay) true else false
                val isEnd: Boolean = if (it == endDay) true else false
                val isEnable: Boolean =
                    if ((it == startDay && !allLocalDays.contains(startDay.minusDays(1))) ||
                        (it == endDay && !allLocalDays.contains(endDay.plusDays(1)))
                    ) true else false
                rentalDaysDao.insertDay(
                    RentalDay(
                        epochDay = it.toEpochDay(),
                        clientColor = client.clientColor,
                        clientId = clientId,
                        isStartDay = isStart,
                        isEndDay = isEnd,
                        isEnable = isEnable,
                        appatmentName = client.appatmentName
                    )
                )
            }
        }
    }


    fun deleteClientDays(clientId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            rentalDaysDao.deleteClientDays(clientId)
        }
    }

//    fun getClientDays(clientPhone: String) {
//        coroutineScope.launch(Dispatchers.Main) {
//            allClientDays.value = asyncFindClientDays(clientPhone).await()
//        }
//    }
//
//    private fun asyncFindClientDays(clientPhone: String): Deferred<List<RentalDay>?> =
//        coroutineScope.async(Dispatchers.IO) {
//            return@async rentalDaysDao.getClientDays(clientPhone)
//        }


    @WorkerThread
    fun updateClientDays(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: () -> Unit,
        client: Client,
        result: Int
    ) = flow {
        deleteClientDays(client.id)
        insertClientDays(client, client.id)
        if (result < 0) {
            onError()
        } else {
            emit(1)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)


    @WorkerThread
    fun fetchAppatmentRentalDays(
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: () -> Unit,
        appatmentName: String
    ) = flow {
        val rentalDaysList: List<RentalDay> = rentalDaysDao.getAppatmentDays(appatmentName)
        if (rentalDaysList.isEmpty()) {
            onError()
        } else {
            val localDays: MutableList<LocalDate> = mutableListOf()
            rentalDaysList.let {
                it.forEach {
                    if (!it.isEnable) localDays.add(LocalDate.ofEpochDay(it.epochDay))
                }
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
            Log.d("myTag", "$rentalDaysList")
            rentalDaysList.forEach {
                val localDay = LocalDate.ofEpochDay(it.epochDay)
                // Log.d("myTag", "День  - $localDay")
                val client = clientsRepositoryImpl.getClientById(it.clientId)
                val clientMonk = ClientMonk(
                    client,
                    it.appatmentName,
                    it.clientColor,
                    it.isEnable,
                    it.isStartDay,
                    it.isEndDay
                )

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

