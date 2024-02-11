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

        binding.signUpMainCard.setOnClickListener {
            binding.signUpCard.visibility = View.VISIBLE
            binding.signInCard.visibility = View.GONE

        }

        binding.signInMainCard.setOnClickListener {
            binding.signInCard.visibility =View.VISIBLE
            binding.signUpCard.visibility = View.GONE
        }

        binding.buttonSignIn.setOnClickListener{
            binding.signInCard.visibility = View.GONE
            binding.one.text = "Вы вошли в систему"
            binding.loginSignIn.setText("")
            binding.passSignIn.setText("")


        }

        binding.buttonSignUp.setOnClickListener {
            binding.signUpCard.visibility = View.GONE
            binding.one.text = "Зарегистрирован"
            binding.loginSignUp.setText("")
            binding.passSignUp.setText("")
        }




//TODO убрать одинаковые значения в стили


        return binding.root
    }

}