package com.example.knowing.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.ParseException
import androidx.recyclerview.widget.RecyclerView
import com.example.knowing.data.model.network.response.MainWelfareResponse
import com.example.knowing.databinding.ItemHomeCustomWelfareRcBinding
import com.example.knowing.databinding.ItemRcDialogCollegeBinding
import com.example.knowing.databinding.ItemRcDialogDoBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainHomeCustomWelfareRCAdapter(private val welfareList:ArrayList<MainWelfareResponse>):
    RecyclerView.Adapter<MainHomeCustomWelfareRCAdapter.ViewHolder>(){

    private var listener: MainHomeCustomWelfareRCAdapter.OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(value: String)
    }

    fun setOnItemClickListener(listener: MainHomeCustomWelfareRCAdapter.OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemHomeCustomWelfareRcBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MainWelfareResponse) {
            binding.txName.text=data.name
            binding.txAddress.text=data.address
            var maxMoney = data.maxMoney.substring(0,data.maxMoney.length-4)
            binding.txMaxCost.text="최대 ${maxMoney}만원 지원"
            var minMoney = data.maxMoney.substring(0,data.minMoney.length-4)
            binding.txMinCost.text="최소 ${minMoney}만원"

            //신청마감일을 얻기 위해 가공
            val deadline=data.applyDate.split("~")
            val transDeadline="20${deadline[1]}"
            binding.txDeadline.text="${transDeadline}까지"

            //d_day계산
            val deadLineYearMonthDay = transDeadline.split(".") //년,월,일을 쪼갬
            binding.txDDay.text=getDDay(deadLineYearMonthDay[0].toInt(),deadLineYearMonthDay[1].toInt(),deadLineYearMonthDay[2].toInt())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeCustomWelfareRcBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(welfareList[position])
    }

    override fun getItemCount(): Int = welfareList.size


    /*
    dday 구하는 메소드
     */
    private fun getDDay(dayYear:Int,dayMonth:Int,dayDay:Int):String{
        Locale.setDefault(Locale.KOREAN);

        var welfareMonth = dayMonth-1
        var welfareYear = dayYear
        var welfareDay = dayDay

        try {
            val format = SimpleDateFormat("yyyy-mm-dd")
            //지금 시간 가져오기
            val currentDate = Calendar.getInstance()
            val cYear = currentDate.get(Calendar.YEAR)
            val cMonth = currentDate.get(Calendar.MONTH)
            val cDay = currentDate.get(Calendar.DAY_OF_MONTH)
            currentDate.set(cYear,cMonth,cDay) //오늘 날짜에 세팅

            val welfareDate = Calendar.getInstance()
            welfareDate.set(welfareYear,welfareMonth,welfareDay)//복지 날짜 형식에 맞게 세팅

            val currentDay = currentDate.timeInMillis/86400000 //계산을 위해 밀리세컨즈로 변경 후 나누기
            val dday = welfareDate.timeInMillis/86400000 //계산을 위해 밀리세컨즈로 변경 후 나누기
            val count = dday - currentDay //오늘 날짜에서 dday날짜를 빼준다.

            if (count.toInt()>0)
                return "D-${count.toInt()}"
            else if (count.toInt()<0)
                return "D+${count.toInt()*-1}"
            else
                return "D-Day"
        }catch (e: ParseException){
            Log.e("ERROR","날짜 변화 중 형식이 맞지 않아 오류 발생")
            return "날짜 형식이 올바르지 않습니다"
        }
    }
}