package com.example.worldnews

import android.app.Application
import com.example.worldnews.common.PreferenceHelper

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PreferenceHelper.init(this)
    }
}