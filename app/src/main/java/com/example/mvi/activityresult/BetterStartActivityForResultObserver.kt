package com.example.mvi.activityresult

import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class BetterStartActivityForResultObserver @Inject constructor(
    @ActivityContext private val appCompatContext: Context
) : DefaultLifecycleObserver {
    private var _launcher: BaseBetterActivityResult<Intent, ActivityResult>? = null

    fun launcher() = _launcher

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        if (appCompatContext is AppCompatActivity) {
            _launcher = BaseBetterActivityResult(
                appCompatContext,
                ActivityResultContracts.StartActivityForResult(),
                null
            )
        } else if (appCompatContext is ComponentActivity) {
            _launcher = BaseBetterActivityResult(
                appCompatContext,
                ActivityResultContracts.StartActivityForResult(),
                null
            )
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        _launcher = null
    }
}