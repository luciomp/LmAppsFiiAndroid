package com.android.fiiapp.fiis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.android.fiiapp.R
import com.android.fiiapp.data.Fii
import com.android.fiiapp.databinding.FiiItemBinding
import com.android.fiiapp.util.BindableAdapter


class FiisAdapter(
    private val viewModel: FiisViewModel) :
    RecyclerView.Adapter<FiisAdapter.ViewHolder>(), BindableAdapter<List<Fii>> {

    private var dataSet : List<Fii> = listOf()
    override fun getItemCount() = dataSet.size

    override fun setData(data: List<Fii>) {
        dataSet = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(val binding: FiiItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Fii) {
            binding.viewmodel = viewModel
            binding.fii = item
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        var binding = DataBindingUtil.inflate<FiiItemBinding>(
            inflater, R.layout.fii_item, viewGroup, false
        )
        binding.lifecycleOwner = viewGroup.findViewTreeLifecycleOwner()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(dataSet[position])
    }
}
