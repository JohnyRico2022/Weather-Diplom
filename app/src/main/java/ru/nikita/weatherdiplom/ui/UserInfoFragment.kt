package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nikita.weatherdiplom.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment() {

    lateinit var binding: FragmentUserInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserInfoBinding.inflate(inflater, container, false)

        binding.settings.setOnClickListener {
            //TODO переход на фрагмент настроек
        }







        return binding.root
    }

}