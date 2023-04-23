package com.example.navigationexample.di

import android.content.Context
import androidx.room.Room
import com.example.navigationexample.data.AppatmentRoomDatabase
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
        AppatmentRoomDatabase::class.java,
        "product_database"
    ).fallbackToDestructiveMigration()
        .build() // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideAppatmentDao(db: AppatmentRoomDatabase) = db.appatmentDao()

    @Singleton
    @Provides
    fun provideClientDao(db: AppatmentRoomDatabase) = db.ClientDao()
}