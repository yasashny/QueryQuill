package org.queryquill.app.core.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager


/**
 * Vibration for key buttons
 * @param context The context required to access the system services such as Vibrator or VibratorManager.
 */
fun vibration(context: Context) {
    // For API 31 or higher
    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager =
            context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION") context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    vibrator.cancel()

    // Android 10 (API 29) or higher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        vibrator.vibrate(effect)
        // Android 8 (API 26) or higher
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val effect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
        vibrator.vibrate(effect)
    } else {
        // Below Android 8 (API 26)
        @Suppress("DEPRECATION") vibrator.vibrate(100)
    }
}