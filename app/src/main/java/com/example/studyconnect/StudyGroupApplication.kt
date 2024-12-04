package com.example.studyconnect

import android.app.Application

class StudyGroupApplication : Application() {
    val db by lazy { AppDatabase.getInstance(this) }
}