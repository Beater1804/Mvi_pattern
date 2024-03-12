package com.example.mvi.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart


class Logger(
    private val tag: String,
    private val isDebug: Boolean = true,
) {
    fun log(msg: String) {
        if (!isDebug) {
            // production logging - Crashlytics or whatever you want to use
        } else {
            printLogD(tag, msg)
        }
    }


    companion object Factory {
        fun buildDebug(className: String = "StubAnalyticsHelper"): Logger {
            return Logger(
                tag = className,
                isDebug = true,
            )
        }

        fun buildRelease(className: String): Logger {
            return Logger(
                tag = className,
                isDebug = false,
            )
        }

        fun tag(className: String) = Logger(
            tag = className,
            isDebug = true,
        )
    }
}

fun LOG(message: String) = Logger.buildDebug().log(message)
fun printLogD(tag: String?, message: String) {
    println("$tag: $message")
}

fun <S> Flow<S>.lifecycleLog(name: String): Flow<S> = this.onStart {
//        this.info
}.onEach {

}.onCompletion {

}

