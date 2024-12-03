package com.example.studyconnect

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_preferences")
data class UserPreferences(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "timePreference") val timePreference: String?,
    @ColumnInfo(name = "hybridPreference") val hybridPreference: String?,
    @ColumnInfo(name = "learningPreference") val learningPreference: String?,
    @ColumnInfo(name = "classes") val classes: String?,
    @ColumnInfo(name = "groupIDs") val groupIDs: String?
    )