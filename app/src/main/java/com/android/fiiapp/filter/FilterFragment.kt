package com.android.fiiapp.filter

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.android.fiiapp.R
import com.android.fiiapp.databinding.FragmentFiltrosBinding
import com.android.fiiapp.util.getViewModelFactory

class FilterFragment : Fragment(), NavController.OnDestinationChangedListener {

    private val viewModel by viewModels<FilterViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = DataBindingUtil.inflate<FragmentFiltrosBinding>(
            inflater, R.layout.fragment_filtros, container, false
        )
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewmodel = viewModel

        val a = binding.root.findViewById<RecyclerView>(R.id.rv_setores)
        a.adapter = SectorsAdapter(viewModel)

        setHasOptionsMenu(true)

        return binding?.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.filter_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_item_limpar_filtros -> {
                viewModel.clearFilter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        if (destination.id != R.id.frag_filtros) {
            viewModel.confirm()
        }
    }

}