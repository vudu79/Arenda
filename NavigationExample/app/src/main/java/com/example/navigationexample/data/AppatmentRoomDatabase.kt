package com.example.navigationexample.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.dao.AppatmentDao


@Database(entities = [(Appatment::class)], version = 1)
abstract class AppatmentRoomDatabase: RoomDatabase() {

    abstract fun appatmentDao(): AppatmentDao

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