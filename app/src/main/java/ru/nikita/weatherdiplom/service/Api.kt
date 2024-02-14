package ru.nikita.weatherdiplom.service

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ru.nikita.weatherdiplom.dto.Day
import ru.nikita.weatherdiplom.dto.Week


class Api(val context: Application) {

    companion object {
        const val API_KEY = "51e4803842ee__8fa0795006222906"
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }


    var data: MutableLiveData<Day> = MutableLiveData()

    var dataList: MutableLiveData<List<Week>> = MutableLiveData<List<Week>>()

    //TODO №3 спрятать ключ в gitIgnor


    fun getWeather(city: String) {
        //      val city = town ?: "Berlin"
        val language = "en"
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

            //TODO №4 обработка ошибок
        )
        queue.add(request)
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
                day.getJSONObject("day").getJSONObject("condition").getString("text"),
                day.getJSONObject("day").getString("avgtemp_c"),
                day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                day.getJSONArray("hour").toString()
            )
            list.add(item)
        }
        Log.d("MyLog", "parseWeeK: ${list[2]}")

        dataList.value = list
        return dataList
    }

    private fun parseDay(mainObject: JSONObject): MutableLiveData<Day> {

        val day = mainObject.getJSONObject("forecast")
            .getJSONArray("forecastday")[0] as JSONObject

        val itemDay = Day(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("temp_c"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
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
        data.value = itemDay
 //       Log.d("MyLog", "liveData from Api: ${data.value}")

        return data
    }

}