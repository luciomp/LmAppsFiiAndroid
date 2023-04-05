package com.android.fiiapp

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.android.fiiapp.business.BusinessLogic
import com.android.fiiapp.fiis.FiisViewModel
import com.android.fiiapp.filter.FilterViewModel
import com.android.fiiapp.sortby.SortByViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val businessLogic: BusinessLogic,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(FiisViewModel::class.java) ->
                FiisViewModel(businessLogic)
            isAssignableFrom(FilterViewModel::class.java) ->
                FilterViewModel(businessLogic)
            isAssignableFrom(SortByViewModel::class.java) ->
                SortByViewModel(businessLogic)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}