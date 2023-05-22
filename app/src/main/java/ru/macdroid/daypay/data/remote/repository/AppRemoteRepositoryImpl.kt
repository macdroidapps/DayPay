package ru.macdroid.daypay.data.remote.repository

import ru.macdroid.daypay.data.remote.api.AppRetrofitClient
import ru.macdroid.daypay.data.remote.dto.mapToCalendar
import ru.macdroid.daypay.data.remote.dto.mapToMonth
import ru.macdroid.daypay.domain.model.Calendar
import ru.macdroid.daypay.domain.model.Month
import ru.macdroid.daypay.domain.repository.AppRemoteRepository
import javax.inject.Inject

class AppRemoteRepositoryImpl @Inject constructor(
    private val api: AppRetrofitClient
) : AppRemoteRepository {

    override suspend fun getClendar(): Calendar {
        return api.getCalendar().mapToCalendar()
    }

    override suspend fun getSalaryByWorkDays(year: String, month: String): Month {
        return api.getMonth(year, month).mapToMonth()
    }
}