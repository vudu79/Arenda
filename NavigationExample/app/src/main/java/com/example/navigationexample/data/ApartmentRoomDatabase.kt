package com.example.navigationexample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.dao.ApartmentDao
import com.example.navigationexample.data.dao.ClientDao
import com.example.navigationexample.data.dao.RentalDaysDao
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.entity.RentalDay
import com.example.navigationexample.data.entity.Score
import com.example.navigationexample.data.entity.ScoreCategory


@Database(entities = [(Appatment::class),
    (Client::class),
    (RentalDay::class),
    (Score::class),
    (ScoreCategory::class)],
    version =1)
abstract class ApartmentRoomDatabase: RoomDatabase() {

    abstract fun getApartmentDao(): ApartmentDao
    abstract fun getClientDao(): ClientDao
    abstract fun getRentalDayDao(): RentalDaysDao

    companion object {
        private var INSTANCE: ApartmentRoomDatabase? = null
        fun getInstance(context: Context): ApartmentRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ApartmentRoomDatabase::class.java,
                        "product_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}