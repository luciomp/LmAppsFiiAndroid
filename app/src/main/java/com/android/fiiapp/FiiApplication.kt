package com.android.fiiapp

import android.app.Application
import com.android.fiiapp.business.BusinessLogic

class FiiApplication: Application() {
    val businessLogic: BusinessLogic
        get() = ServiceLocator.provide(this)
}