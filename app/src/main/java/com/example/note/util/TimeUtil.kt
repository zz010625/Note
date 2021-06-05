package com.example.note.util

import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    private fun getTime(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    fun getSystemTime(): String {
        val systemTime: Calendar = Calendar.getInstance()
        systemTime.set(
            systemTime.get(Calendar.YEAR),
            systemTime.get(Calendar.MONTH),
            systemTime.get(Calendar.DAY_OF_MONTH),
            systemTime.get(Calendar.HOUR_OF_DAY),
            systemTime.get(Calendar.MINUTE)
        )
        return getTime(systemTime.time)
    }
}