package com.example.tsunotintime.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object DateTimeParser {
    fun formatIsoDateToDisplay(isoDate: String): String {
        val possibleFormats = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
            "yyyy-MM-dd'T'HH:mm:ss'Z'"
        )

        val displayFormatter = SimpleDateFormat("dd.MM.yyyy | HH:mm", Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }

        for (format in possibleFormats) {
            try {
                val isoFormatter = SimpleDateFormat(format, Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
                val date = isoFormatter.parse(isoDate)
                if (date != null) {
                    return displayFormatter.format(date)
                }
            } catch (_: ParseException) {
            }
        }

        return "Invalid date"
    }
}