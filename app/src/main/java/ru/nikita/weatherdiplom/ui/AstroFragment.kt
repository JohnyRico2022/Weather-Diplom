package ru.nikita.weatherdiplom.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.nikita.weatherdiplom.databinding.FragmentAstroBinding
import ru.nikita.weatherdiplom.utils.MoonPhases
import ru.nikita.weatherdiplom.viewmodel.WeatherViewModel

class AstroFragment : Fragment() {

    private lateinit var binding: FragmentAstroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        binding = FragmentAstroBinding.inflate(inflater, container, false)

        val preferences = this.requireActivity()
            .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)

        val userSignUp = preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
        val userSignIn = preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
        updateUI(userSignIn, userSignUp)


        viewModel.dataDay.observe(viewLifecycleOwner) {

            val moonPhValue = MoonPhases.changeMoonPhases(it.moonPhase)
            val moonPhaseImage = MoonPhases.setMoonImage(it.moonPhase)

            // Вспомогательный блок кода для перевода фазы луны при смене языка.
            // Прямой подстановкой работает не корректно!
            binding.moonHelper.setText(moonPhValue)
            val helperMoonPhase = binding.moonHelper.text
            // Конец вспомогательного блока

            with(binding) {
                sunRiseValue.text = it.sunRise
                sunSetValue.text = it.sunSet
                moonRiseValue.text = it.moonRise
                moonSetValue.text = it.moonSet
                moonPhaseValue.text = helperMoonPhase
                moonIlluminationValue.text = "${it.moonIllumination} %"
            }
            binding.moonPhaseImage.setImageResource(moonPhaseImage)
        }

        return binding.root
    }

    private fun updateUI(signInPref: String, signUpPref: String) {
        if (signInPref == signUpPref) {
            binding.frameLayoutBottom.visibility = View.VISIBLE
            binding.frameLayoutTop.visibility = View.VISIBLE
            binding.noAccessCardAstroFragment.visibility = View.GONE
        } else {
            binding.frameLayoutBottom.visibility = View.GONE
            binding.frameLayoutTop.visibility = View.GONE
            binding.noAccessCardAstroFragment.visibility = View.VISIBLE
        }
    }

}

