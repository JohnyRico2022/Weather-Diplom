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

    val dataListHours: MutableLiveData<List<Week>> = apiService.dataListHours

   suspend fun getWeather(city: String, language: String) {
            apiService.getWeather(city, language)
    }
   suspend  fun hoursForParse(itemWeek: String) {
            apiService.parseHours(itemWeek)
    }

}