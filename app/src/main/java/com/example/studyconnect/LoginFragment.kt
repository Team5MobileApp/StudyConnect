package com.example.studyconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val signupButton = view.findViewById<Button>(R.id.signupButton)

        loginButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.usernameET).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordET).text.toString()

            // Perform login validation here
            if (performLogin(username, password)) {
                // Navigate to Main Fragment if login is successful
                navigateToMainFragment()
            } else {
                Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
            }
        }

        signupButton.setOnClickListener {
            // Navigate to SignupFragment
            navigateToSignupFragment()
        }
    }

    private fun performLogin(username: String, password: String): Boolean {
        // Add your authentication logic here (e.g., checking username/password)
        return username == "user" && password == "password"  // Example validation
    }

    private fun navigateToMainFragment() {
        val activity = activity as MainActivity
        activity.loadMainContent()
    }

    private fun navigateToSignupFragment() {
        // Switch to the Sign Up fragment
        val signupFragment = SignupFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, signupFragment)
            .addToBackStack(null)  // Allow users to navigate back to login
            .commit()
    }
}
