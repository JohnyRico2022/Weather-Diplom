package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
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

  //      viewModel.getWeather("")   //!!!!!!!!!!!!!!!!!

        viewModel.data.observe(viewLifecycleOwner) {

            with(binding) {
                minTempValue.text = "${it.minTemp} °C"
                maxTempValue.text = "${it.maxTemp} °C"
                averageTempValue.text = "${it.avrTemp} °C"
                windValue.text = "${it.wind} ${R.string.km_h}"
                averageVisibilityValue.text = "${it.avrVisibility} ${R.string.km_h}"
                precipitationValue.text = "${it.precipitation} ${R.string.mm}"
                humidityValue.text = "${it.humidity} %"
                rainChanceValue.text = "${it.chanceOfRain} %"
                snowChanceValue.text = "${it.chanceOfSnow} %"
            }
        }

        return binding.root
    }
}