package ru.nikita.weatherdiplom.ui

import android.app.AlertDialog
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
const val KEY_DATA_LANGUAGE = "LANGUAGE"
const val KEY_DATA_COLOR = "COLOR"

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
        val language = pref.getString(KEY_DATA_LANGUAGE, "en").toString()

        viewModel.getWeather(city, language)

        binding.searchImage.setOnClickListener {
            val textCity = binding.searchCity.text.toString()
            pref.edit()
                .putString(KEY_DATA_CITY, textCity)
                .apply()
            AndroidUtils.hideKeyboard(requireView())
            viewModel.getWeather(textCity, language)
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
            showInfoDialog()
        }

        return binding.root
    }

    private fun showInfoDialog() {
        AlertDialog.Builder(requireContext())
            .setIcon(R.drawable.ic_error_24_black)
            .setTitle(R.string.important_information)
            .setMessage(R.string.info_main_card)
            .setPositiveButton(R.string.i_understand) { _, _ -> }
            .show()
    }
}