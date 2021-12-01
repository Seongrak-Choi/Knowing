package com.teamteam.knowing.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamteam.knowing.databinding.ItemRcDocumentBinding

class WelfareDetailDocumentRCAdapter(private val documentList:List<String>):
    RecyclerView.Adapter<WelfareDetailDocumentRCAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemRcDocumentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            if (data!="없음"){
                binding.txDocument.text = data
            }else{
                binding.txDocument.text="-"
                binding.imgDocument.visibility= View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRcDocumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(documentList[position])
    }

    override fun getItemCount(): Int = documentList.size
}