package com.example.studyconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SignupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignupFragment : Fragment(R.layout.fragment_signup) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signupButton = view.findViewById<Button>(R.id.signupButton)
        signupButton.setOnClickListener {
            val username = view.findViewById<EditText>(R.id.usernameET).text.toString()
            val password = view.findViewById<EditText>(R.id.passwordET).text.toString()

            // Perform sign up logic here
            if (performSignup(username, password)) {
                // After sign up, navigate to login screen
                navigateToLoginFragment()
            } else {
                Toast.makeText(context, "Sign up failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun performSignup(username: String, password: String): Boolean {
        // Add your sign-up logic here (e.g., saving user details in database)
        return username.isNotEmpty() && password.isNotEmpty()  // Example validation
    }

    private fun navigateToLoginFragment() {
        val loginFragment = LoginFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, loginFragment)
            .commit()
    }
}