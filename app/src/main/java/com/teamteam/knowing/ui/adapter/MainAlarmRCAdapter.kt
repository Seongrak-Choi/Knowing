package com.teamteam.knowing.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamteam.knowing.data.model.network.response.AlarmList
import com.teamteam.knowing.databinding.ItemRcHomeAlarmBinding

class MainAlarmRCAdapter(private val alarmList:ArrayList<AlarmList>,private val mContext:Context):
    RecyclerView.Adapter<MainAlarmRCAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAlarmRCAdapter.ViewHolder {
        val binding = ItemRcHomeAlarmBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAlarmRCAdapter.ViewHolder, position: Int) {
        holder.bind(alarmList[position])
    }

    override fun getItemCount(): Int = alarmList.size

    inner class ViewHolder(val binding:ItemRcHomeAlarmBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(data:AlarmList){
            binding.txTitle.text=data.title
            binding.txSubtitle.text=data.subTitle
            binding.txDate.text=data.date
        }
    }

}