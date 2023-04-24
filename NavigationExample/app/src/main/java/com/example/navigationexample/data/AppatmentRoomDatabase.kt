package com.example.navigationexample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.dao.AppatmentDao
import com.example.navigationexample.data.dao.ClientDao
import com.example.navigationexample.data.dao.RentalDaysDao
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.data.entity.RentalDay


@Database(entities = [(Appatment::class), (Client::class), (RentalDay::class)], version = 1)
abstract class AppatmentRoomDatabase: RoomDatabase() {

    abstract fun getAppatmentDao(): AppatmentDao
    abstract fun getClientDao(): ClientDao
    abstract fun getRentalDayDao(): RentalDaysDao

    companion object {

        private var INSTANCE: AppatmentRoomDatabase? = null

        fun getInstance(context: Context): AppatmentRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppatmentRoomDatabase::class.java,
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