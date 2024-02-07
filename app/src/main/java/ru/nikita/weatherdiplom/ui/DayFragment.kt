package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import android.util.Log
import android.view.Gravity
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


        viewModel.getWeather()


        viewModel.data.observe(viewLifecycleOwner) {
            val curTemp = "${it.currentTemp}°C"
            with(binding) {
                cityName.text = it.city
                currentTemp.text = curTemp
                condition.text = it.condition
                Picasso.get().load("https:" + it.imageURL).into(imageWeather)
            }
            Log.d("MyLog", "liveData FRAGMENT: $it")
        }

        binding.mainCard.setOnClickListener {
            findNavController().navigate(R.id.action_dayFragment_to_fullCurrentWeatherFragment)
        }

        binding.info.setOnClickListener {
            val toast = Toast.makeText(
                requireContext(),
                "Для перехода кликни по карточке",
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.TOP, 0, 250)
            toast.show()
        }


        return binding.root
    }

}