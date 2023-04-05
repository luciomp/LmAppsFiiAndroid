package com.android.fiiapp.data.source

import com.android.fiiapp.data.source.local.LocalDataSource

class FavoriteRepository(
    private val localDataSource: LocalDataSource<MutableList<String>>
) {
    var favorites: MutableList<String> = localDataSource.read() ?: mutableListOf()
        private set

    fun toggleFavorite(code: String) {
        if (code in favorites) {
            favorites.remove(code)
        } else {
            favorites.add(code)
        }
        localDataSource.write(favorites)
    }
}