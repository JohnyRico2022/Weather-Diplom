package ru.nikita.weatherdiplom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nikita.weatherdiplom.R


class DayFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_day, container, false)



        //TODO отступы в xml


        //TODO lenear layout????


        //TODO Сделать данные в виде карточки и по клику переходить на новый экран (более подробная информация)
    }

}