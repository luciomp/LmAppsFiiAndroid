package com.android.fiiapp.data

import com.android.fiiapp.R
import kotlinx.serialization.Serializable

@Serializable
data class Filter (
    var sortBy: Int = R.id.radio_item_sort_by_codigo,
    var sortOrderAsc: Boolean = true,
    var favorites: Boolean = false,

    var sectors: Set<String> = setOf(),
    var pl: Range = Range(),
    var price: Range = Range(),
    var dy: Range = Range(),
    var dy12m: Range = Range(),
    var vpa: Range = Range(),
    var pvpa: Range = Range(),
) {
    fun isEmpty(): Boolean {
        return sectors.isEmpty() && pl.isEmpty() && price.isEmpty() && dy.isEmpty() &&
                dy12m.isEmpty() && vpa.isEmpty() && pvpa.isEmpty()
    }
}
