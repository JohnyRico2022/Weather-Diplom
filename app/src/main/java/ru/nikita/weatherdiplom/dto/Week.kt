package ru.nikita.weatherdiplom.dto

data class Week(
    val date: String,
    val condition: String,
    val currentTemp: String,
    val imageURL: String,
)
