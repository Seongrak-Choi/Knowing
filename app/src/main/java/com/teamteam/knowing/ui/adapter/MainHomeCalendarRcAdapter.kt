package com.teamteam.knowing.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.swipe.SwipeLayout
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter
import com.teamteam.knowing.R
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.AlarmPostResponse
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.data.remote.api.AlarmInterface
import com.teamteam.knowing.databinding.ItemHomeMyCalendarRcBinding
import com.teamteam.knowing.ui.view.main.WelfareDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

            //거주지가 서울인 경우 로고를 서울로 변경
            if ("서울" in data.address){
                binding.imgAddress.setImageResource(R.drawable.logo_30_seoul)
            }

            //알림 버튼 클릭 리스너
            binding.btnNotification.setOnClickListener {
                val alarmInterface = ApplicationClass.sRetrofit.create(AlarmInterface::class.java)
                alarmInterface.postChangeAlarm(ApplicationClass.USER_UID,data.uid).enqueue(object:Callback<AlarmPostResponse>{
                    override fun onResponse(call: Call<AlarmPostResponse>, response: Response<AlarmPostResponse>) {
                        if (response.isSuccessful){
                            val result = response.body() as AlarmPostResponse
                            if (result.alarmPostResult.alarmWhether =="알림등록"){
                                binding.btnNotification.setImageResource(R.drawable.ic_my_calendar_notice)
                            }else{
                                binding.btnNotification.setImageResource(R.drawable.ic_my_calendar_notice_grey)
                            }
                        }else{
                            Log.e("ERROR","알림 설정 해제 api 실패")
                        }
                    }

                    override fun onFailure(call: Call<AlarmPostResponse>, t: Throwable) {
                        Log.e("ERROR","알림 설정 해제 api 통신 실패")
                    }

                })
            }

            val alarmInterface = ApplicationClass.sRetrofit.create(AlarmInterface::class.java)
            alarmInterface.getIsWelfareApplyToAlarm(ApplicationClass.USER_UID,data.uid).enqueue(object:Callback<AlarmPostResponse>{
                override fun onResponse(call: Call<AlarmPostResponse>, response: Response<AlarmPostResponse>) {
                    if (response.isSuccessful){
                        val result = response.body() as AlarmPostResponse
                        if (result.alarmPostResult.alarmWhether =="알림등록"){
                            binding.btnNotification.setImageResource(R.drawable.ic_my_calendar_notice)
                        }else{
                            binding.btnNotification.setImageResource(R.drawable.ic_my_calendar_notice_grey)
                        }
                    }
                }
                override fun onFailure(call: Call<AlarmPostResponse>, t: Throwable) {
                }

            })

            //복지 누르면 상세보기로 이동
            binding.btnSelectWelfare.setOnClickListener {
                val intent = Intent(mContext, WelfareDetailActivity::class.java)
                intent.putExtra("welfareInfo",data)
                mContext.startActivity(intent)
            }

            //스와이프 후 삭제 버튼 누르면 발동하는 클릭 리스너
            binding.btnDelete.setOnClickListener {
                //인터페이스를 이용해서 삭제 클릭되면 activity에서 api삭제 요청 후 리사이클러뷰 재정비
                listener?.onItemClick(data.uid)

                welfareInfoList.remove(welfareInfoList[position])
                notifyItemRemoved(position)
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