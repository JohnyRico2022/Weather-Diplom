package ru.nikita.weatherdiplom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FullCurrentWeatherFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {










        return inflater.inflate(R.layout.fragment_full_current_weather, container, false)
    }
}