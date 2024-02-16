package ru.nikita.weatherdiplom.dto

data class Day(
    val city: String,
    val currentTemp: String,
    val condition: String,
    val imageURL: String,

    val minTemp: String,         //мин темп
    val maxTemp: String,        //макс темп
    val avrTemp: String,        //средняя темп
    val wind: String,           // скорость ветра
    val avrVisibility: String,  //средняя видимость в км
    val precipitation: String,  //Кол-во осадков
    val humidity: String,       //Влажность в процентах
    val chanceOfRain: String,     //вероятность дождя
    val chanceOfSnow: String,     //вероятность снега

    val sunRise: String,
    val sunSet: String,
    val moonRise: String,
    val moonSet: String,
    val moonPhase: String,
    val moonIllumination: String,
)