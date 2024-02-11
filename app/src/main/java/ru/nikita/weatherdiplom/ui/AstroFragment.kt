package ru.nikita.weatherdiplom.ui

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

    lateinit var binding: FragmentAstroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        binding = FragmentAstroBinding.inflate(inflater, container, false)


        viewModel.data.observe(viewLifecycleOwner) {

            val moonPhValue = MoonPhases.changeMoonPhases(it.moonPhase)
            val moonPhaseImage = MoonPhases.setMoonImage(it.moonPhase)

            with(binding) {
                sunRiseValue.text = it.sunRise
                sunSetValue.text = it.sunSet
                moonRiseValue.text = it.moonRise
                moonSetValue.text = it.moonSet
                moonPhaseValue.text = moonPhValue
                moonIlluminationValue.text = "${it.moonIllumination} %"
            }
            binding.moonPhaseImage.setImageResource(moonPhaseImage)
        }


        return binding.root
    }
}

//TODO  №5 вставлять картинку фаза луны