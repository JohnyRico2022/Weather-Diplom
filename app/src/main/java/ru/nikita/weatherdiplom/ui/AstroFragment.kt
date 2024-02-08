package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import ru.nikita.weatherdiplom.databinding.FragmentAstroBinding
import ru.nikita.weatherdiplom.viewmodel.WeatherViewModel

class AstroFragment : Fragment() {

    lateinit var binding : FragmentAstroBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        binding = FragmentAstroBinding.inflate(inflater, container, false)

        viewModel.getWeather()

        viewModel.data.observe(viewLifecycleOwner) {

            with(binding) {
                sunRiseValue.text = it.sunrise
                sunSetValue.text = it.sunset
                moonRiseValue.text = it.moonrise
                moonSetValue.text = it.moonset
                moonPhaseValue.text = it.moon_phase
                moonIlluminationValue.text = it.moonIllumination
            }
            Log.d("MyLog", "liveData FRAGMENT: $it")
        }

        return binding.root

    }
}

//TODO вставлять картинку фаза луны

//TODO Идет постоянный запрос данных в сеть, проработать этот вопрос