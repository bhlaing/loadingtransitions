package com.simple.loadingtransition

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.animation.BounceInterpolator
import android.widget.*
import androidx.annotation.RequiresApi

class SimpleLoadingIndicator: FrameLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
            super(context, attrs, defStyleAttr, defStyleRes)

    private var successImageView: ImageView
    private var deniedImageView: ImageView
    private var loadingIndicator: ProgressBar
    private var statusTextView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.progress_dialog_generic, this, true)

        successImageView = findViewById(R.id.successImageView)
        deniedImageView = findViewById(R.id.deniedImageView)
        loadingIndicator = findViewById(R.id.loadingIndicator)
        statusTextView = findViewById(R.id.statusTextView)
    }

    fun processCompletedSuccessfully() {
        transitionToIntendedView(successImageView).also {
            statusTextView.text = "Success!"
        }
    }

    fun processFailed() {
        transitionToIntendedView(deniedImageView).also {
            statusTextView.text = "Denied!"
        }
    }

    private fun transitionToIntendedView(intendedView: ImageView) {
        intendedView.animate().apply {
            // apply bouncing animation
            interpolator = BounceInterpolator()

            // Hide loading indicator and make intended image view visible
            // at the beginning of this transition
            withStartAction {
                loadingIndicator.visibility = View.INVISIBLE
                intendedView.visibility = View.VISIBLE
            }

            // scale x2 horizontally and vertically
            // over 0.8 seconds duration
            duration = 800
            scaleX(2.0f)
            scaleY(2.0f)

            start()
        }
    }

    fun reset() {
        //show loading wheel and hide success/denied image views
        loadingIndicator.visibility = View.VISIBLE
        successImageView.visibility = View.INVISIBLE
        deniedImageView.visibility = View.INVISIBLE

        // rescale success image view to original size
        successImageView.scaleX = 1.0f
        successImageView.scaleY = 1.0f
        // rescale denied image view to original size
        deniedImageView.scaleX = 1.0f
        deniedImageView.scaleY = 1.0f

        // change text back to original
        statusTextView.text = "Loading.."
    }

}