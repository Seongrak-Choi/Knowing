package com.example.knowing.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowing.databinding.*

class WelfareDetailBenefitsSmallSubjectRCAdapter(private val smallSubjectList:List<String>):
    RecyclerView.Adapter<WelfareDetailBenefitsSmallSubjectRCAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemRcSmallSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.txSmallSubject.text = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRcSmallSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(smallSubjectList[position])
    }

    override fun getItemCount(): Int = smallSubjectList.size
}