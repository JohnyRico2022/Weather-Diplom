package ru.nikita.weatherdiplom.utils

import ru.nikita.weatherdiplom.R

object MoonPhases {

    fun changeMoonPhases(phase: String): String {
        return when (phase) {

            "New Moon" -> {
                "Новолуние"
            }                                     //1  луна не видна
            "Waxing Crescent" -> {
                "Растущий серп"
            }                                     //2
            "First Quarter" -> {
                "Первая четверть"
            }                                     //3
            "Waxing Gibbous" -> {
                "Растущая луна"
            }                                    //4
            "Full Moon" -> {
                "Полнолуние"
            }                                    // 5  видна полностью
            "Waning Gibbous" -> {
                "Убывающая луна"
            }                                     //6
            "Last Quarter" -> {
                "Последняя четверть"
            }                                     //7
            "Waning Crescent" -> {
                "Убывающий месяц"
            }                                    //8  сейчас
            else -> "error moon phase"
        }
    }



    fun setMoonImage(phase: String) : Int {

        return when(phase){

            "Waning Crescent" -> {
                R.drawable.ic_week_24
            }

            else -> R.drawable.ic_moon_24
        }

    }






}