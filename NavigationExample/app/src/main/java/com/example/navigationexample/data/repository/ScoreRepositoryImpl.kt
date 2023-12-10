package com.example.navigationexample.data.repository

import com.example.navigationexample.data.dao.ScoresDao
import com.example.navigationexample.data.entity.Score
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class ScoreRepositoryImpl @Inject constructor(private val scoresDao: ScoresDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun addScore(score: Score) {
        coroutineScope.launch(Dispatchers.IO) {
            scoresDao.insertScore(score)
        }
    }

    fun updateClientIncomeScore(scoreVal: Int, date: Long, clientId: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            scoresDao.updateClientIncomeScore(scoreVal, date, clientId)
        }
    }
}