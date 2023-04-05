package com.android.fiiapp.util

import androidx.fragment.app.Fragment
import com.android.fiiapp.FiiApplication
import com.android.fiiapp.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val businessLogic = (requireContext().applicationContext as FiiApplication).businessLogic
    return ViewModelFactory(businessLogic, this)
}