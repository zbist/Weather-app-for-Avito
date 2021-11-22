package com.zbistapp.weatherappforavito

import android.app.Application
import com.zbistapp.weatherappforavito.di.AppComponent
import com.zbistapp.weatherappforavito.di.ApplicationModule
import com.zbistapp.weatherappforavito.di.DaggerAppComponent

class App : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    companion object {
        lateinit var INSTANCE: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }

}