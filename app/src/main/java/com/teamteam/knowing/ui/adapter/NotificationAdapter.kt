package com.teamteam.knowing.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.network.response.NotificationResult
import com.teamteam.knowing.databinding.ItemRcNotificationBinding
import com.teamteam.knowing.databinding.ItemRcWelfareDetailBigSubjectBinding

class NotificationAdapter(private val notificationList:ArrayList<NotificationResult>,private val mContext :Context):RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    inner class ViewHolder(val binding:ItemRcNotificationBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data:NotificationResult){
            binding.txTitle.text=data.title
            binding.txContent.text=data.content
            binding.txDate.text=data.date

            //뉴 이미지 넣고 안넣고 차이 주는 코드
            if (data.newStatus){
                binding.imgNew.visibility=View.VISIBLE
            }else{
                binding.imgNew.visibility=View.GONE
            }

            //더 보기 버튼 클릭 리스너
            binding.btnMoreInfo.setOnClickListener {
                if (binding.constraintContent.visibility== View.GONE){
                    binding.imgMoreInfo.setImageResource(R.drawable.ic_arrow_close)
                    binding.constraintContent.visibility=View.VISIBLE
                }else if (binding.constraintContent.visibility==View.VISIBLE){
                    binding.imgMoreInfo.setImageResource(R.drawable.ic_arrow_open)
                    binding.constraintContent.visibility=View.GONE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationAdapter.ViewHolder {
        val binding = ItemRcNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ViewHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    override fun getItemCount(): Int = notificationList.size
}