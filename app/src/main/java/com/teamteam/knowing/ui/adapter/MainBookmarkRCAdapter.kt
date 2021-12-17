package com.teamteam.knowing.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.net.ParseException
import androidx.recyclerview.widget.RecyclerView
import com.teamteam.knowing.R
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import com.teamteam.knowing.databinding.ItemRcHomeBookmarkBinding
import com.teamteam.knowing.ui.view.main.WelfareDetailActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainBookmarkRCAdapter(private val welfareList:ArrayList<WelfareInfo>, private val mContext : Context):
    RecyclerView.Adapter<MainBookmarkRCAdapter.ViewHolder>(), Filterable {//필터기능 위해 Filterable 상속

    private var listener: OnItemClickListener? = null

    //서버에서 받아온 복지 리스트를 files에 저장
    private var files: ArrayList<WelfareInfo> = welfareList


    interface OnItemClickListener {
        fun onItemClick(value: String)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(val binding: ItemRcHomeBookmarkBinding) :
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
                val intent = Intent(mContext,WelfareDetailActivity::class.java)
                intent.putExtra("welfareInfo",data)
                mContext.startActivity(intent)
            }

            //스와이프 후 삭제 버튼 누르면 발동하는 클릭 리스너
            binding.btnDelete.setOnClickListener {
                //인터페이스를 이용해서 삭제 클릭되면 activity에서 api삭제 요청 후 리사이클러뷰 재정비
                listener?.onItemClick(data.uid)

                //해당 복지 리스트에서 삭제
                files.remove(files[adapterPosition])
                //검색할 때 총 몇건인지 실시간으로 출력하기 위함
                (mContext as AppCompatActivity).findViewById<TextView>(R.id.tx_welfare_total).text="총 ${files.size}건"

                //스와이프 삭제 했을 때 리스트가 0이면 일러 나오도록 설정
                if (files.isEmpty())
                    (mContext as AppCompatActivity).findViewById<ConstraintLayout>(R.id.constraint_no_data).visibility=View.VISIBLE
                else
                    (mContext as AppCompatActivity).findViewById<ConstraintLayout>(R.id.constraint_no_data).visibility=View.INVISIBLE
                notifyItemRemoved(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRcHomeBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = files[position] //files에는 필터링된 복지 정보 리스트들이 담겨 있음
        holder.bind(current)  //holder부분에 넘겨받은 welfareList[position]을 넘겨주는게 아님

    }

    override fun getItemCount(): Int = files.size //필터기능 때문에 files길이 반환


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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                files = if (charString.isEmpty()) { welfareList
                } else {
                    val filteredList = ArrayList<WelfareInfo>()
                    if (welfareList != null) {
                        for (welfare in welfareList) {
                            if (welfare.name.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList.add(welfare)
                            }
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = files
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                files = results?.values as ArrayList<WelfareInfo>

                //검색할 때 총 몇건인지 실시간으로 출력하기 위함
                (mContext as AppCompatActivity).findViewById<TextView>(R.id.tx_welfare_total).text="총 ${files.size}건"
                notifyDataSetChanged()
            }
        }
    }
}