package ru.nikita.weatherdiplom.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.nikita.weatherdiplom.R
import ru.nikita.weatherdiplom.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)


        binding.backToFragmentUser.setOnClickListener {
            findNavController().popBackStack(R.id.userInfoFragment, false)
        }




        binding.button1.setOnClickListener {

            val preferences = this.requireActivity()
                .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)

            val userSignUp = preferences.getString(KEY_AUTH_SIGNIN, "signIn")
            val userSignIn = preferences.getString(KEY_AUTH_SIGNUP, "signUp")

            binding.userText1.text = userSignUp
            binding.userText2.text = userSignIn

            if (userSignUp == userSignIn){
                Toast.makeText(requireContext(),"равны", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(),"НЕ РАВНЫ", Toast.LENGTH_SHORT).show()
            }
        }






        return binding.root
    }


}