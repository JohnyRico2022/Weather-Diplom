package ru.nikita.weatherdiplom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.nikita.weatherdiplom.dto.Day
import ru.nikita.weatherdiplom.dto.Week
import ru.nikita.weatherdiplom.service.Api

class WeatherViewModel(context: Application) : AndroidViewModel(context) {
    private val apiService = Api(context,)

    val dataDay: MutableLiveData<Day> = apiService.dataDay

    val dataListWeek: MutableLiveData<List<Week>> = apiService.dataListWeek

    val dataHours: MutableLiveData<List<Week>> = apiService.dataHours

    fun getWeather(town: String) {
        apiService.getWeather(town)
    }
    fun hoursForParse(itemWeek: String) {
        apiService.parseHours(itemWeek)
    }

}