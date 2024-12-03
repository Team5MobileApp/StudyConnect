package com.example.studyconnect

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// 1. Annotate your database class with @Database
@Database(entities = [UserPreferences::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // 2. Define the DAOs you want to access in this database
    abstract fun userPreferencesDao(): UserPreferencesDao

    // 3. Implement a singleton pattern for getting the database instance
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "study_connect_database"
            ).build()
    }

}