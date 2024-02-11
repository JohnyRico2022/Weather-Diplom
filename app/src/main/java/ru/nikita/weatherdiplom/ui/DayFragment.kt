package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ru.nikita.weatherdiplom.R
import ru.nikita.weatherdiplom.databinding.FragmentDayBinding
import ru.nikita.weatherdiplom.viewmodel.WeatherViewModel

class DayFragment : Fragment() {
    private lateinit var binding: FragmentDayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        binding = FragmentDayBinding.inflate(inflater, container, false)

        var city = "Moscow"

        viewModel.getWeather(city)

        viewModel.data.observe(viewLifecycleOwner) {
            with(binding) {
                cityName.text = it.city
                currentTemp.text = "${it.currentTemp} °C"
                condition.text = it.condition
                Picasso.get().load("https:" + it.imageURL).into(imageWeather)
            }
        }

        binding.mainCard.setOnClickListener {
            findNavController().navigate(R.id.action_dayFragment_to_fullCurrentWeatherFragment)
        }

        binding.info.setOnClickListener {
            Toast.makeText(requireContext(), R.string.toast_info, Toast.LENGTH_SHORT).show()
        }

        binding.searchImage.setOnClickListener {
            val textCity = binding.searchCity.text.toString()
            city = textCity
            Toast.makeText(requireContext(), "Вы выбрали $city", Toast.LENGTH_SHORT).show()
            viewModel.getWeather(city)
            binding.searchCity.setText("")
        }

        //TODO по нажатию кнопки убрать клавиатуру


        return binding.root
    }
}