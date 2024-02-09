package ru.nikita.weatherdiplom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.nikita.weatherdiplom.dto.Day
import ru.nikita.weatherdiplom.service.Api

class WeatherViewModel(context: Application) : AndroidViewModel(context) {
    private val apiService = Api(context)


    val liveDataList = MutableLiveData<List<Day>>()

    val data: MutableLiveData<Day> = apiService.data


    fun getWeather(town: String) {
        apiService.getCurrentWeather(town)
    }


}