package com.example.mvi.activityresult

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat

/**
 * https://developer.android.com/training/basics/intents/result
 * Get a result from an activity
 */

open class BaseBetterActivityResult<Input, Result> constructor(
    caller: ActivityResultCaller,
    contract: ActivityResultContract<Input, Result>,
    private var onActivityResult: OnActivityResult<Result>?
) : BetterActivityResult<Input, Result> {
    /**
     * Callback interface
     */
    interface OnActivityResult<O> {
        /**
         * Called after receiving a result from the target activity
         */
        fun onActivityResult(result: O)
    }

    private val launcher: ActivityResultLauncher<Input>

    init {
        launcher = caller.registerForActivityResult(contract) { result: Result -> callOnActivityResult(result) }
    }

    override fun setOnActivityResult(onActivityResult: OnActivityResult<Result>?) {
        this.onActivityResult = onActivityResult
    }

    /**
     * Launch activity, same as [ActivityResultLauncher.launch] except that it allows a callback
     * executed after receiving a result from the target activity.
     */
    /**
     * Same as [.launch] with last parameter set to `null`.
     */

    override fun launch(input: Input, onActivityResult: OnActivityResult<Result>?) {
        if (onActivityResult != null) {
            this.onActivityResult = onActivityResult
        }
        launcher.launch(input)
    }

    override fun launch(
        input: Input,
        options: ActivityOptionsCompat?,
        onActivityResult: OnActivityResult<Result>?
    ) {
        if (onActivityResult != null) {
            this.onActivityResult = onActivityResult
        }
        if (options != null) {
            launcher.launch(input, options)
        } else {
            launcher.launch(input)
        }
    }

    private fun callOnActivityResult(result: Result) {
        onActivityResult?.onActivityResult(result)
    }

    companion object {
        /**
         * Register activity result using a [ActivityResultContract] and an in-place activity result callback like
         * the default approach. You can still customise callback using [.launch].
         */
        fun <Input, Result> registerForActivityResult(
            caller: ActivityResultCaller,
            contract: ActivityResultContract<Input, Result>,
            onActivityResult: OnActivityResult<Result>?
        ): BaseBetterActivityResult<Input, Result> {
            return BaseBetterActivityResult(caller, contract, onActivityResult)
        }

        /**
         * Same as [.registerForActivityResult] except
         * the last argument is set to `null`.
         */
        fun <Input, Result> registerForActivityResult(
            caller: ActivityResultCaller,
            contract: ActivityResultContract<Input, Result>
        ): BaseBetterActivityResult<Input, Result> {
            return registerForActivityResult(caller, contract, null)
        }

        /**
         * Specialised method for launching new activities.
         */
        fun registerActivityForResult(
            caller: ActivityResultCaller
        ): BaseBetterActivityResult<Intent, ActivityResult> {
            return registerForActivityResult(caller, ActivityResultContracts.StartActivityForResult())
        }
    }
}