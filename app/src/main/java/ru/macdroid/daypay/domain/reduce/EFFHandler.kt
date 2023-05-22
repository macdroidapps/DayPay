package ru.macdroid.daypay.domain.reduce

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.macdroid.daypay.utils.NetworkState
import ru.macdroid.daypay.domain.MainEffects
import ru.macdroid.daypay.domain.MainEvents
import ru.macdroid.daypay.domain.usecases.GetSalaryByWorkDaysUsecase
import ru.macdroid.daypay.utils.AppConstants
import javax.inject.Inject

class EFFHandler @Inject constructor(
    private val getSalaryByWorkDaysUsecase: GetSalaryByWorkDaysUsecase
) : IEffHandler<MainEffects, MainEvents> {

    private lateinit var localJob: Job

    override suspend fun handle(
        effect: MainEffects,
        commit: (MainEvents) -> Unit
    ) {
        when (effect) {
            is MainEffects.GetSalaryByMonth -> {
                localJob = Job()

                withContext(context = GlobalScope.coroutineContext + localJob + Dispatchers.IO) {

                    getSalaryByWorkDaysUsecase(
                        salary = effect.salary,
                        workedDays = effect.workedDays,
                        year = effect.year,
                        month = effect.month
                    ).onEach { result ->

                        when (result) {
                            is NetworkState.Loading -> commit(
                                MainEvents.ShowLoading
                            )
                            is NetworkState.Success -> {
                                commit(
                                    MainEvents.SetSalary(
                                        result.data?.salary ?: AppConstants.EMPTY_STRING
                                    )
                                )
                                commit(
                                    MainEvents.SetWorkingDays(
                                        result.data?.workingDays ?: AppConstants.EMPTY_STRING
                                    )
                                )
                            }
                            is NetworkState.Error -> {

                            }

                        }
                    }.launchIn(this)
                }
            }
        }
    }
}