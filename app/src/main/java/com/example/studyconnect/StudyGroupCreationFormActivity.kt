package com.example.studyconnect

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudyGroupCreationFormActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase // Room database instance
    private lateinit var studyGroupDao: StudyGroupDao // DAO instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_group_creation_form) // This should be your form layout file

        // Initialize Room database and DAO
        db = AppDatabase.getInstance(this) // Access database
        studyGroupDao = db.studyGroupDao()

        // Find your UI elements
        val groupNameET: EditText = findViewById(R.id.groupNameET)
        val subjectET: EditText = findViewById(R.id.subjectET)
        val hybridSpinner: Spinner = findViewById(R.id.spinner_hybrid_preference)
        val timeSpinner: Spinner = findViewById(R.id.spinner_time_preference)
        val learningSpinner: Spinner = findViewById(R.id.spinner_learning_type)
        val createGroupButton: Button = findViewById(R.id.btn_create_group)

        val learningOptions = listOf("Visual", "Auditory", "Write/Read", "Kinesthetic")
        val timeOptions = listOf("Morning", "Afternoon", "Evening")
        val hybridOptions = listOf("In-person", "Hybrid", "Virtual")

        // Set adapters for Spinners
        learningSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, learningOptions)
        timeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeOptions)
        hybridSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, hybridOptions)


        // Set click listener for save button
        createGroupButton.setOnClickListener {
            val groupName = groupNameET.text.toString()
            val subject = subjectET.text.toString()
            val hybrid = hybridSpinner.selectedItem.toString()
            val time = timeSpinner.selectedItem.toString()
            val learningType = learningSpinner.selectedItem.toString()

            // Create a UserPreferences object
            val studyGroup = StudyGroup(
                groupName = groupName,
                subject = subject,
                time = time,
                hybrid = hybrid,
                learningType = learningType
            )

            // Save the data using coroutine in background thread (Dispatchers.IO)
            lifecycleScope.launch {
                saveStudyGroup(studyGroup)
            }

        }
    }

    private suspend fun saveStudyGroup(studyGroup: StudyGroup) {
        // Insert userPreferences into Room database on IO thread
        CoroutineScope(Dispatchers.IO).launch {
            studyGroupDao.insertUser(studyGroup)  // Insert data into database
            this@StudyGroupCreationFormActivity.runOnUiThread {
                // Optionally update UI or notify user upon success
                // For example: show a toast or navigate to another screen
            }
        }
    }
}
