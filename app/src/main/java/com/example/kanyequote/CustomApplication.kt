package com.example.kanyequote

import android.app.Application


import io.branch.referral.Branch

class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Branch logging for debugging
        Branch.enableLogging()

        // Branch object initialization
        Branch.getAutoInstance(this)
    }
}