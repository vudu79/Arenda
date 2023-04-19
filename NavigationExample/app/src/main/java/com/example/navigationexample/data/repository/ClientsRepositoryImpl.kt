package com.example.navigationexample.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.dao.ClientDao
import com.example.navigationexample.data.entity.Client
import kotlinx.coroutines.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit


class ClientsRepositoryImpl(private val clientDao: ClientDao) {


    var dateClientMap: MutableMap<LocalDate, Client> = mutableMapOf()
    val allClients: LiveData<List<Client>> = clientDao.getAllClients()
    val allAppatmentClients = MutableLiveData<List<Client>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertClient(newClient: Client) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.insertClient(newClient)
        }
    }

    fun deleteClient(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            clientDao.deleteClient(name)
        }
    }


    fun getAppatmentClients(appatmentName: String) {
        coroutineScope.launch(Dispatchers.Main) {
            allAppatmentClients.value = asyncFind(appatmentName).await()
            val clients = allAppatmentClients.value
            clients?.let { it ->
                it.forEach { client ->
                    val startDay = LocalDate.ofEpochDay(client.inDate!!)
                    val endDay = LocalDate.ofEpochDay(client.outDate!!)
                    val clientPeriod = listDaysBetween(startDay, endDay)
                    clientPeriod.forEach { day -> dateClientMap.put(day, client) }
                }
            }
        }
    }

//
//    fun getDateClientMap(appatmentName: String) {
//        coroutineScope.launch(Dispatchers.Main) {
//            allAppatmentClients.value = asyncFind(appatmentName).await()
//
//        }
//    }


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