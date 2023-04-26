package com.example.navigationexample.domain.usecase

import com.example.navigationexample.data.repository.DaysRepositoryImpl
import java.time.LocalDate
import javax.inject.Inject

class getAppatmentPlanedDaysUseCase @Inject constructor(private val daysRepositoryImpl: DaysRepositoryImpl) {
    fun invoke(appatmentName: String): MutableList<LocalDate> {
        val rentalDaysList = daysRepositoryImpl.getAppatmentDays(appatmentName)
        val localDays: MutableList<LocalDate> = mutableListOf()
        rentalDaysList?.let {
            it.forEach { rentalDay ->
                localDays.add(LocalDate.ofEpochDay(rentalDay.epochDay))
            }
        }
        return if (localDays.isEmpty()) mutableListOf(LocalDate.now()) else localDays
    }
}