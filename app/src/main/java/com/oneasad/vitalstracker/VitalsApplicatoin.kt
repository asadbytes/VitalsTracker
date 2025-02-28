package com.oneasad.vitalstracker

import android.annotation.SuppressLint
import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.oneasad.vitalstracker.di.appModule
import java.util.concurrent.TimeUnit
import com.oneasad.vitalstracker.presentation.worker.VialsReminderWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class VitalsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VitalsApplication)
            modules(appModule)
        }
        scheduleReminderWork()
    }

    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    private fun scheduleReminderWork() {
        val reminderWorkRequest = PeriodicWorkRequest.Builder(
            VialsReminderWorker::class.java,
            5, TimeUnit.SECONDS
        ).build()

        WorkManager.getInstance(this).enqueue(reminderWorkRequest)
    }
}