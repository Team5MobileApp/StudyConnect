package com.example.studyconnect

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserPreferencesDao {
    @Insert
    fun insertUser(userPreferences: UserPreferences)

    @Update
    fun updateUser(userPreferences: UserPreferences)

    @Query("SELECT * FROM user_preferences WHERE email = :email LIMIT 1")
    fun getUserPreferencesByEmail(email: String): UserPreferences?
}