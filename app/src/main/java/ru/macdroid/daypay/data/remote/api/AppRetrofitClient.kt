package ru.macdroid.daypay.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.macdroid.daypay.data.remote.CALENDAR_ENDPOINT
import ru.macdroid.daypay.data.remote.dto.CalendarDTO
import ru.macdroid.daypay.data.remote.dto.MonthDTO

interface AppRetrofitClient {

    @GET(CALENDAR_ENDPOINT)
    suspend fun getCalendar(): CalendarDTO

    @GET("$CALENDAR_ENDPOINT/{year}/{month}")
    suspend fun getMonth(@Path("year") year: Int, @Path("month") month: Int): MonthDTO
}