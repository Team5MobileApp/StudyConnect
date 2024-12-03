package com.example.studyconnect

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth


private lateinit var auth: FirebaseAuth


class SignupFragment : Fragment(R.layout.fragment_signup) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val signupButton = view.findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.usernameET).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordET).text.toString()

            // Perform sign up logic here
            if (username.isNotEmpty() && password.isNotEmpty()) {
                Log.d("SignupFragment", "Email or Password is not empty")
                // Perform sign-up using Firebase Authentication
                performSignup(username, password)
            } else {
                Log.d("SignupFragment", "Email or Password is empty")
                Toast.makeText(context, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performSignup(email: String, password: String) {
        // Create user with email and password
        Log.d("SignupFragment", "creating user")
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("SignupFragment", "success")
                    // If sign-up is successful, show success message and navigate to login screen
                    Toast.makeText(context, "Sign-up successful", Toast.LENGTH_SHORT).show()
                    navigateToLoginFragment()
                } else {
                    Log.d("SignupFragment", "fail")
                    // If sign-up fails, show error message
                    Toast.makeText(context, "Sign-up failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToLoginFragment() {
        val loginFragment = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, loginFragment)
            .commit()
    }
}