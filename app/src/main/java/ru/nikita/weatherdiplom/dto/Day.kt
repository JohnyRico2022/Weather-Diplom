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

    val sunrise: String,
    val sunset: String,
    val moonrise: String,
    val moonset: String,
    val moon_phase: String,
    val moonIllumination: String,
    )


// TODO сделать свайпрефреш

//TODO ставить обои в зависимости от погоды, ориентируясь на пришедший condition

// направление ветра - опционально