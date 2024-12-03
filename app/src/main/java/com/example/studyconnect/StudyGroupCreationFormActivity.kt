package com.example.studyconnect

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.google.firebase.auth.FirebaseAuth


class StudyGroupCreationFormActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase // Room database instance
    private lateinit var studyGroupDao: StudyGroupDao // DAO instance
    private lateinit var userPreferencesDao: UserPreferencesDao // DAO instance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_group_creation_form) // This should be your form layout file

        // Initialize Room database and DAO
        db = AppDatabase.getInstance(this) // Access database
        studyGroupDao = db.studyGroupDao()
        userPreferencesDao = db.userPreferencesDao()


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
                Toast.makeText(this@StudyGroupCreationFormActivity, "Study group created!", Toast.LENGTH_SHORT).show()
                finish()
            }

        }
    }

    private suspend fun saveStudyGroup(studyGroup: StudyGroup) {
        // Use Dispatchers.IO to offload the database operation to a background thread
        withContext(Dispatchers.IO) {
            // Insert the study group into the database
            studyGroupDao.insertGroup(studyGroup)

            // Get the email from SharedPreferences, Firebase, or Session (e.g., getUserEmail())
            val email = getUserEmail() // Replace with your method to retrieve the email

            if (email != null) {
                // Retrieve user preferences by email (done off the main thread)
                val userPreferences = userPreferencesDao.getUserPreferencesByEmail(email)

                if (userPreferences != null) {
                    // Append the new group ID to the existing groupIDs string
                    val newGroupIds = if (userPreferences.groupIDs.isNullOrEmpty()) {
                        studyGroup.id.toString()
                    } else {
                        "${userPreferences.groupIDs},${studyGroup.id}"
                    }

                    // Update the user preferences with the new groupID
                    val updatedUserPreferences = userPreferences.copy(groupIDs = newGroupIds)

                    // Save the updated user preferences back into the database
                    userPreferencesDao.updateUser(updatedUserPreferences)
                }

                // Notify the UI thread (optional)
                withContext(Dispatchers.Main) {
                    // Show a success message or navigate to another screen
                    Toast.makeText(this@StudyGroupCreationFormActivity, "Study group created and preferences updated!", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity
                }
            } else {
                // Handle the case when email is not found or the user is not logged in
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@StudyGroupCreationFormActivity, "User not logged in", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    private fun getUserEmail(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }

}


