package com.teamteam.knowing.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_UID
import com.teamteam.knowing.data.model.network.response.AlarmList
import com.teamteam.knowing.data.model.network.response.AlarmListResponse
import com.teamteam.knowing.data.model.network.response.AlarmListResult
import com.teamteam.knowing.data.model.network.response.OneOfWelfareInfoResponse
import com.teamteam.knowing.data.remote.api.AlarmInterface
import com.teamteam.knowing.data.remote.api.WelfareInterface
import com.teamteam.knowing.databinding.ItemRcHomeAlarmBinding
import com.teamteam.knowing.ui.view.main.WelfareDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainAlarmRCAdapter(private val alarmList:ArrayList<AlarmList>,private val mContext:Context):
    RecyclerView.Adapter<MainAlarmRCAdapter.ViewHolder>() {

    private var listener: MainAlarmRCAdapter.OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(value: ArrayList<AlarmList>)
    }

    fun setOnItemClickListener(listener: MainAlarmRCAdapter.OnItemClickListener) {
        this.listener = listener
    }

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
            binding.txDate.text=formatTimeString(data.date)

            //아이템 클릭 리스너
            binding.btnSelectWelfare.setOnClickListener {
                //복지 검색 후 성공하면 상세페이지로 넘어가는 메소드
                val welfareInterface = ApplicationClass.sRetrofit.create(WelfareInterface::class.java)
                welfareInterface.getOneOfWelfareInfo(data.postUid).enqueue(object:Callback<OneOfWelfareInfoResponse>{
                    override fun onResponse(call: Call<OneOfWelfareInfoResponse>, response: Response<OneOfWelfareInfoResponse>) {
                        if (response.isSuccessful){
                            val result = response.body() as OneOfWelfareInfoResponse
                            val intent = Intent(mContext, WelfareDetailActivity::class.java)
                            intent.putExtra("welfareInfo",result.oneOfWelfareInfoResult)
                            mContext.startActivity(intent)
                        }else{
                            Log.e("ERROR","복지 하나 검색 api 호출 실패")
                        }
                    }
                    override fun onFailure(call: Call<OneOfWelfareInfoResponse>, t: Throwable) {
                        Log.e("ERROR","복지 하나 검색 api 호출 통신 실패")
                    }
                })
            }

            //삭제 버튼 클릭 리스너
            binding.btnDelete.setOnClickListener {
                val alarmInterface = ApplicationClass.sRetrofit.create(AlarmInterface::class.java)
                alarmInterface.deleteAlarm(USER_UID,data.uid).enqueue(object:Callback<AlarmListResponse>{
                    override fun onResponse(call: Call<AlarmListResponse>, response: Response<AlarmListResponse>) {
                        if (response.isSuccessful){
                            val result = response.body() as AlarmListResponse
                            listener?.onItemClick(result.alarmListResult.alarmList)
                        }else{
                            Log.e("ERROR","삭제 버튼 클릭해서 알림 하나 삭제하는 api 실패")
                        }
                    }
                    override fun onFailure(call: Call<AlarmListResponse>, t: Throwable) {
                        Log.e("ERROR","삭제 버튼 클릭해서 알림 하나 삭제하는 api 통신 실패")
                    }
                })
            }
        }
    }



    /*
    몇분전, 몇시간전 등등 계산해서 메세지 돌려주는 메소드
     */
    fun formatTimeString(stringDateTime:String):String{
        val format = SimpleDateFormat("yyyyMMddHHmmss")

        val calendar = Calendar.getInstance()
        val date = format.parse(stringDateTime)
        val regTime = date.time
        val curTime=calendar.timeInMillis

        var diffTime : Long = (curTime - regTime) / 1000
        var msg = ""

        if (diffTime<60){
            msg = "방금 전"
        }else if ((diffTime / 60)< 60){
            msg = "${diffTime/60}분 전"

        }else if ((diffTime/60/60)<24){
            msg = "${diffTime/60/60}시간 전"

        }else if ((diffTime/60/60/24)<30){
            msg = "${diffTime/60/60/24+1}일 전"

        }else if ((diffTime/60/60/24/30)<12){
            msg = "${diffTime/60/60/24/30}달 전"
        }else{
            msg = "${diffTime/60/60/24/30/12}년 전"
        }
        return msg
    }

}