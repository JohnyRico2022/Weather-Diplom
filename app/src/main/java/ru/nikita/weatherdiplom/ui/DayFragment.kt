package ru.nikita.weatherdiplom.ui

import android.content.Context
import android.os.Bundle
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
import ru.nikita.weatherdiplom.utils.AndroidUtils
import ru.nikita.weatherdiplom.viewmodel.WeatherViewModel

const val KEY_DATA = "DATA"
const val KEY_DATA_CITY = "CITY"

class DayFragment : Fragment() {
    private lateinit var binding: FragmentDayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        binding = FragmentDayBinding.inflate(inflater, container, false)
        val pref = this.requireActivity()
            .getSharedPreferences(KEY_DATA, Context.MODE_PRIVATE)


        val city = pref.getString(KEY_DATA_CITY, "Moscow").toString()

        viewModel.getWeather(city)

        binding.searchImage.setOnClickListener {
            val textCity = binding.searchCity.text.toString()
            pref.edit()
                .putString(KEY_DATA_CITY, textCity)
                .apply()
            AndroidUtils.hideKeyboard(requireView())
            viewModel.getWeather(textCity)
            binding.searchCity.setText("")
        }


        viewModel.dataDay.observe(viewLifecycleOwner) {
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

        return binding.root
    }
}