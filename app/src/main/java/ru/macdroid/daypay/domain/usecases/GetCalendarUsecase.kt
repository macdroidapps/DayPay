package ru.macdroid.daypay.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.macdroid.daypay.domain.model.Calendar
import ru.macdroid.daypay.domain.repository.AppRemoteRepository

import javax.inject.Inject

class GetCalendarUsecase @Inject constructor(
    private val appRemoteRepository: AppRemoteRepository
) {

    operator fun invoke(): Flow<Calendar> = flow {

        val calendar = appRemoteRepository.getClendar()

        emit(calendar)

    }
}
