package com.android.fiiapp.sortby

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.android.fiiapp.R
import com.android.fiiapp.databinding.FragmentSortbyBinding
import com.android.fiiapp.util.getViewModelFactory

class SortByFragment : Fragment(), NavController.OnDestinationChangedListener {

    private val viewModel by viewModels<SortByViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = DataBindingUtil.inflate<FragmentSortbyBinding>(
            inflater, R.layout.fragment_sortby, container, false
        )
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewmodel = viewModel

        return binding?.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().addOnDestinationChangedListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        findNavController().removeOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id != R.id.frag_sortBy) {
            viewModel.confirm()
        }
    }
}
