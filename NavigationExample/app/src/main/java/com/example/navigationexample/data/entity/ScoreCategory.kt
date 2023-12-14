package com.example.navigationexample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.navigationexample.domain.constants.ScoreType

@Entity(tableName = "score_category")
data class ScoreCategory (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id:Int = 0,

    @ColumnInfo("category")
    var category:String = "",

    @ColumnInfo("type")
    var type: ScoreType = ScoreType.EXPENSES

)