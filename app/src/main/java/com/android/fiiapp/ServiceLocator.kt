package com.android.fiiapp

import android.content.Context
import android.util.Log
import com.android.fiiapp.business.BusinessLogic
import com.android.fiiapp.data.FiiList
import com.android.fiiapp.data.Filter
import com.android.fiiapp.data.source.FavoriteRepository
import com.android.fiiapp.data.source.FiiRepository
import com.android.fiiapp.data.source.FilterRepository
import com.android.fiiapp.data.source.local.LocalDataSource
import com.android.fiiapp.data.source.remote.FiiRemoteDataSource

object  ServiceLocator {

    private var businessLogic: BusinessLogic? = null

    private fun createBusinessLogic(context: Context): BusinessLogic {
        businessLogic = BusinessLogic(
            FiiRepository(FiiRemoteDataSource(),
                LocalDataSource(context, "fiis")),
            FilterRepository(LocalDataSource(context, "filters")),
            FavoriteRepository(LocalDataSource(context, "favorites"))
        )
        return businessLogic!!
    }

    fun provide(context: Context): BusinessLogic {
        Log.d("ServiceLocator", "provide: $businessLogic")
        return  businessLogic ?: businessLogic ?: createBusinessLogic(context)
    }

}