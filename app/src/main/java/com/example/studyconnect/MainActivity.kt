package com.example.studyconnect

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        val fragmentManager: FragmentManager = supportFragmentManager
        // define  fragments
        val profileFragment: Fragment = ProfileFragment()
        val dashboardFragment: Fragment = DashboardFragment()
        val searchFragment: Fragment = SearchFragment()
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.action_profile -> fragment = profileFragment
                R.id.action_dashboard -> fragment = dashboardFragment
                R.id.action_search -> fragment = searchFragment
            }
            fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit()
            true
        }
        // Set default selection
        bottomNavigationView.selectedItemId = R.id.action_profile
        // Call helper method to swap the FrameLayout with the fragment
        replaceFragment(ProfileFragment())

    }
    // func to swap fragment
    private fun replaceFragment(profileFragment: ProfileFragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, profileFragment)
        fragmentTransaction.commit()
    }
}