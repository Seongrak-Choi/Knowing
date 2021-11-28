package com.example.knowing.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.knowing.R
import com.example.knowing.databinding.*

class WelfareDetailBenefitsRCAdapter(
    private val benefitsList: List<String>,
    private val mContext: Context
) :
    RecyclerView.Adapter<WelfareDetailBenefitsRCAdapter.ViewHolder>() {

    //소주제가 있는지 없는지 확인하기 위한 변수
    private var isSmallSubject = false

    inner class ViewHolder(val binding: ItemRcWelfareDetailBigSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.txBigSubject.text = data

            if (isSmallSubject){ //소주제가 있는 경우 클릭 리스너
                //혜택 정보 더보기 위한 버튼 클릭 리스너
                binding.btnMoreInfo.setOnClickListener {
                    if (binding.rcSmallSubject.visibility == View.VISIBLE) {
                        binding.rcSmallSubject.visibility = View.GONE
                        binding.btnMoreInfo.setImageResource(R.drawable.ic_arrow_open)
                        //큰 주제를 한 줄로 나타내도록 설정
                        binding.txBigSubject.maxLines = 1
                    } else if (binding.rcSmallSubject.visibility == View.GONE) {
                        binding.rcSmallSubject.visibility = View.VISIBLE
                        binding.btnMoreInfo.setImageResource(R.drawable.ic_arrow_close)
                        //큰 주제를 한 줄 이상으로 나타내도록 설정
                        binding.txBigSubject.maxLines = 3
                    }
                }
            }else{//소주제가 없는 경우 클릭 리스너
                //혜택 정보 더보기 위한 버튼 클릭 리스너
                binding.btnMoreInfo.setOnClickListener {
                    if (binding.txBigSubject.maxLines == 3) {
                        binding.rcSmallSubject.visibility = View.GONE
                        binding.btnMoreInfo.setImageResource(R.drawable.ic_arrow_open)
                        //큰 주제를 한 줄로 나타내도록 설정
                        binding.txBigSubject.maxLines = 1
                    } else if (binding.txBigSubject.maxLines == 1) {
                        binding.rcSmallSubject.visibility = View.GONE
                        binding.btnMoreInfo.setImageResource(R.drawable.ic_arrow_close)
                        //큰 주제를 한 줄 이상으로 나타내도록 설정
                        binding.txBigSubject.maxLines = 3
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemRcWelfareDetailBigSubjectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val smallSubjectMining: List<String>
        //들어온 주제 중 큰주제와 소주제로 나누기 위해 ^로 데이터 마이닝
        val bigSmallSubjectMining = benefitsList[position].split("^")
        //소주제를 하나하나 나누기 위해 %로 데이터 마이닝
        if (bigSmallSubjectMining.size != 1)
            smallSubjectMining = bigSmallSubjectMining[1].split("=")
        else
            smallSubjectMining = listOf("")

        //소주제가 비어있는 경우 버튼 안보이도록 설정
        if (smallSubjectMining[0].isEmpty()) {
            holder.binding.btnMoreInfo.visibility = View.INVISIBLE
            isSmallSubject = false
        } else {
            //소주제가 비어있지 않으면 true로 변경
            isSmallSubject = true
        }

        holder.binding.txBigSubject.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                try {
                    //ellipsis가 시작되는 위치를 구한다
                    val ellipsisStar = holder.binding.txBigSubject.layout.getEllipsisStart(0)
                    //ellipsis시작 위치가 0보다 크면 어찌 되었든 한 줄을 넘었다sms 얘기이므로 더보기 버튼을 달아준다.
                    if (0 < ellipsisStar) {
                        holder.binding.btnMoreInfo.visibility = View.VISIBLE
                    }
                } catch (e: Exception) {
                    Log.e("ERROR", "WelfareDetailBenefitsRCAdapter에서 한 줄이 넘어가냐 안넘어가냐 확인하는 부분")
                }
                //한 번만 수행하고 리스너 삭제
                holder.binding.txBigSubject.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })


        //리사이클러뷰 어댑터 셋팅
        holder.binding.rcSmallSubject.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        holder.binding.rcSmallSubject.adapter =
            WelfareDetailBenefitsSmallSubjectRCAdapter(smallSubjectMining)

        //이 리사이클러뷰에 데이터 넣기 위함.
        holder.bind(bigSmallSubjectMining[0])
    }

    override fun getItemCount(): Int = benefitsList.size
}