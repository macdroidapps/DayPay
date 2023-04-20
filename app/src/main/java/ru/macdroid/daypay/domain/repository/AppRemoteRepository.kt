package ru.macdroid.daypay.domain.repository

import ru.macdroid.daypay.domain.model.Calendar
import ru.macdroid.daypay.domain.model.Month


interface AppRemoteRepository {
    suspend fun getClendar(): Calendar
    suspend fun getMonth(year: Int, month: Int): Month
}