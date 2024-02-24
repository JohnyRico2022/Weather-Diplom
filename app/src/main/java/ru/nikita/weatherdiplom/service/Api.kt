package ru.nikita.weatherdiplom.service

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import ru.nikita.weatherdiplom.dto.Day
import ru.nikita.weatherdiplom.dto.Week
import ru.nikita.weatherdiplom.utils.TextConverter

class Api(val context: Application) {
    //TODO №1 спрятать ключ в gitIgnor
    companion object {
        const val API_KEY = "51e4803842ee448fa0795006222906"
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }

    var dataDay: MutableLiveData<Day> = MutableLiveData<Day>()
    var dataListWeek: MutableLiveData<List<Week>> = MutableLiveData<List<Week>>()
    var dataHours: MutableLiveData<List<Week>> = MutableLiveData<List<Week>>()


    suspend fun getWeather(city: String, language: String) {
        Log.d("MyLog", "getWeather. Thread name:${Thread.currentThread().name}")

        withContext(Dispatchers.IO) {

            val url =
                "${BASE_URL}forecast.json?key=${API_KEY}&q=${city}&days=3&aqi=no&alerts=no&lang=${language}"

            val queue = Volley.newRequestQueue(context)

            val request = StringRequest(
                Request.Method.GET,
                url,
                { result ->
                    parseWeatherData(result)
                },
                { error -> Log.d("MyLog", "Error: $error") }
                //TODO №2 обработка ошибок
            )
            Log.d("MyLog", "getWeather. Thread name2:${Thread.currentThread().name}")
            queue.add(request)
        }
    }


    private fun parseWeatherData(result: String) {

        val mainObject = JSONObject(result)
        parseDay(mainObject)
        parseWeek(mainObject)


    }

    private fun parseWeek(mainObject: JSONObject): MutableLiveData<List<Week>> {

        val list = ArrayList<Week>()
        val daysArray = mainObject.getJSONObject("forecast").getJSONArray("forecastday")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject

            val item = Week(
                day.getString("date"),
                TextConverter.convertToUtf8(
                    day.getJSONObject("day").getJSONObject("condition").getString("text")
                ),
                day.getJSONObject("day").getString("avgtemp_c"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }

        dataListWeek.value = list
        return dataListWeek
    }

    private fun parseDay(mainObject: JSONObject): MutableLiveData<Day> {

        val day = mainObject.getJSONObject("forecast")
            .getJSONArray("forecastday")[0] as JSONObject

        val itemDay = Day(
            TextConverter.convertToUtf8(mainObject.getJSONObject("location").getString("name")),
            mainObject.getJSONObject("current").getString("temp_c"),
            TextConverter.convertToUtf8(
                mainObject.getJSONObject("current").getJSONObject("condition").getString("text")
            ),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
            day.getJSONObject("day").getString("mintemp_c"),
            day.getJSONObject("day").getString("maxtemp_c"),
            day.getJSONObject("day").getString("avgtemp_c"),
            mainObject.getJSONObject("current").getString("wind_kph"),
            day.getJSONObject("day").getString("avgvis_km"),
            day.getJSONObject("day").getString("totalprecip_mm"),
            mainObject.getJSONObject("current").getString("humidity"),
            day.getJSONObject("day").getString("daily_chance_of_rain"),
            day.getJSONObject("day").getString("daily_chance_of_snow"),
            day.getJSONObject("astro").getString("sunrise"),
            day.getJSONObject("astro").getString("sunset"),
            day.getJSONObject("astro").getString("moonrise"),
            day.getJSONObject("astro").getString("moonset"),
            day.getJSONObject("astro").getString("moon_phase"),
            day.getJSONObject("astro").getString("moon_illumination"),
        )

        dataDay.value = itemDay
        return dataDay
    }

    suspend fun parseHours(itemWeek: String): MutableLiveData<List<Week>> {
        val hoursArray = JSONArray(itemWeek)
        val list = ArrayList<Week>()
        for (i in 0 until hoursArray.length()) {
            val item = Week(
                (hoursArray[i] as JSONObject).getString("time"),
                TextConverter.convertToUtf8(
                    (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text")
                ),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            list.add(item)
        }

        dataHours.value = list
        return dataHours
    }
}