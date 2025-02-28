package com.oneasad.vitalstracker.di

import androidx.room.Room
import com.oneasad.vitalstracker.data.local.VitalsDatabase
import com.oneasad.vitalstracker.data.repository.VitalsRepository
import com.oneasad.vitalstracker.presentation.viewmodel.VitalsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            VitalsDatabase::class.java,
            "vitals_db"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<VitalsDatabase>().vitalsDao() }
    single { VitalsRepository(get()) }
    viewModel { VitalsViewModel(get()) }
}

