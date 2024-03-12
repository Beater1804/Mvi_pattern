package com.example.mvi.base
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import com.example.mvi.activityresult.BaseBetterActivityResult
import com.example.mvi.activityresult.BetterStartActivityForResultObserver
import javax.inject.Inject

abstract class BaseComponentActivity : ComponentActivity(), UIController {
    @Composable
    protected abstract fun ComposeReady(savedInstanceState: Bundle?)

    val activityResult = object : BaseBetterActivityResult.OnActivityResult<ActivityResult> {
        override fun onActivityResult(result: ActivityResult) {

        }
    }

    @Inject
    lateinit var startActivityForResultObserver: BetterStartActivityForResultObserver
//    @Inject
//    lateinit var analyticsHelper: AnalyticsHelper
//
//    @Inject
//    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (::startActivityForResultObserver.isInitialized) {
            lifecycle.addObserver(startActivityForResultObserver)
        }
        // Turn off the decor fitting system windows, which allows us to handle insets,
        // including IME animations, and go edge-to-edge
        // This also sets up the initial system bar style based on the platform theme
        enableEdgeToEdge()
        setContent {
            // val darkTheme = shouldUseDarkTheme(LiveStreamActivityUiState.Idle)
            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            val darkTheme = true//isSystemInDarkTheme()
            val androidTheme = true
            val disableDynamicTheming = false

            // Update the edge to edge configuration to match the theme
            // This is the same parameters as the default enableEdgeToEdge call, but we manually
            // resolve whether or not to show dark theme using uiState, since it can be different
            // than the configuration's dark theme value based on the user preference.
            DisposableEffect(darkTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.TRANSPARENT,
                        Color.TRANSPARENT,
                    ) { darkTheme },
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim,
                        darkScrim,
                    ) { darkTheme },
                )
                onDispose {}
            }

//            CompositionLocalProvider(LocalAnalyticsHelper provides analyticsHelper) {
//                FslTheme(
//                    darkTheme = darkTheme,
//                    androidTheme = androidTheme,
//                    disableDynamicTheming = disableDynamicTheming
//                ) {
                    ComposeReady(savedInstanceState)
//                }
//            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::startActivityForResultObserver.isInitialized) {
            lifecycle.removeObserver(startActivityForResultObserver)
        }
    }
}

/**
 * Returns `true` if the Android theme should be used, as a function of the [uiState].
 */
//@Composable
//private fun shouldUseAndroidTheme(
//    uiState: LiveStreamActivityUiState,
//): Boolean = when (uiState) {
//    LiveStreamActivityUiState.Idle -> false
////    is Success -> when (uiState.userData.themeBrand) {
////        ThemeBrand.DEFAULT -> false
////        ThemeBrand.ANDROID -> true
////    }
//    else -> true
//}


/**
 * Returns `true` if the dynamic color is disabled, as a function of the [uiState].
 */
//@Composable
//private fun shouldDisableDynamicTheming(
//    uiState: LiveStreamActivityUiState,
//): Boolean = when (uiState) {
//    LiveStreamActivityUiState.Idle -> false
////    is Success -> !uiState.userData.useDynamicColor
//    else -> true
//}

/**
 * Returns `true` if dark theme should be used, as a function of the [uiState] and the
 * current system context.
 */
//@Composable
//private fun shouldUseDarkTheme(
//    uiState: LiveStreamActivityUiState,
//): Boolean = when (uiState) {
//    LiveStreamActivityUiState.Idle -> isSystemInDarkTheme()
////    is Success -> when (uiState.userData.darkThemeConfig) {
////        DarkThemeConfig.FOLLOW_SYSTEM -> isSystemInDarkTheme()
////        DarkThemeConfig.LIGHT -> false
////        DarkThemeConfig.DARK -> true
////    }
//    else -> true
//}

@Composable
private fun IntSize.toDpSize(): DpSize = with(LocalDensity.current) {
    DpSize(width.toDp(), height.toDp())
}

/**
 * The default light scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=35-38;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val lightScrim = android.graphics.Color.argb(0xe6, 0xFF, 0xFF, 0xFF)

/**
 * The default dark scrim, as defined by androidx and the platform:
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:activity/activity/src/main/java/androidx/activity/EdgeToEdge.kt;l=40-44;drc=27e7d52e8604a080133e8b842db10c89b4482598
 */
private val darkScrim = android.graphics.Color.argb(0x80, 0x1b, 0x1b, 0x1b)

