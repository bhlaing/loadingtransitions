package com.simple.loadingtransition

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi

class LockLoadingIndicator: RelativeLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    super(context, attrs, defStyleAttr, defStyleRes)

    private var lockImageView: ImageView
    private var unlockImageView: ImageView
    private var loadingIndicator: ProgressBar
    private var circularBackground: ImageView

    init {
        LayoutInflater.from(context).inflate(R.layout.progress_dialog_lock_unlock, this, true)

        lockImageView = findViewById(R.id.successImageView)
        unlockImageView = findViewById(R.id.deniedImageView)
        loadingIndicator = findViewById(R.id.loadingIndicator)
        circularBackground = findViewById(R.id.successCircleView)
    }

    // region Success
    fun transitionToUnlocked() =
        unlockImageView.animate().apply {

            // reveal unlocked view slowly
            // over 0.8 seconds duration
            duration = 800
            alpha = 1.0f

            withStartAction {
                hideLockViewAndLoadingWheel()
                startSuccessBackgroundTransition()
            }


        }.start()


    private fun startSuccessBackgroundTransition() =
        with(circularBackground.background as AnimationDrawable) {
            start()
        }

    private fun hideLockViewAndLoadingWheel() =
        // slowly fade out lock view
        // over 0.8 seconds duration
        lockImageView.animate().apply {
            duration = 800
            alpha = 0.0f

            withEndAction {
                loadingIndicator.visibility = GONE
            }
        }
    // endregion

    // region Denied
    fun transitionToDenied() = startDeniedBackgroundTransition().also {
        loadingIndicator.visibility = GONE
    }

    private fun startDeniedBackgroundTransition() {
        circularBackground.background = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             context.getDrawable(R.drawable.animation_list_denied)
        } else {
            resources.getDrawable(R.drawable.animation_list_denied)
        }

        val animationDrawable= circularBackground.background as AnimationDrawable
        animationDrawable.start()
    }
    // endregion

    fun reset() {
        // resetting image to lock image
        unlockImageView.alpha = 0.0f
        lockImageView.alpha = 1.0f

        // reset circle image to gray
        val animationDrawable= circularBackground.background as AnimationDrawable
        animationDrawable.stop()
        animationDrawable.selectDrawable(0)

        // make spinning wheel visible
        loadingIndicator.visibility = VISIBLE

        // reset alpha for self
        alpha = 1.0f

        // reset background to default success state
        circularBackground.background = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getDrawable(R.drawable.animation_list_success)
        } else {
            resources.getDrawable(R.drawable.animation_list_success)
        }
    }
}