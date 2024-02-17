package ru.nikita.weatherdiplom.utils

import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

object TextConverter {

    fun convertToUtf8(word: String): String {

        val windows1251Bytes = word.toByteArray(Charset.forName("ISO-8859-1"))

        return String(windows1251Bytes, StandardCharsets.UTF_8)
    }
}
