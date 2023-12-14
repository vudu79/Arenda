package com.example.navigationexample.di

import android.content.Context
import androidx.room.Room
import com.example.navigationexample.data.ApartmentRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        ApartmentRoomDatabase::class.java,
        "product_database"
    ).fallbackToDestructiveMigration()
        .build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideAppatmentDao(db: ApartmentRoomDatabase) = db.getApartmentDao()

    @Singleton
    @Provides
    fun provideClientDao(db: ApartmentRoomDatabase) = db.getClientDao()

    @Singleton
    @Provides
    fun provideRentalDayDao(db: ApartmentRoomDatabase) = db.getRentalDayDao()

    @Singleton
    @Provides
    fun provideScoreDao(db: ApartmentRoomDatabase) = db.getScoresDao()

}