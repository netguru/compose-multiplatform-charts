package com.netguru.multiplatform.charts.application

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
internal abstract class Store<Action, State>(
    initialState: State,
    dispatcher: CoroutineDispatcher,
) {

    val state: StateFlow<State>
        get() = _state

    private val _state = MutableStateFlow(initialState)
    private val actions = MutableSharedFlow<Action>()
    private val storeScope = CoroutineScope(dispatcher + SupervisorJob())

    fun dispatch(action: Action) {
        storeScope.launch { actions.emit(action) }
    }

    open fun onCleared() {
        storeScope.cancel()
    }

    protected fun updateState(function: State.() -> State) = _state.update(function)

    protected inline fun <reified SpecificAction : AppAction> onAction(
        crossinline function: suspend (SpecificAction) -> Unit,
    ) {
        actions
            .filterIsInstance<SpecificAction>()
            .mapLatest { function(it) }
            .launchIn(storeScope)
    }
}
