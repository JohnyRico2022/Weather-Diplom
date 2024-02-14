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
            }                                    //8
            else -> "error moon phase"
        }
    }


    fun setMoonImage(phase: String): Int {

        return when (phase) {

            "New Moon" -> R.drawable.new_moon

            "Waxing Crescent" -> R.drawable.waxing_crescent

            "First Quarter" -> R.drawable.first_quarter

            "Waxing Gibbous" -> R.drawable.waxing_gibbous

            "Full Moon" -> R.drawable.full_moon

            "Waning Gibbous" -> R.drawable.waning_gibbous

            "Last Quarter" -> R.drawable.last_quarter

            "Waning Crescent" -> R.drawable.waning_crescent

            else -> R.drawable.ic_error_24
        }
    }
}