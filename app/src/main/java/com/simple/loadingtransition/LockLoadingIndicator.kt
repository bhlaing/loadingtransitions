package com.simple.loadingtransition

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi

class LockLoadingIndicator: FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    super(context, attrs, defStyleAttr, defStyleRes)

    private var loadingIndicator: ProgressBar
    private var centerLockView: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.progress_dialog_lock_unlock, this, true)

        loadingIndicator = findViewById(R.id.spinningIndicator)
        centerLockView = findViewById(R.id.centerLockView)
    }

    // region Success
    fun transitionToUnlocked() {
        centerLockView.background =  safelyGetDrawable(R.drawable.animation_list_success)

        (centerLockView.background as AnimationDrawable).start()

        slowlyHideLoadingIndicator()
    }
    // endregion

    // region Denied
    fun transitionToDenied() {
        centerLockView.background = safelyGetDrawable(R.drawable.animation_list_denied)
        (centerLockView.background as AnimationDrawable).start()

        slowlyHideLoadingIndicator()
    }
    // endregion

    private fun safelyGetDrawable(@DrawableRes drawableId: Int) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        context.getDrawable(drawableId)
    } else {
        resources.getDrawable(drawableId)
    }

    private fun slowlyHideLoadingIndicator() =
        loadingIndicator.animate().apply {
            alpha(0.0f)
            duration = 500
        }.start()

    fun reset() {
        centerLockView.background = safelyGetDrawable(R.drawable.lock_default)
        loadingIndicator.alpha = 1.0f
    }
}