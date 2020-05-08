package com.simple.loadingtransition

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var simpleLoadingIndicator: SimpleLoadingIndicator
    private lateinit var lockLoadingIndicator: LockLoadingIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        simpleLoadingIndicator = findViewById(R.id.simpleLoadingIndicator)

        findViewById<Button>(R.id.successButton).setOnClickListener {
            simpleLoadingIndicator.reset()
            simpleLoadingIndicator.processCompletedSuccessfully()
        }

        findViewById<Button>(R.id.deniedButton).setOnClickListener {
            simpleLoadingIndicator.reset()
            simpleLoadingIndicator.processFailed()
        }

        findViewById<Button>(R.id.resetSimpleLoadingIndicatorButton).setOnClickListener {
            simpleLoadingIndicator.reset()
        }

        lockLoadingIndicator = findViewById(R.id.lockLoadingIndicator)


        findViewById<Button>(R.id.successLockButton).setOnClickListener {
            lockLoadingIndicator.reset()
            lockLoadingIndicator.transitionToUnlocked()
        }

        findViewById<Button>(R.id.deniedLockButton).setOnClickListener {
            lockLoadingIndicator.reset()
            lockLoadingIndicator.transitionToDenied()
        }

        findViewById<Button>(R.id.resetLockLoadingIndicatorButton).setOnClickListener {
            lockLoadingIndicator.reset()
        }
    }
}
