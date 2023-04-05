package com.android.fiiapp.fiis

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.android.fiiapp.R
import com.android.fiiapp.business.BusinessLogic
import com.android.fiiapp.data.Fii
import kotlinx.coroutines.launch


class FiisViewModel(
    private val businessLogic: BusinessLogic
) : ViewModel() {

    private val _dataLoading = MutableLiveData(false)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _fiis = MutableLiveData(businessLogic.getFiiList())
    val fiis: LiveData<List<Fii>> = _fiis

    private val _activity = MutableLiveData<Intent?>(null)
    val activity: LiveData<Intent?> = _activity

    private val _favorites = MutableLiveData<List<String>>(businessLogic.favorites)
    val favorites: LiveData<List<String>> = _favorites

    fun update() {
        _fiis.value = businessLogic.getFiiList()
    }

    val filter = businessLogic.filter

    fun toggleSortOrder() {
        businessLogic.toggleSortOrder()
        _fiis.value = businessLogic.getFiiList()
    }
    fun toggleFilterFavorite() {
        businessLogic.toggleFavorite()
        _fiis.value = businessLogic.getFiiList()
    }
    fun toggleFavorite(code: String) {
        businessLogic.toggleFavorite(code)
        _favorites.value = businessLogic.favorites.toList()
        if (filter.favorites && !favorites.value?.contains(code)!! && fiis.value!!.any { i -> i.CODIGODOFUNDO == code }) {
            _fiis.value = fiis.value?.filter {
                it.CODIGODOFUNDO != code
            }
        }
    }

    fun refresh() {
        _dataLoading.value = true
        viewModelScope.launch {
            businessLogic.refresh()
            _fiis.value = businessLogic.getFiiList()
            _dataLoading.value = false
        }
    }

    fun openDetail(code: String) {
        _activity.value = Intent(Intent.ACTION_VIEW, Uri.parse("https://fiis.com.br/$code/"))
    }
}