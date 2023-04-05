package com.android.fiiapp.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.android.fiiapp.R
import com.android.fiiapp.databinding.SectorCheckboxItemBinding
import com.android.fiiapp.util.BindableAdapter
import java.util.*


class SectorsAdapter(
    private val viewModel: FilterViewModel) :
    RecyclerView.Adapter<SectorsAdapter.ViewHolder>(),
    BindableAdapter<SortedSet<String>>
{
    private var dataSet : SortedSet<String> = sortedSetOf()
    override fun getItemCount() = dataSet.size

    override fun setData(data: SortedSet<String>) {
        dataSet = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: SectorCheckboxItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val sector: String = dataSet.toList()[position]
            binding.viewmodel = viewModel
            binding.sector = sector
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        var binding = DataBindingUtil.inflate<SectorCheckboxItemBinding>(
            inflater, R.layout.sector_checkbox_item, viewGroup, false
        )
        binding.lifecycleOwner = viewGroup.findViewTreeLifecycleOwner()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(position)
    }
}