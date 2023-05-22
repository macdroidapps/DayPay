package ru.macdroid.daypay.domain.reduce

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch
import ru.macdroid.daypay.domain.MainEffects
import ru.macdroid.daypay.domain.MainEvents
import ru.macdroid.daypay.domain.MainState
import ru.macdroid.daypay.domain.reduce.reduce
import javax.inject.Inject

class Feature @Inject constructor() : IFeature<MainState, MainEvents, MainEffects> {

    private fun initialState(): MainState = MainState()
    private fun initialEffects(): Set<MainEffects> = emptySet()

    private val mutations: MutableSharedFlow<MainEvents> = MutableSharedFlow()
    private val _state: MutableStateFlow<MainState> = MutableStateFlow(initialState())
    override val state = _state.asStateFlow()

    private lateinit var _scope: CoroutineScope

    override fun mutate(mutation: MainEvents) {
        _scope.launch {
            mutations.emit(mutation)
        }
    }

    override fun listen(
        scope: CoroutineScope,
        handler: IEffHandler<MainEffects, MainEvents>
    ) {
        _scope = scope
        _scope.launch {
            mutations
                .onEach {
//                    Log.d("happy", "MUTATION: $it")
                }
                .scan(initialState() to initialEffects()) { (s, _), m ->
                    s.reduce(m)
                }
                .collect { (s, effs) ->
                    _state.emit(s)
                    effs.forEach { eff ->
                        _scope.launch {
                            handler.handle(eff) { event ->
                                mutate(event)
                            }
                        }
                    }
                }
        }
    }
}




