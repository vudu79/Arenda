package com.example.navigationexample.domain.usecase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.entity.RentalDay
import com.example.navigationexample.data.repository.ClientsRepositoryImpl
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import com.example.navigationexample.presentation.screens.common.listDaysBetween
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class GetDayClientMapUseCase @Inject constructor(
    private val daysRepositoryImpl: DaysRepositoryImpl,
    private val clientsRepositoryImpl: ClientsRepositoryImpl
) {


    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private var allAppatmentDays: List<RentalDay>? = listOf()
    fun invoke(appatmentName: String): MutableMap<LocalDate, MutableSet<Client>>{
        val dateClientMap: MutableMap<LocalDate, MutableSet<Client>> = mutableMapOf()
        daysRepositoryImpl.getAppatmentDays(appatmentName)
        allAppatmentDays = daysRepositoryImpl.allAppatmentDays
        allAppatmentDays?.forEach {
            val localDay = LocalDate.ofEpochDay(it.epochDay)
            val client = clientsRepositoryImpl.getClient(it.clientName)
            dateClientMap[localDay]?.add(client)
        }
        return dateClientMap
    }
}