package com.example.studyconnect

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val addGroupButton: Button = view.findViewById(R.id.button)

        // Set up OnClickListener for the button
        addGroupButton.setOnClickListener {
            // Use Intent to navigate to StudyGroupCreationFormActivity
            val intent = Intent(activity, StudyGroupCreationFormActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}