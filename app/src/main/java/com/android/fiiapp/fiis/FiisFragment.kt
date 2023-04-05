package com.android.fiiapp.fiis

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.RecyclerView
import com.android.fiiapp.R
import com.android.fiiapp.databinding.FragmentFiiListBinding
import com.android.fiiapp.util.getViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationMenuItemView

class FiisFragment : Fragment() {

    private val viewModel by viewModels<FiisViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentFiiListBinding>(
            inflater, R.layout.fragment_fii_list, container, false)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        val a = binding.root.findViewById<RecyclerView>(R.id.rv_fii)
        a.adapter = FiisAdapter(viewModel)
        viewModel.update()

        setHasOptionsMenu(true)

        val obs = Observer<Intent?> { activity: Intent? ->
            activity?.let { requireContext().startActivity(it) }
        }
        viewModel.activity.observe(viewLifecycleOwner, obs)

        activity?.findViewById<BottomNavigationView>(R.id.bottom_navbar)?.menu?.
        findItem(R.id.frag_filtros)?.let {
            if (viewModel.filter.isEmpty()) {
                it.setIcon(R.drawable.ic_baseline_filter_alt_24)
            } else {
                it.setIcon(R.drawable.ic_filter_filtered)
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)

        setFavoriteIcon(menu.findItem(R.id.app_bar_favorites))
        setOrderAscIcon(menu.findItem(R.id.app_bar_sortorder))
    }

    private fun setFavoriteIcon(item: MenuItem) {
        if (viewModel.filter.favorites) {
            item.setIcon(R.drawable.ic_star)
        } else {
            item.setIcon(R.drawable.ic_star_outline)
        }
    }

    private fun setOrderAscIcon(item: MenuItem) {
        if (viewModel.filter.sortOrderAsc) {
            item.setIcon(R.drawable.ic_sort_asc)
        } else {
            item.setIcon(R.drawable.ic_sort_desc)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("FiisFragment: ", "onOptionsItemSelected")
        val navController = findNavController()
        return when (item.itemId) {
            R.id.app_bar_sortorder -> {
                viewModel.toggleSortOrder()
                setOrderAscIcon(item)
                true
            }
            R.id.app_bar_favorites -> {
                viewModel.toggleFilterFavorite()
                setFavoriteIcon(item)
                true
            } else ->
                item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

}