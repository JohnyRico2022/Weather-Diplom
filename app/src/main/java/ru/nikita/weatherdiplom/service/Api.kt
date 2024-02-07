package ru.nikita.weatherdiplom.service

import android.app.Application
import android.util.Log
import android.widget.Toast
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
        val url = "${BASE_URL}current.json?key=${API_KEY}&q=${city}&aqi=no"

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
        val itemDay = Day(
            mainObject.getJSONObject("location").getString("name"),
            mainObject.getJSONObject("current").getString("last_updated"),
            mainObject.getJSONObject("current").getJSONObject("condition").getString("text"),
            mainObject.getJSONObject("current").getString("temp_c"),
            "",
            "",
            mainObject.getJSONObject("current").getJSONObject("condition").getString("icon"),
        )
        data.value = itemDay //попробовать сразу ретурн на эту строку

        Log.d("MyLog", "liveData from Api: ${data.value}")
        Log.d("MyLog", "liveData from Api 2: ${data.isInitialized}")

        return data
    }

}