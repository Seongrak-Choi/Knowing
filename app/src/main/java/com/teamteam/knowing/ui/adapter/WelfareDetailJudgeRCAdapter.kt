package com.teamteam.knowing.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamteam.knowing.databinding.ItemRcJudgeBinding

class WelfareDetailJudgeRCAdapter(private val judgeList:List<String>):
    RecyclerView.Adapter<WelfareDetailJudgeRCAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemRcJudgeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            if (data!="없음"){
                binding.txJudge.text = data
                binding.txNo.text=(position+1).toString()
            }else{
                binding.txNo.visibility= View.GONE
                binding.txJudge.text="-"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRcJudgeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(judgeList[position])
    }

    override fun getItemCount(): Int = judgeList.size
}