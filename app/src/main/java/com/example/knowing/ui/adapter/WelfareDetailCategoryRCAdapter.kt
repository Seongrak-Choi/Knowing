package com.example.knowing.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knowing.databinding.ItemRcDialogCollegeBinding
import com.example.knowing.databinding.ItemRcDialogDoBinding
import com.example.knowing.databinding.ItemWelfareDetailCateogryBinding

class WelfareDetailCategoryRCAdapter(private val categoryList:List<String>):
    RecyclerView.Adapter<WelfareDetailCategoryRCAdapter.ViewHolder>(){

    private var listener: SelectDoDialogRCAdapter.OnItemClickListener? = null

    inner class ViewHolder(val binding: ItemWelfareDetailCateogryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.txCategory.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemWelfareDetailCateogryBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(categoryList[position])
    }

    override fun getItemCount(): Int = categoryList.size
}