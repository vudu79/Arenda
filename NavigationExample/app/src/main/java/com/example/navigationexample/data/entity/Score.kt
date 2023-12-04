package com.example.navigationexample.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.navigationexample.constants.ScoreType

@Entity(tableName = "score")
class Score {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id:Long = 0

    @ColumnInfo("score_value")
    var scoreValue:Int = 0

    @ColumnInfo("score_date")
    var scoreDate:Long = 0

    @ColumnInfo("client_id")
    var clientId:Int? = 0

    @ColumnInfo("score_category")
    var scoreCategory:String = ""

    @ColumnInfo("type")
    var type:ScoreType = ScoreType.EXPENSES

    @ColumnInfo("apartment")
    var apartment:String = ""
}