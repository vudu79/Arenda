package com.example.navigationexample.domain.usecase

import android.util.Log
import com.example.navigationexample.data.repository.DaysRepositoryImpl
import java.time.LocalDate
import javax.inject.Inject

class getAppatmentPlanedDaysUseCase @Inject constructor(private val daysRepositoryImpl: DaysRepositoryImpl) {
}
