package ru.nikita.weatherdiplom.ui

import android.content.Context
import android.os.Bundle
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

        val preferences = this.requireActivity()
            .getSharedPreferences(KEY_AUTH, Context.MODE_PRIVATE)

        val userSignUp = preferences.getString(KEY_AUTH_SIGNIN, "signIn").toString()
        val userSignIn = preferences.getString(KEY_AUTH_SIGNUP, "signUp").toString()
        updateUI(userSignIn, userSignUp)

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
            adapterDays.submitList(it)
        }

        return binding.root
    }

    private fun updateUI(signInPref: String, signUpPref: String) {
        if (signInPref == signUpPref) {
            binding.frameLayoutDays.visibility = View.VISIBLE
            binding.frameLayoutHours.visibility = View.VISIBLE
            binding.noAccessCardWeekFragment.visibility = View.GONE
        } else {
            binding.frameLayoutDays.visibility = View.GONE
            binding.frameLayoutHours.visibility = View.GONE
            binding.noAccessCardWeekFragment.visibility = View.VISIBLE
        }
    }

}