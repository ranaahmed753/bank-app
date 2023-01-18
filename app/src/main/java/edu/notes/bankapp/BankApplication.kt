package edu.notes.bankapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BankApplication:Application(){
    override fun onCreate() {
        super.onCreate()

    }
}