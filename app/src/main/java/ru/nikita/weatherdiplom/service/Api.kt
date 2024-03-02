package ru.nikita.weatherdiplom.service

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import ru.nikita.weatherdiplom.BuildConfig
import ru.nikita.weatherdiplom.R
import ru.nikita.weatherdiplom.dto.Day
import ru.nikita.weatherdiplom.dto.Week
import ru.nikita.weatherdiplom.utils.DateConverter
import ru.nikita.weatherdiplom.utils.TextConverter
import javax.inject.Inject
import javax.inject.Singleton


class Api(val context: Application) {

    val dataDay: MutableLiveData<Day> = MutableLiveData<Day>()
    val dataListWeek: MutableLiveData<List<Week>> = MutableLiveData<List<Week>>()
    val dataListHours: MutableLiveData<List<Week>> = MutableLiveData<List<Week>>()

    private val myApiKey = BuildConfig.MY_API_KEY
    private val baseURL = "https://api.weatherapi.com/v1/"

    private val dateConverter = DateConverter()

    suspend fun getWeather(city: String, language: String) {

        withContext(Dispatchers.IO) {

            val url =
                "${baseURL}forecast.json?key=${myApiKey}&q=${city}&days=3&aqi=no&alerts=no&lang=${language}"

            val queue = Volley.newRequestQueue(context)

            val request = StringRequest(
                Request.Method.GET,
                url,
                { result -> parseWeatherData(result) },
                { error -> parseError(error.toString()) }
            )
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
                dateConverter.convertDate(day.getString("date")),
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
                dateConverter.convertDateWithHours((hoursArray[i] as JSONObject).getString("time")),
                TextConverter.convertToUtf8(
                    (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text")
                ),
                (hoursArray[i] as JSONObject).getString("temp_c"),
                (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                ""
            )
            list.add(item)
        }

        dataListHours.value = list
        return dataListHours
    }


    private fun parseError(error: String) {
        when (error) {
            "com.android.volley.NoConnectionError: java.net.UnknownHostException: Unable to resolve host \"api.weatherapi.com\": No address associated with hostname" ->
                Toast.makeText(context, R.string.no_network, Toast.LENGTH_LONG).show()

            "com.android.volley.ClientError" ->
                Toast.makeText(context, R.string.city_does_not_exist, Toast.LENGTH_LONG).show()

            "com.android.volley.AuthFailureError" ->
                Toast.makeText(context, R.string.access_key_error, Toast.LENGTH_LONG).show()
        }
    }
}