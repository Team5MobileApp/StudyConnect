package com.example.studyconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileFragment : Fragment() {

    private lateinit var db: AppDatabase // Room database instance
    private lateinit var userPreferencesDao: UserPreferencesDao // DAO instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val profileView = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize Room database and DAO
        db = AppDatabase.getInstance(requireContext()) // Access database
        userPreferencesDao = db.userPreferencesDao()

        // Get UI elements
        val nameET: EditText = profileView.findViewById(R.id.nameET)
        val learningSpinner: Spinner = profileView.findViewById(R.id.learningSpinner)
        val timeSpinner: Spinner = profileView.findViewById(R.id.timeSpinner)
        val hybridSpinner: Spinner = profileView.findViewById(R.id.hybridSpinner)
        val classesML: EditText = profileView.findViewById(R.id.classesML)
        val saveButton: Button = profileView.findViewById(R.id.saveButton)

        val learningOptions = listOf("Visual", "Auditory", "Write/Read", "Kinesthetic")
        val timeOptions = listOf("Morning", "Afternoon", "Evening")
        val hybridOptions = listOf("In-person", "Hybrid", "Virtual")

        // Set adapters for Spinners
        learningSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, learningOptions)
        timeSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, timeOptions)
        hybridSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, hybridOptions)

        // Set OnClickListener for Save Button
        saveButton.setOnClickListener {
            val name = nameET.text.toString()
            val learningType = learningSpinner.selectedItem.toString()
            val timePreference = timeSpinner.selectedItem.toString()
            val hybridPreference = hybridSpinner.selectedItem.toString()
            val classesString = classesML.text.toString()
            val email = getUserEmail()
            if(email == null) {
                // Handle the case when email is not found or the user is not logged in
                    Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show()
            }

            // Create a UserPreferences object
            val userPreferences = UserPreferences(
                email = email,
                name = name,
                learningPreference = learningType,
                timePreference = timePreference,
                hybridPreference = hybridPreference,
                classes = classesString,
                groupIDs = null,
            )

            // Save the data using coroutine in background thread (Dispatchers.IO)
            lifecycleScope.launch {
                saveUserPreferences(userPreferences)
            }

            // Optional: Provide feedback to the user that the preferences were saved
            Toast.makeText(requireContext(), "Preferences saved!", Toast.LENGTH_SHORT).show()
        }

        return profileView
    }

    // Function to save user preferences asynchronously using CoroutineScope
    private suspend fun saveUserPreferences(userPreferences: UserPreferences) {
        // Insert userPreferences into Room database on IO thread
        CoroutineScope(Dispatchers.IO).launch {
            userPreferencesDao.insertUser(userPreferences)  // Insert data into database
            requireActivity().runOnUiThread {
                // Optionally update UI or notify user upon success
                // For example: show a toast or navigate to another screen
            }
        }
    }

    private fun getUserEmail(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }
}