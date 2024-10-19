package com.ahmety.newsapp.utilities

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
fun String.formatDate(): String {
    var formattedDate = ""
    val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    timeFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date: Date? = timeFormat.parse(this)
    val desiredFormat = SimpleDateFormat("dd/MM/yyyy")
    date?.let { dateTime ->
        formattedDate = desiredFormat.format(dateTime)
    }
    return formattedDate
}

@SuppressLint("SimpleDateFormat")
fun String.formatDateWithHour(): String {
    var formattedDate = ""
    val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    timeFormat.timeZone = TimeZone.getTimeZone("UTC")
    val date: Date? = timeFormat.parse(this)
    val desiredFormat = SimpleDateFormat("dd/MM/yyyy HH:MM")
    date?.let { dateTime ->
        formattedDate = desiredFormat.format(dateTime)
    }
    return formattedDate
}