package ru.nikita.weatherdiplom.dto

data class Day (
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val imageURL: String,
    val hours: String,
)