package com.example.navigationexample.domain.usecase

import android.util.Log
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import java.time.LocalDate
import javax.inject.Inject

class getAppatmentPlanedDaysUseCase @Inject constructor(private val daysRepositoryImpl: DaysRepositoryImpl) {
    fun invoke(appatmentName: String): MutableList<LocalDate> {
        val rentalDaysList = daysRepositoryImpl.getAppatmentDays(appatmentName)
        Log.d("myTag", "лист rentalDays - $rentalDaysList")
        val localDays: MutableList<LocalDate> = mutableListOf()
        rentalDaysList?.let {
            it.forEach { rentalDay ->
                localDays.add(LocalDate.ofEpochDay(rentalDay.epochDay))
            }
        }
        Log.d("myTag", "лист localdate - $localDays")
        return if (localDays.isEmpty()) mutableListOf(LocalDate.now().plusDays(2)) else localDays
    }
}
