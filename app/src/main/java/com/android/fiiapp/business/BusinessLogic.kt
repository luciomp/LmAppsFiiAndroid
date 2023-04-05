package com.android.fiiapp.business

import android.util.Log
import com.android.fiiapp.R
import com.android.fiiapp.data.source.FavoriteRepository
import com.android.fiiapp.data.source.FiiRepository
import com.android.fiiapp.data.source.FilterRepository
import com.android.fiiapp.data.Fii
import com.android.fiiapp.data.Range
import java.util.*

class BusinessLogic (
    private val fiiRepository: FiiRepository,
    private val filterRepository: FilterRepository,
    private val favoriteRepository: FavoriteRepository
) {
    val filter = filterRepository.filter
    val fiis = fiiRepository.fiiList

    fun rangeOf(i: Fii.() -> Double) = fiiRepository.fiiList.rangeOf(i)

    suspend fun refresh() {
        fiiRepository.refresh()
    }

    var sortBy: Int
        get() = filter.sortBy
        set(value) { filterRepository.sortBy = value }

    val favorites = favoriteRepository.favorites
    fun toggleFavorite(code: String) = favoriteRepository.toggleFavorite(code)

    fun setFilters(sectors: Set<String>, pl: Range, price: Range,
                   dy: Range, dy12m: Range, vpa: Range, pvpa: Range
    ) = filterRepository.setFilters(sectors, pl, price, dy, dy12m, vpa, pvpa)
    fun toggleFavorite() = filterRepository.toggleFavorite()
    fun toggleSortOrder() = filterRepository.toggleSortOrder()

    private fun shouldFilterOut(min: Double?, higher: Double?, v: Double): Boolean {
        return (min != null && v < min) || (higher != null && v > higher)
    }

    fun isFavorite(code: String): Boolean {
        return code in favorites
    }

    fun <T : Comparable<T>> sortUsing(fiis: List<Fii>, fn: Fii.() -> T, asc: Boolean): List<Fii> {
        return if (asc) {
            fiis.sortedBy { it.fn() }
        } else {
            fiis.sortedByDescending { it.fn() }
        }
    }

    private fun sortByAtt(filteredFiiList: List<Fii>, i: Int, asc: Boolean): List<Fii> {
        return when (i) {
            R.id.radio_item_sort_by_codigo -> {
                sortUsing (filteredFiiList, Fii::CODIGODOFUNDO, asc)
            }
            R.id.radio_item_sort_by_setor -> {
                sortUsing (filteredFiiList, Fii::SETOR, asc )
            }
            R.id.radio_item_sort_by_pl -> {
                sortUsing (filteredFiiList, Fii::PATRIMONIOLIQ, asc)
            }
            R.id.radio_item_sort_by_preco -> {
                sortUsing (filteredFiiList, Fii::PRECOATUAL, asc)
            }
            R.id.radio_item_sort_by_dy -> {
                sortUsing (filteredFiiList, Fii::DIVIDENDYIELD, asc)
            }
            R.id.radio_item_sort_by_dy12m -> {
                sortUsing (filteredFiiList, Fii::DY12MACUMULADO, asc)
            }
            R.id.radio_item_sort_by_vpa -> {
                sortUsing (filteredFiiList, Fii::VPA, asc)
            }
            R.id.radio_item_sort_by_pvpa -> {
                sortUsing (filteredFiiList, Fii::PVPA, asc)
            }
            else -> sortUsing (filteredFiiList, Fii::CODIGODOFUNDO, asc)
        }
    }

    fun getFiiList(): List<Fii> {
        val filter = filterRepository.filter
        val filteredFiiList = fiiRepository.fiiList.fiis.filter {
            // Sectors
            if (filter.sectors.isNotEmpty() && !filter.sectors.contains(it.SETOR)) false
            // PL
            else if (shouldFilterOut(filter.pl.min, filter.pl.max, it.PATRIMONIOLIQ)) false
            // Price
            else if (shouldFilterOut(filter.price.min, filter.price.max, it.PRECOATUAL)) false
            // DY
            else if (shouldFilterOut(filter.dy.min, filter.dy.max, it.DIVIDENDYIELD)) false
            // DY 12 M
            else if (shouldFilterOut(filter.dy12m.min, filter.dy12m.max, it.DY12MACUMULADO)) false
            // VPA
            else if (shouldFilterOut(filter.vpa.min, filter.vpa.max, it.VPA)) false
            // P/VPA
            else if (shouldFilterOut(filter.pvpa.min, filter.pvpa.max, it.PVPA)) false
            // Favorites
            else if (filter.favorites && !isFavorite(it.CODIGODOFUNDO)) false
            else (true)
        }
        return sortByAtt(filteredFiiList, filter.sortBy, filter.sortOrderAsc)
    }
}