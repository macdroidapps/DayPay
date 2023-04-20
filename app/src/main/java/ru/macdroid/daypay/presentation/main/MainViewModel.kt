package ru.macdroid.daypay.presentation.main

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import ru.macdroid.daypay.domain.MainEvents
import ru.macdroid.daypay.domain.MainState
import ru.macdroid.daypay.domain.model.Month
import ru.macdroid.daypay.domain.usecases.GetCalendarUsecase
import ru.macdroid.daypay.domain.usecases.GetMonthUsecase
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCalendarUsecase: GetCalendarUsecase,
    private val getMonthUsecase: GetMonthUsecase
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    fun onEvent(event: MainEvents) {
        when (event) {
            is MainEvents.GetCalendar -> {
                getCalendar()
            }
            is MainEvents.MonthInput -> {
                _state.value = _state.value.copy(
                    month = event.item
                )
            }
            is MainEvents.SalaryInput -> {
                _state.value = _state.value.copy(
                    salary = event.item
                )
            }
            is MainEvents.WorkingDaysInput -> {
                _state.value = _state.value.copy(
                    workingDays = event.item
                )
            }
            is MainEvents.YearInput -> {
                _state.value = _state.value.copy(
                    year = event.item
                )
            }
            is MainEvents.GetMonthSalaryByMonth -> {
                getInfoByMonth()
            }
        }
    }

    private fun getCalendar() {
        viewModelScope.launch {
            getCalendarUsecase().onEach { result ->

                Log.d("happy", "getCalendar: $result")

            }.launchIn(this)
        }

    }

    private fun getInfoByMonth() {
        viewModelScope.launch {
            getMonthUsecase.invoke(
                year = _state.value.year,
                month = _state.value.month
            ).onEach { result ->

                _state.value = _state.value.copy(
                    salaryByMonth = setSallaryByMonth(result) * 0.87f,
                    salaryByMonthWithoutNalog = setSallaryByMonth(result),
                    workingDaysInMonth = result.month.workingDays
                )

            }.launchIn(this)
        }
    }

    private fun setSallaryByMonth(result: Month) : Float {
        return _state.value.salary * _state.value.workingDays / result.month.workingDays
    }




}
