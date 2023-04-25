package com.example.navigationexample.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import com.example.navigationexample.data.dao.ClientDao
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.screens.common.listDaysBetween
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject


class ClientsRepositoryImpl @Inject constructor(private val clientDao: ClientDao) {

    var dateClientMap: MutableMap<LocalDate, MutableSet<Client>> = mutableMapOf()
    val allClients: LiveData<List<Client>> = clientDao.getAllClients()
    val allAppatmentClients = MutableLiveData<List<Client>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertClient(newClient: Client) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.insertClient(newClient)
        }
    }

    fun getClient(name: String): Client {
        return clientDao.getClient(name)
    }

    fun deleteClient(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.deleteClient(name)
        }
    }


    fun getAppatmentClients(appatmentName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            allAppatmentClients.value = asyncFind(appatmentName).await()
//            dateClientMap.clear()
//            val clients = allAppatmentClients.value
//            clients?.let { it ->
//                val fff: MutableList<Client> = mutableListOf()
//                it.forEach { client ->
//                    val startDay = LocalDate.ofEpochDay(client.inDate!!)
//                    val endDay = LocalDate.ofEpochDay(client.outDate!!)
//                    val clientPeriod = listDaysBetween(startDay, endDay)
//                    clientPeriod.forEach { day ->
//                        run {
//                            if (dateClientMap.containsKey(day)) {
//                                Log.d("myTag", "условие сработало мап = $dateClientMap")
//                                fff.addAll(dateClientMap[day]!!)
//                                Log.d("myTag", "добавил в fff лист из мапы = $fff")
//                                fff.add(client)
//                                Log.d("myTag", "добавил в fff клиента = $fff")
//                                dateClientMap[day]?.addAll(fff)
//                                Log.d("myTag", "обновил мапу = $dateClientMap")
//
//                                fff.clear()
////                                Log.d("myTag", " fff -----  $fff")
////                                Log.d("myTag", " map -----  ${dateClientMap[day]?.size}")
//                            } else {
//                                dateClientMap.put(day, mutableSetOf(client))
//                            }
//
//
//                        }
//                    }
//                }
//            }
        }
    }


    private fun asyncFind(appatmentName: String): Deferred<List<Client>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async clientDao.getAppatmentClients(appatmentName)
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