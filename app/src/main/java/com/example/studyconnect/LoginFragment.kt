package com.example.studyconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


private lateinit var auth: FirebaseAuth


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val loginButton = view.findViewById<Button>(R.id.loginButton)
        val signupButton = view.findViewById<Button>(R.id.signupButton)

        loginButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.usernameET).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordET).text.toString()

            // Perform login validation here
            // Perform login using Firebase authentication
            if (username.isNotEmpty() && password.isNotEmpty()) {
                performLogin(username, password)
            } else {
                Toast.makeText(context, "Please enter username and password", Toast.LENGTH_SHORT).show()
            }
        }

        signupButton.setOnClickListener {
            // Navigate to SignupFragment
            navigateToSignupFragment()
        }
    }

    private fun performLogin(email: String, password: String) {
        // Sign in using Firebase Authentication
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // If login is successful, navigate to Main Fragment
                    navigateToMainFragment()
                } else {
                    // If login fails, show error message
                    Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToMainFragment() {
        val activity = activity as MainActivity
        activity.loadMainContent()
    }

    private fun navigateToSignupFragment() {
        // Switch to the Sign Up fragment
        val signupFragment = SignupFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, signupFragment)
            .addToBackStack(null)  // Allow users to navigate back to login
            .commit()
    }
}
