package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import org.json.JSONArray
import org.json.JSONObject
import ru.nikita.weatherdiplom.adapter.MyAdapter
import ru.nikita.weatherdiplom.databinding.FragmentWeekBinding
import ru.nikita.weatherdiplom.dto.Week
import ru.nikita.weatherdiplom.viewmodel.WeatherViewModel
import java.util.ArrayList

class WeekFragment : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel: WeatherViewModel by activityViewModels()
        binding = FragmentWeekBinding.inflate(layoutInflater, container, false)

        fun getHoursList(weatherItem: Week): List<Week> {
            val hoursArray = JSONArray(weatherItem.hours)
            val list = ArrayList<Week>()
            for (i in 0 until hoursArray.length()) {
                val item = Week(
                    (hoursArray[i] as JSONObject).getString("time"),
                    (hoursArray[i] as JSONObject).getJSONObject("condition").getString("text"),
                    (hoursArray[i] as JSONObject).getString("temp_c"),
                    (hoursArray[i] as JSONObject).getJSONObject("condition").getString("icon"),
                    ""
                )
                list.add(item)
            }
            return list
        }

        val adapterDays = MyAdapter()
        binding.daysRecycler.adapter = adapterDays

        val adapterHours = MyAdapter()
        binding.hoursRecycler.adapter = adapterHours

        viewModel.dataList.observe(viewLifecycleOwner) {

            adapterHours.submitList(getHoursList(it[0]))
            Log.d("MyLog", "info from weekFragment: $it")

            adapterDays.submitList(it)
        }

        return binding.root
    }

}