package com.example.studyconnect

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.studyconnect.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        enableEdgeToEdge()

        // Start by loading LoginFragment if the user isn't logged in
        if (isUserLoggedIn()) {
            // If the user is logged in, load the main content fragments
            loadMainContent()
        } else {
            // If the user is not logged in, show the login screen
            loadLoginFragment()
        }
    }


    // func to swap fragment
    private fun replaceFragment(profileFragment: ProfileFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, profileFragment)
        fragmentTransaction.commit()
    }

    // Method to check if the user is logged in
    private fun isUserLoggedIn(): Boolean {
        // Replace this with your actual login check (e.g., check shared preferences or database)
        return false // This is just for demo; set it to true after successful login
    }

    // Method to load the LoginFragment
    private fun loadLoginFragment() {
        // Hide Bottom Navigation when showing login/signup
        binding.bottomNavigationView.visibility = View.GONE

        val loginFragment = LoginFragment() // Assuming LoginFragment is already defined
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, loginFragment)
            .commit()
    }

    // Method to load the main content with BottomNavigationView
    fun loadMainContent() {
        // Show Bottom Navigation when loading main content
        binding.bottomNavigationView.visibility = View.VISIBLE
        val fragmentManager: FragmentManager = supportFragmentManager
        // define  fragments
        val profileFragment: Fragment = ProfileFragment()
        val dashboardFragment: Fragment = DashboardFragment()
        val searchFragment: Fragment = SearchFragment()


        // handle navigation selection for BottomNavigationView
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.action_profile -> fragment = profileFragment
                R.id.action_dashboard -> fragment = dashboardFragment
                R.id.action_search -> fragment = searchFragment
            }
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
            true
        }

        // Set default selection (for example, ProfileFragment)
        binding.bottomNavigationView.selectedItemId = R.id.action_profile
        // Call helper method to swap the FrameLayout with the fragment
        fragmentManager.beginTransaction().replace(R.id.frame_layout, profileFragment).commit()
    }
}