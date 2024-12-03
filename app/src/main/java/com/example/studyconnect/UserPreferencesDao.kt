package com.example.studyconnect

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferencesDao {
    @Insert
    fun insertUser(userPreferences: UserPreferences)
}