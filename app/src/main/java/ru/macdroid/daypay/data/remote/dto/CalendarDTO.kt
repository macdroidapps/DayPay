package ru.macdroid.daypay.data.remote.dto

import ru.macdroid.daypay.domain.model.Calendar

data class CalendarDTO(
    val status: Int,
    val years: List<Int>
)

// map CalendarDTO to Calendar
fun CalendarDTO.mapToCalendar(): Calendar {
    return Calendar(
        status = status,
        years = years
    )
}