package com.example.navigationexample.domain.usecase

import com.example.navigationexample.data.repository.DaysRepositoryImpl
import javax.inject.Inject

class getChartData @Inject constructor(
    private val xList: List<Float>,
    private val yList: List<Float>
) {
}
