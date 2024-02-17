package ru.nikita.weatherdiplom.ui

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import ru.nikita.weatherdiplom.R
import ru.nikita.weatherdiplom.databinding.FragmentUserInfoBinding

const val KEY_AUTH = "KOTLIN"
const val KEY_AUTH_SIGNUP = "SIGNUP"
const val KEY_AUTH_SIGNIN = "SIGNIN"
const val KEY_LOGIN_NAME = "LOGIN"

class UserInfoFragment : Fragment() {

    private lateinit var binding: FragmentUserInfoBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        val preferences = this.requireActivity()
            .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)

        val userSignUp = preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
        val userSignIn = preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
        val userLogin = preferences.getString(KEY_LOGIN_NAME, "Login")
        binding.loginTextMainCard.text = userLogin
        updateMainCard(userSignIn, userSignUp)

        binding.signUpMainCard.setOnClickListener {
            binding.signUpCard.visibility = View.VISIBLE
            binding.signInCard.visibility = View.GONE
        }

        binding.buttonSignUp.setOnClickListener {
            val login = binding.loginSignUp.text.trim().toString()
            val email = binding.emailSignUp.text.trim().toString()
            val pass = binding.passSignUp.text.trim().toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && login.isNotEmpty()) {

                firebaseAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {

                            successfulSignUp()

                            val user = firebaseAuth.uid
                            preferences.edit()
                                .putString(KEY_AUTH_SIGNUP, user)
                                .putString(KEY_LOGIN_NAME, login)
                                .apply()
                        } else {
                            showDialog()
                        }
                    }

            } else {
                toastAllFields()
            }
        }

        binding.signInMainCard.setOnClickListener {
            binding.signInCard.visibility = View.VISIBLE
            binding.signUpCard.visibility = View.GONE
        }

        binding.buttonSignIn.setOnClickListener {
            val email = binding.mailSignIn.text.trim().toString()
            val pass = binding.passSignIn.text.trim().toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(requireActivity()) { task ->
                        if (task.isSuccessful) {

                            successfulSignIn()

                            val user = firebaseAuth.uid
                            preferences.edit()
                                .putString(KEY_AUTH_SIGNIN, user)
                                .apply()

                            val userLogin2 = preferences.getString(KEY_LOGIN_NAME, "Login")
                            binding.loginTextMainCard.text = userLogin2
                            val userSignUp2 =
                                preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
                            val userSignIn2 =
                                preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
                            updateMainCard(userSignIn2, userSignUp2)

                        } else {
                            showDialog()
                        }
                    }
            } else {
                toastAllFields()
            }
        }

        binding.signOutMainCard.setOnClickListener {

            preferences.edit()
                .putString(KEY_AUTH_SIGNIN, "signIn")
                .apply()

            val userSignUp3 = preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
            val userSignIn3 = preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
            updateMainCard(userSignIn3, userSignUp3)
            Toast.makeText(requireContext(),R.string.sign_out_successful, Toast.LENGTH_SHORT).show()
        }

        binding.settings.setOnClickListener {
            findNavController().navigate(R.id.action_userInfoFragment_to_settingsFragment)
        }

        return binding.root
    }

    private fun updateMainCard(signInPref: String, signUpPref: String) {

        if (signInPref == signUpPref) {
            binding.loginTextMainCard.visibility = View.VISIBLE
            binding.signOutMainCard.visibility = View.VISIBLE

            binding.withAsMainCard.visibility = View.GONE
            binding.signUpMainCard.visibility = View.GONE
            binding.signInMainCard.visibility = View.GONE
        } else {
            binding.loginTextMainCard.visibility = View.GONE
            binding.signOutMainCard.visibility = View.GONE

            binding.withAsMainCard.visibility = View.VISIBLE
            binding.signUpMainCard.visibility = View.VISIBLE
            binding.signInMainCard.visibility = View.VISIBLE
        }
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_error_24_black)
            .setTitle(R.string.important_information)
            .setMessage(R.string.text_in_dialog)
            .setPositiveButton(R.string.i_understand) { _, _ -> }
            .show()
    }

    private fun successfulSignUp() {
        Toast.makeText(requireContext(), R.string.sign_up_successful, Toast.LENGTH_SHORT).show()
        binding.loginSignUp.setText("")
        binding.emailSignUp.setText("")
        binding.passSignUp.setText("")
        binding.signUpCard.visibility = View.GONE
    }

    private fun successfulSignIn() {
        Toast.makeText(requireContext(), R.string.sign_in_successful, Toast.LENGTH_SHORT).show()
        binding.mailSignIn.setText("")
        binding.passSignIn.setText("")
        binding.signInCard.visibility = View.GONE
    }

    private fun toastAllFields() {
        Toast.makeText(requireContext(), R.string.all_fields_must_be_filled_in, Toast.LENGTH_SHORT)
            .show()
    }
}


