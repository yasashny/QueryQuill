package org.queryquill.app.core.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator


fun vibration(context: Context) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.cancel()

    // Android 10 or higher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val effect = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        vibrator.vibrate(effect)
    } else {
        // Below Android 10
        vibrator.vibrate(100)
    }
}