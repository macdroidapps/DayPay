package ru.macdroid.daypay.domain.reduce

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface IFeature<S, Ev, Eff> {
    val state: StateFlow<S>

    fun mutate(mutation: Ev)
    fun listen(
        scope: CoroutineScope,
        handler: IEffHandler<Eff, Ev>
    )
}