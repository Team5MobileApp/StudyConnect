package com.example.studyconnect

import android.os.Bundle
import android.widget.TextView
import androidx.activity.OnBackPressedDispatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class StudyGroupInterfaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_group_interface) // Set the layout file to use
// Inside StudyGroupInterfaceActivity.kt
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the back button in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Handle back button click
        toolbar.setNavigationOnClickListener {
            val dispatcher: OnBackPressedDispatcher = onBackPressedDispatcher
            dispatcher.onBackPressed() // Manually invoke the back press dispatcher
        }

// Enable the up button (this is the back arrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Get the data passed through the Intent
        val studyGroupName = intent.getStringExtra("groupName")
        val subject = intent.getStringExtra("subject")
        val time = intent.getStringExtra("time")
        val learningType = intent.getStringExtra("learningType")
        val hybrid = intent.getStringExtra("hybrid")

        // Display the data in the views
        val groupNameTextView: TextView = findViewById(R.id.studyGroupName)
        val subjectTextView: TextView = findViewById(R.id.classContent)
        val timeTextView: TextView = findViewById(R.id.timeContent)
        val learningTypeTextView: TextView = findViewById(R.id.learningStyleContent)
        val hybridTextView: TextView = findViewById(R.id.hybridContent)

        // Set the text to the respective views
        groupNameTextView.text = studyGroupName
        subjectTextView.text = subject
        timeTextView.text = time
        learningTypeTextView.text = learningType
        hybridTextView.text = hybrid

    }
}
