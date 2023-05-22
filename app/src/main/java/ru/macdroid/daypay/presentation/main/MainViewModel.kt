package ru.macdroid.daypay.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.macdroid.daypay.domain.MainEvents
import ru.macdroid.daypay.domain.reduce.EFFHandler
import ru.macdroid.daypay.domain.reduce.Feature
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    handler: EFFHandler,
    val feature: Feature
) : ViewModel() {

    init {
        feature.listen(viewModelScope, handler)
    }

    fun onEvent(event: MainEvents) {
        feature.mutate(event)
    }
}