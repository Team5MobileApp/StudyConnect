package com.example.studyconnect

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "study_group")
data class StudyGroup(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "groupName") val groupName: String?,
    @ColumnInfo(name = "subject") val subject: String?,
    @ColumnInfo(name = "hybrid") val hybrid: String?,
    @ColumnInfo(name = "time") val time: String?,
    @ColumnInfo(name = "learningType") val learningType: String?
    )