package com.example.studyconnect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class StudyGroupCreationFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_group_creation_form) // This should be your form layout file

        // Find your UI elements
        val groupNameEditText: EditText = findViewById(R.id.groupNameET)
        val createGroupButton: Button = findViewById(R.id.btn_create_group)

        // Set click listener for save button
        createGroupButton.setOnClickListener {
            val groupName = groupNameEditText.text.toString()
            // You can add logic here to save the group to the database or something else
            // For now, we'll just log the group name.
            println("Group Name: $groupName")
        }
    }
}
