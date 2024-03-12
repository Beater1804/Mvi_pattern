package com.example.mvi.base

import android.os.Build
import android.os.Looper
import androidx.annotation.CallSuper
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import com.example.mvi.mvipattern.MviIntent
import com.example.mvi.mvipattern.MviSingleEvent
import com.example.mvi.mvipattern.MviViewModel
import com.example.mvi.mvipattern.MviViewState
import com.example.mvi.util.Logger
import com.example.mvi.util.debugCheckImmediateMainDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.onSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseMviViewModel<
        I : MviIntent,
        S : MviViewState,
        E : MviSingleEvent,
        > : MviViewModel<I, S, E>, ViewModel() {
    protected open val rawLogTag: String? = null

    private companion object {
        private const val MAX_TAG_LENGTH = 23
    }

    protected val logTag by lazy(LazyThreadSafetyMode.PUBLICATION) {
        (rawLogTag ?: this::class.java.simpleName).let { tag: String ->
            // Tag length limit was removed in API 26.
            if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= 26) {
                tag
            } else {
                tag.take(MAX_TAG_LENGTH)
            }
        }
    }
    private val initialState: S by lazy { setInitialState() }

    private val _viewState = MutableStateFlow(initialState)

    override val viewState: StateFlow<S> = _viewState.asStateFlow()

    private val eventChannel = Channel<E>(Channel.UNLIMITED)

    final override val singleEvent: Flow<E> = eventChannel.consumeAsFlow()

    private val intentMutableFlow = MutableSharedFlow<I>(extraBufferCapacity = Int.MAX_VALUE)

    protected val intentSharedFlow: SharedFlow<I> get() = intentMutableFlow

    @MainThread
    final override suspend fun processIntent(intent: I) {
        debugCheckMainThread()
        debugCheckImmediateMainDispatcher()

        check(intentMutableFlow.tryEmit(intent)) { "Failed to emit intent: $intent" }
        Logger.tag(logTag).log("processIntent: $intent")
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        eventChannel.close()
        Logger.tag(logTag).log("onCleared")
    }

    protected suspend fun sendEvent(event: E) {
        debugCheckMainThread()
        debugCheckImmediateMainDispatcher()

        eventChannel.trySend(event)
            .onSuccess { Logger.tag(logTag).log("sendEvent: event= $event") }
            .onFailure {
                Logger
                    .tag(logTag)
                    .log("$it >>>> Failed to send event: $event")
            }
            .getOrThrow()
    }
}

private fun debugCheckMainThread() {
//    if (BuildConfig.DEBUG) {
//        check(Looper.getMainLooper() === Looper.myLooper()) {
//            "Expected to be called on the main thread but was " + Thread.currentThread().name
//        }
//    }
}