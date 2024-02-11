package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nikita.weatherdiplom.adapter.MyAdapter
import ru.nikita.weatherdiplom.databinding.FragmentWeekBinding
import ru.nikita.weatherdiplom.dto.Day
import ru.nikita.weatherdiplom.dto.Week

class WeekFragment : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeekBinding.inflate(layoutInflater, container, false)

        val adapterDays = MyAdapter()
        binding.daysRecycler.adapter = adapterDays

        //Временный список для тестов
        val list = listOf<Week>(
            Week("2024-02-11", "sunny", "23/25", ""),
            Week("2024-02-12", "sunny", "24/30", ""),
            Week("2024-02-13", "sunny", "27/15", ""),
        )
        adapterDays.submitList(list)




        val adapterHours = MyAdapter()
        binding.hoursRecycler.adapter = adapterHours

        val hoursList = listOf<Week>(
            Week("12:00", "sunny", "24", ""),
            Week("13:00", "sunny", "24", ""),
            Week("14:00", "sunny", "24", ""),
            Week("15:00", "sunny", "24", ""),
            Week("16:00", "sunny", "24", ""),
            Week("17:00", "sunny", "24", ""),
            Week("18:00", "sunny", "24", ""),
            Week("19:00", "sunny", "24", ""),
            Week("20:00", "sunny", "24", ""),
            )
        adapterHours.submitList(hoursList)

        return binding.root
    }
}