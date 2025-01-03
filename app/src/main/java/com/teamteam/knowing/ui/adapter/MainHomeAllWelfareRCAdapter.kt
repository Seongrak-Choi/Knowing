package com.teamteam.knowing.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.ParseException
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.databinding.ItemHomeAllWelfareRcBinding
import com.teamteam.knowing.ui.view.main.WelfareDetailActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainHomeAllWelfareRCAdapter(private val welfareList:ArrayList<WelfareInfo>,private val mContext:Context):
    RecyclerView.Adapter<MainHomeAllWelfareRCAdapter.ViewHolder>(){

    private var listener: MainHomeAllWelfareRCAdapter.OnItemClickListener? = null

    //부모 뷰페이저2
    lateinit var parentViewPager2 : ViewPager2

    interface OnItemClickListener {
        fun onItemClick(value: String)
    }

    fun setOnItemClickListener(listener: MainHomeAllWelfareRCAdapter.OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemHomeAllWelfareRcBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: WelfareInfo) {
            binding.txName.text=data.name
            binding.txAddress.text=data.address

            //거주지가 서울인 경우 로고를 서울로 변경
            if ("서울" in data.address){
                binding.imgAddress.setImageResource(R.drawable.logo_20_seoul)
            }


            if (data.maxMoney!="0"){//0이 아닌 경우만 수정하기 위함
                var maxMoney = data.maxMoney.substring(0,data.maxMoney.length-4)
                binding.txMaxCost.text="최대 ${maxMoney}만원 지원"
            }else{//최대 금액이 없는 경우는 서비스형태를 출력해주기 위함.
                binding.txMaxCost.text=data.serviceType
            }

            if (data.minMoney!="0" && data.minMoney !="조건별상이"){//0이 아닌 경우만을 위함
                var minMoney = data.minMoney.substring(0,data.minMoney.length-4)
                binding.txMinCost.text="최소 ${minMoney}만원"
            }else if(data.minMoney=="조건별상이"){//최소금액이 없는 경우
                binding.txMinCost.text="조건별상이"
            }else{
                binding.txMinCost.text=""
            }


            //신청마감일을 얻기 위해 가공
            if (data.applyDate!="연중상시" && data.applyDate!="별도공지" && data.applyDate.isNotEmpty()){ //데이트가 아닌 연중상시로 들어오는 경우를 처리하기 위한 조건문
                val deadline=data.applyDate.split("~")
                val transDeadline="${deadline[1]}"
                binding.txDeadline.text="${transDeadline}까지"
                //d_day계산
                val deadLineYearMonthDay = transDeadline.split(".") //년,월,일을 쪼갬
                binding.txDDay.text=getDDay(deadLineYearMonthDay[0].toInt(),deadLineYearMonthDay[1].toInt(),deadLineYearMonthDay[2].toInt())
            }else if (data.applyDate=="연중상시"){
                binding.txDDay.text=""
                binding.txDeadline.text="연중 상시"
                binding.line1.visibility= View.INVISIBLE
            }else{
                binding.txDDay.text=""
                binding.txDeadline.text="별도 공지"
                binding.line1.visibility= View.INVISIBLE
            }

            //복지 누르면 상세보기로 이동
            binding.btnSelectWelfare.setOnClickListener {
                val intent = Intent(mContext, WelfareDetailActivity::class.java)
                intent.putExtra("welfareInfo",data)
                mContext.startActivity(intent)
            }


            //리사이클러뷰 아이템 터치 리스너를 설정해서 아이템이 터치될 때는 뷰페이저 스크롤링이 먹도록 설정
            binding.btnSelectWelfare.setOnTouchListener { v, event ->
                when(event.action){
                    MotionEvent.ACTION_DOWN->{
                        parentViewPager2.isUserInputEnabled=true
                    }
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeAllWelfareRcBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        //부모 뷰페이저 선언
        parentViewPager2=(mContext as AppCompatActivity).findViewById(R.id.view_pager2)

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