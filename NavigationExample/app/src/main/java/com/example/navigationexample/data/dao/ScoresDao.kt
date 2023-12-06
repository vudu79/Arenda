package com.example.navigationexample.data.dao

import androidx.room.*
import com.example.navigationexample.constants.ScoreType
import com.example.navigationexample.data.entity.Relations.CategoryWithScores
import com.example.navigationexample.data.entity.Score
import com.example.navigationexample.data.entity.ScoreCategory
import kotlinx.coroutines.flow.Flow


@Dao
interface ScoresDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScore(score: Score): Long

    @Delete
    suspend fun deleteScore(score: Score)
    @Transaction
    @Query("SELECT * FROM score_category, score WHERE apartment = :apartment AND score_date = :day AND type = :type")
    fun getAllCatWithScoresForDay(apartment: String, day:Long, type: ScoreType): Flow<List<CategoryWithScores>>
    @Transaction

    @Query("SELECT * FROM score_category, score WHERE apartment = :apartment AND score_date >= :dayStart AND score_date <= :dayEnd AND type = :type")
    fun getAllCatWithScoresForPeriod(apartment: String, dayStart:Long, dayEnd:Long, type: ScoreType): Flow<List<CategoryWithScores>>

    @Transaction
    @Query("SELECT * FROM score_category WHERE category = :category")
    fun getCategoriesWithScores(category: String): Flow<List<CategoryWithScores>>

    @Transaction
    @Query("SELECT * FROM score_category WHERE category = :category")
    fun getCategory(category: String): ScoreCategory

}