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
        val pref = this.requireActivity()
            .getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE)



        binding.backToFragmentUser.setOnClickListener {
            findNavController().popBackStack(R.id.userInfoFragment, false)
        }




        binding.ruButton.setOnClickListener {
            val lang = "ru"
            pref.edit()
                .putString(KEY_DATA_LANGUAGE, lang)
                .apply()
            Toast.makeText(requireContext(), "ru", Toast.LENGTH_SHORT).show()
        }
        binding.enButton.setOnClickListener {
            val lang = "en"
            pref.edit()
                .putString(KEY_DATA_LANGUAGE, lang)
                .apply()
            Toast.makeText(requireContext(), "en", Toast.LENGTH_SHORT).show()
        }
        binding.itButton.setOnClickListener {
            val lang = "it"
            pref.edit()
                .putString(KEY_DATA_LANGUAGE, lang)
                .apply()
            Toast.makeText(requireContext(), "it", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}