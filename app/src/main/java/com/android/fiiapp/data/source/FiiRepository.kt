package com.android.fiiapp.data.source

import android.util.Log
import com.android.fiiapp.data.Fii
import com.android.fiiapp.data.FiiList
import com.android.fiiapp.data.source.local.LocalDataSource
import com.android.fiiapp.data.source.remote.FiiRemoteDataSource

class FiiRepository(
    private val remoteDataSource: FiiRemoteDataSource,
    private val localDataSource: LocalDataSource<FiiList>
) {
    var fiiList: FiiList = localDataSource.read<FiiList>() ?: FiiList()
        private set

    suspend fun refresh() {
        fiiList = remoteDataSource.fetch()
        localDataSource.write(fiiList)
    }
}