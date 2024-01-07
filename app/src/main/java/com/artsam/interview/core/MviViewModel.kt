package com.artsam.interview.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class MviViewModel<S : MviState, I : MviIntent> : ViewModel() {

    // STATE
    abstract val emptyState: S

    private val mutableState: MutableStateFlow<S> by lazy { MutableStateFlow(emptyState) }
    val state: StateFlow<S> by lazy { mutableState.asStateFlow() }

    protected val currentState: S
        get() = state.value

    open fun setState(state: S) {
        mutableState.tryEmit(state)
    }

    // INTENT
    abstract fun handleIntent(intent: MviIntent)
}
