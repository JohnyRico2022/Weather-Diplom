package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nikita.weatherdiplom.adapter.MyAdapter
import ru.nikita.weatherdiplom.databinding.FragmentWeekBinding
import ru.nikita.weatherdiplom.dto.Week

class WeekFragment : Fragment() {

    private lateinit var binding: FragmentWeekBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeekBinding.inflate(layoutInflater, container, false)

        val adapter = MyAdapter()
        binding.recycler.adapter = adapter

        //Временный список для тестов
        val list = listOf<Week>(
            Week("12:00", "sunny", "23", ""),
            Week("13:00", "sunny", "24", ""),
            Week("14:00", "sunny", "27", ""),
            Week("15:00", "sunny", "25", ""),
            Week("16:00", "sunny", "22", ""),
        )



        adapter.submitList(list)




        //TODO Улучшить внешний вид каждого айтема


        return binding.root
    }
}