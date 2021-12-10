package com.teamteam.knowing.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.databinding.ItemHomeMyCalendarRcBinding
import com.teamteam.knowing.ui.view.main.WelfareDetailActivity

class MainHomeCalendarRcAdapter(context:Context,private val welfareInfoList:ArrayList<WelfareInfo>) :
    RecyclerSwipeAdapter<MainHomeCalendarRcAdapter.SimpleViewHolder>() {
    private val mContext = context

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(value: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class SimpleViewHolder(val binding: ItemHomeMyCalendarRcBinding ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data:WelfareInfo){

            binding.txName.text=data.name

            binding.txAddress.text=data.address

            //복지 누르면 상세보기로 이동
            binding.btnSelectWelfare.setOnClickListener {
                println("캘린더 어댑터 왜 클릭이 안됌")
                val intent = Intent(mContext, WelfareDetailActivity::class.java)
                intent.putExtra("welfareInfo",data)
                mContext.startActivity(intent)
            }

            //스와이프 후 삭제 버튼 누르면 발동하는 클릭 리스너
            binding.btnDelete.setOnClickListener {
                //인터페이스를 이용해서 삭제 클릭되면 activity에서 api삭제 요청 후 리사이클러뷰 재정비
                listener?.onItemClick(data.uid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHomeCalendarRcAdapter.SimpleViewHolder {
        val binding =
            ItemHomeMyCalendarRcBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SimpleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHomeCalendarRcAdapter.SimpleViewHolder, position: Int) {
        holder.bind(welfareInfoList[position])
        holder.binding.calendarSwipe.showMode=SwipeLayout.ShowMode.PullOut
    }

    override fun getItemCount(): Int = welfareInfoList.size

    override fun getSwipeLayoutResourceId(position: Int): Int {
        return R.id.calendar_swipe
    }
}