package com.example.studyconnect

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StudyGroupDao {
    @Insert
    fun insertGroup(studyGroup: StudyGroup)

    @Query("SELECT * FROM study_group WHERE id IN (:groupIDs)")
    fun getStudyGroupsByGroupIDs(groupIDs: List<String>): Flow<List<StudyGroup>>

    @Query("SELECT * FROM study_group")
    fun getAllStudyGroups(): List<StudyGroup>

}