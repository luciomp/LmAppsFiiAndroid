package com.android.fiiapp.data.source

import android.util.Log
import com.android.fiiapp.data.Filter
import com.android.fiiapp.data.Range
import com.android.fiiapp.data.source.local.LocalDataSource

class FilterRepository (
    private val localDataSource: LocalDataSource<Filter>
) {
    val filter: Filter = localDataSource.read() ?: Filter()

    var sortBy: Int
        get() = filter.sortBy
        set(value) {
            filter.sortBy = value
            localDataSource.write(filter)
        }

    fun setFilters(
        sectors: Set<String>,
        pl: Range,
        price: Range,
        dy: Range,
        dy12m: Range,
        vpa: Range,
        pvpa: Range)
    {
        filter.sectors = sectors
        filter.pl = pl
        filter.price = price
        filter.dy = dy
        filter.dy12m = dy12m
        filter.vpa = vpa
        filter.pvpa = pvpa
        localDataSource.write(filter)
    }

    fun toggleSortOrder() {
        filter.sortOrderAsc = !filter.sortOrderAsc
        localDataSource.write(filter)
    }

    fun toggleFavorite() {
        filter.favorites = !filter.favorites
        localDataSource.write(filter)
    }
}