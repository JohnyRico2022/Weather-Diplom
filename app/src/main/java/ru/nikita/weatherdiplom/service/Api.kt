package ru.nikita.weatherdiplom.service

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ru.nikita.weatherdiplom.dto.Day


class Api(val context: Application) {

    companion object {
        const val API_KEY = "51e4803842ee__8fa0795006222906"
        const val BASE_URL = "https://api.weatherapi.com/v1/"
    }

    var data: MutableLiveData<Day> = MutableLiveData() //может val
    //TODO спрятать ключ в gitIgnor


    fun getCurrentWeather() {
        val city = "Berlin"
        val language = "en"
        val url =
            "${BASE_URL}forecast.json?key=${API_KEY}&q=${city}&days=3&aqi=no&alerts=no&lang=${language}"

        val queue = Volley.newRequestQueue(context)
        val request = StringRequest(
            Request.Method.GET,
            url,
            { result ->
                parseWeatherDataDay(result)
            },
            { error -> Log.d("MyLog", "Error: $error") }
        )
        queue.add(request)
    }

    private fun parseWeatherDataDay(result: String): MutableLiveData<Day> {
        val mainObject = JSONObject(result)
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
        data.value = itemDay               //попробовать сразу ретурн на эту строку

        Log.d("MyLog", "liveData from Api: ${data.value}")


        return data
    }

}