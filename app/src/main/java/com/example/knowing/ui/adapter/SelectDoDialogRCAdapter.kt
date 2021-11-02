package com.example.knowing.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.knowing.databinding.ItemRcDialogDoBinding

class SelectDoDialogRCAdapter(private var doList:ArrayList<String>):RecyclerView.Adapter<SelectDoDialogRCAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectDoDialogRCAdapter.ViewHolder {
        val binding = ItemRcDialogDoBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SelectDoDialogRCAdapter.ViewHolder, position: Int) {
        holder.bind(doList[position])
    }

    override fun getItemCount(): Int =doList.size

    inner class ViewHolder(val binding : ItemRcDialogDoBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:String){
            binding.txName.text=data
        }
    }

}