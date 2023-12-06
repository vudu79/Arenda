package com.example.navigationexample.data.entity.Relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.navigationexample.data.entity.Score
import com.example.navigationexample.data.entity.ScoreCategory

data class CategoryWithScores(
    @Embedded
    val scoreCategory: ScoreCategory,

    @Relation(
        parentColumn = "category",
        entityColumn = "score_category"
    )
    val scores: List<Score>
)
