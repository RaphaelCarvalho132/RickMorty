package com.raphael.carvalho.rickmorty

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class CustomApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())// TODO implement Tree for production
    }
}