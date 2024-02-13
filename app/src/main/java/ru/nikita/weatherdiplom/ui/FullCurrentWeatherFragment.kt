package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.nikita.weatherdiplom.R
import ru.nikita.weatherdiplom.databinding.FragmentFullCurrentWeatherBinding
import ru.nikita.weatherdiplom.viewmodel.WeatherViewModel

class FullCurrentWeatherFragment : Fragment() {

    private lateinit var binding: FragmentFullCurrentWeatherBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        binding = FragmentFullCurrentWeatherBinding.inflate(inflater, container, false)

        viewModel.data.observe(viewLifecycleOwner) {

            // Вспомогательный блок кода для перевода значений при смене языка.
            // Прямой подстановкой работает не корректно!
            binding.kmH.setText(R.string.km_h)
            binding.mm.setText(R.string.mm)
            val kmH = binding.kmH.text
            val mm = binding.mm.text
            // Конец вспомогательного блока


            with(binding) {
                minTempValue.text = "${it.minTemp} °C"
                maxTempValue.text = "${it.maxTemp} °C"
                averageTempValue.text = "${it.avrTemp} °C"
                windValue.text = it.wind + " " + kmH
                averageVisibilityValue.text = it.avrVisibility + " " + kmH
                precipitationValue.text = it.precipitation + " " + mm
                humidityValue.text = "${it.humidity} %"
                rainChanceValue.text = "${it.chanceOfRain} %"
                snowChanceValue.text = "${it.chanceOfSnow} %"
            }
        }

        binding.backToFragmentDay.setOnClickListener {
            findNavController().popBackStack(R.id.dayFragment, false)
        }

        return binding.root
    }
}

//TODO Поставить scrollview для маленьких экранов

//TODO №1  свайпрефреш