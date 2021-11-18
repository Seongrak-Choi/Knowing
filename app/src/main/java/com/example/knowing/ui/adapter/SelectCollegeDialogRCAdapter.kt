package com.example.knowing.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knowing.databinding.ItemRcDialogCollegeBinding
import com.example.knowing.databinding.ItemRcDialogDoBinding

class SelectCollegeDialogRCAdapter(private val college_name_list:ArrayList<String>):
    RecyclerView.Adapter<SelectCollegeDialogRCAdapter.ViewHolder>(){

    private var listener: SelectDoDialogRCAdapter.OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(value: String)
    }

    fun setOnItemClickListener(listener: SelectDoDialogRCAdapter.OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemRcDialogCollegeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.txName.text = data
            binding.btnSelect.setOnClickListener {
                listener?.onItemClick(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRcDialogCollegeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(college_name_list[position])
    }

    override fun getItemCount(): Int = college_name_list.size
}