package com.example.navigationexample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.dao.AppatmentDao
import com.example.navigationexample.data.dao.ClientDao
import com.example.navigationexample.data.entity.Client


@Database(entities = [(Appatment::class), (Client::class)], version = 10)
abstract class AppatmentRoomDatabase: RoomDatabase() {

    abstract fun appatmentDao(): AppatmentDao
    abstract fun ClientDao(): ClientDao

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