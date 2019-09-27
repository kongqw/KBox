package com.kongqw.kbox.sample

import android.app.Application
import com.kongqw.kbox.network.NetworkManager

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        NetworkManager.init(this)
    }
}