package com.example.knowing.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.daimajia.swipe.SimpleSwipeListener
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.BaseSwipeAdapter
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.example.knowing.R
import com.example.knowing.databinding.ItemHomeAllWelfareRcBinding
import com.example.knowing.databinding.ItemHomeCustomWelfareRcBinding
import com.example.knowing.databinding.ItemHomeMyCalendarRcBinding

class MainHomeCalendarRcAdapter(context:Context,private val welfareInfoList:ArrayList<String>) :
    RecyclerSwipeAdapter<MainHomeCalendarRcAdapter.SimpleViewHolder>() {
    private val mContext = context

    inner class SimpleViewHolder(val binding: ItemHomeMyCalendarRcBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:String){
            binding.txName.text=data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHomeCalendarRcAdapter.SimpleViewHolder {
        val binding =
            ItemHomeMyCalendarRcBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SimpleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHomeCalendarRcAdapter.SimpleViewHolder, position: Int) {
        holder.bind(welfareInfoList[position])
        holder.binding.calendarSwipe.showMode=SwipeLayout.ShowMode.LayDown
        holder.binding.btnDelete.setOnClickListener {
            Toast.makeText(mContext,"삭제 버튼 클릭",Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = welfareInfoList.size

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.calendar_swipe
    }
}