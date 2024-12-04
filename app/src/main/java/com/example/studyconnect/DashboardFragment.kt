package com.example.studyconnect

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class DashboardFragment : Fragment() {
    // Create var for displayable study groups, the recyclerview, and binding var
    private val studyGroups = mutableListOf<StudyGroup>()
    private lateinit var studyGroupRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // Set up RecyclerView
        studyGroupRecyclerView = view.findViewById(R.id.studyGroupRV)
        val studyGroupAdapter = StudyGroupAdapter(view.context, studyGroups)
        studyGroupRecyclerView.adapter = studyGroupAdapter

        // Set up dividers in the RecyclerView layout
        studyGroupRecyclerView.layoutManager = LinearLayoutManager(view.context).also {
            val dividerItemDecoration = DividerItemDecoration(view.context, it.orientation)
            studyGroupRecyclerView.addItemDecoration(dividerItemDecoration)
        }

        // Launch the coroutine to fetch all study groups
        lifecycleScope.launch {
            try {
                // Fetch all study groups from the database
                val allStudyGroups = withContext(Dispatchers.IO) {
                    // Fetch all study groups from the database
                    (requireActivity().application as StudyGroupApplication)
                        .db.studyGroupDao()
                        .getAllStudyGroups() // This method should return all study groups
                }

                // Update the RecyclerView with the list of all study groups
                withContext(Dispatchers.Main) {
                    studyGroups.clear()
                    studyGroups.addAll(allStudyGroups)
                    studyGroupAdapter.notifyDataSetChanged()
                }

            } catch (e: Exception) {
                Log.e("DashboardFragment", "Error fetching study groups", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Error fetching study groups", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Inflate the layout for this fragment
        return view
    }

    // Helper function to get user email from Firebase Auth (if needed for future logic)
    private fun getUserEmail(): String? {
        return FirebaseAuth.getInstance().currentUser?.email
    }
}
