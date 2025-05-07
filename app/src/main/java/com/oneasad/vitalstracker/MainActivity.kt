package com.oneasad.vitalstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.oneasad.vitalstracker.presentation.screens.VitalsApp
import com.oneasad.vitalstracker.ui.theme.VitalsTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VitalsTrackerTheme {
                VitalsApp()
            }
        }
    }
}

