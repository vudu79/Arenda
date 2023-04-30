package com.example.navigationexample.domain.usecase

import android.util.Log
import com.example.navigationexample.data.entity.RentalDay
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import com.example.navigationexample.domain.models.ClientMonk
import java.time.LocalDate
import javax.inject.Inject

class GetDayClientMapUseCase @Inject constructor(
    private val daysRepositoryImpl: DaysRepositoryImpl,
) {

    private var allAppatmentDays: List<RentalDay>? = listOf()
    private var dateClientMap: MutableMap<LocalDate, MutableSet<ClientMonk>> = mutableMapOf()
    fun invoke(appatmentName: String): MutableMap<LocalDate, MutableSet<ClientMonk>> {

        allAppatmentDays = daysRepositoryImpl.allAppatmentDays.value

        Log.d("myTag", "from UseCase  - $allAppatmentDays")
        allAppatmentDays?.forEach {
            val localDay = LocalDate.ofEpochDay(it.epochDay)
            Log.d("myTag", "День  - $localDay")
            val clientMonk = ClientMonk(it.clientName, it.appatmentName, it.clientColor)

            if (dateClientMap.containsKey(localDay)) {
                dateClientMap[localDay]?.add(clientMonk)
            } else {
                dateClientMap[localDay] = mutableSetOf(clientMonk)
            }
        }
        return dateClientMap
    }
}