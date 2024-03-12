package com.example.mvi.activityresult

import androidx.core.app.ActivityOptionsCompat

interface BetterActivityResult<Input, Result> {
    fun setOnActivityResult(onActivityResult: BaseBetterActivityResult.OnActivityResult<Result>?)

    fun launch(
        input: Input,
        onActivityResult: BaseBetterActivityResult.OnActivityResult<Result>?
    )

    fun launch(
        input: Input,
        options: ActivityOptionsCompat?,
        onActivityResult: BaseBetterActivityResult.OnActivityResult<Result>?
    )
}