package ru.macdroid.daypay.domain.repository

import ru.macdroid.daypay.domain.model.Calendar
import ru.macdroid.daypay.domain.model.Month


interface AppRemoteRepository {
    suspend fun getClendar(): Calendar
    suspend fun getSalaryByWorkDays(year: String, month: String): Month
}