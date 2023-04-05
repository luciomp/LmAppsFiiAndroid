package com.android.fiiapp.sortby

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.fiiapp.R
import com.android.fiiapp.business.BusinessLogic

class SortByViewModel(
    private val businessLogic: BusinessLogic
): ViewModel() {
    val sortBy = MutableLiveData<Int>(businessLogic.filter.sortBy)

    fun confirm() {
        Log.d("SortByViewModel", "confirm ${sortBy.value}")
        businessLogic.sortBy = sortBy.value!!
    }
}