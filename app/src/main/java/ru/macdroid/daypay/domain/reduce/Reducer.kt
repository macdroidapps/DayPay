package ru.macdroid.daypay.domain.reduce

import ru.macdroid.daypay.domain.MainEffects
import ru.macdroid.daypay.domain.MainEvents
import ru.macdroid.daypay.domain.MainState

fun MainState.reduce(event: MainEvents): Pair<MainState, Set<MainEffects>> =
    when (event) {

        is MainEvents.MonthInput -> copy(
            month = event.item
        ) to emptySet()

        is MainEvents.SalaryInput -> copy(
            salary = event.item
        ) to emptySet()

        is MainEvents.WorkedDaysInput -> copy(
            workedDays = event.item
        ) to emptySet()

        is MainEvents.YearInput -> copy(
            year = event.item
        ) to emptySet()

        is MainEvents.ShowLoading -> copy(
            isLoading = true
        ) to emptySet()

        is MainEvents.SetSalary -> copy(
            salaryByMonth = (event.salary.toFloat() * 0.87).toString(),
            salaryByMonthWithoutNalog = event.salary
        ) to emptySet()

        is MainEvents.GetSalaryByMonth -> copy() to setOf(
            MainEffects.GetSalaryByMonth(event.salary, event.workedDays, event.year, event.month)
        )

        is MainEvents.SetWorkingDays -> copy(
            workingDaysInMonth = event.item
        ) to emptySet()
    }
