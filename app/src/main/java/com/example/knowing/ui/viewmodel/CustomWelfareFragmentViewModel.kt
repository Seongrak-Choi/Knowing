package com.example.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.knowing.data.model.network.response.MainWelfareResponse

class CustomWelfareFragmentViewModel(application: Application):AndroidViewModel(application) {
    //API로 받아온 복지 정보
    private val _welfareInfo : MutableLiveData<MainWelfareResponse> by lazy { MutableLiveData<MainWelfareResponse>() }

    //그래프 중 막대바 위에 총 몇건인지 표시할 라이브데이터
    private val _currentTxSubject1Total : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject2Total : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject3Total : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject4Total : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject5Total : MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject6Total : MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //그래프 중 주제를 표시할 라이브데이터
    private val _currentTxSubject1: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject2 : MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject3 : MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject4 : MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject5 : MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject6 : MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }



    //그래프 중 막대바의 높이를 비율로 정하기 위해 높이에 곱해질 비율을 저장할 라이브 데이터
    private val _biasBarHeightSubject1 : MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject2 : MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject3 : MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject4 : MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject5 : MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject6 : MutableLiveData<Float> by lazy { MutableLiveData<Float>() }

    //맞춤복지 리사이클러뷰 출력할 라이브데이터
    private val _currentRcList = MutableLiveData<ArrayList<MainWelfareResponse>>()



    val welfareInfo : MutableLiveData<MainWelfareResponse>
        get() = _welfareInfo

    val currentTxSubject1Total : MutableLiveData<String>
        get() = _currentTxSubject1Total
    val currentTxSubject2Total : MutableLiveData<String>
        get() = _currentTxSubject2Total
    val currentTxSubject3Total : MutableLiveData<String>
        get() = _currentTxSubject3Total
    val currentTxSubject4Total : MutableLiveData<String>
        get() = _currentTxSubject4Total
    val currentTxSubject5Total : MutableLiveData<String>
        get() = _currentTxSubject5Total
    val currentTxSubject6Total : MutableLiveData<String>
        get() = _currentTxSubject6Total


    val currentTxSubject1 : MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject1
    val currentTxSubject2 :  MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject2
    val currentTxSubject3 :  MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject3
    val currentTxSubject4 :  MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject4
    val currentTxSubject5 :  MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject5
    val currentTxSubject6 :  MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject6




    val biasBarHeightSubject1 : MutableLiveData<Float>
        get() = _biasBarHeightSubject1
    val biasBarHeightSubject2 : MutableLiveData<Float>
        get() = _biasBarHeightSubject2
    val biasBarHeightSubject3 : MutableLiveData<Float>
        get() = _biasBarHeightSubject3
    val biasBarHeightSubject4 : MutableLiveData<Float>
        get() = _biasBarHeightSubject4
    val biasBarHeightSubject5 : MutableLiveData<Float>
        get() = _biasBarHeightSubject5
    val biasBarHeightSubject6 : MutableLiveData<Float>
        get() = _biasBarHeightSubject6


    val currentRcList : MutableLiveData<ArrayList<MainWelfareResponse>>
        get() = _currentRcList



    init {
        _currentTxSubject1Total.value="20"
        _currentTxSubject2Total.value="15"
        _currentTxSubject3Total.value="10"
        _currentTxSubject4Total.value="5"
    }

    /*
    복지 정보를 알맞게 라이브데이터에 넣도록 호출하는 메소드
     */
    fun settingAllView(){
        //그래프 관련 데이터 세팅하는 메소드
        getBiasAndTxGraphSubject()
    }


    fun getBiasAndTxGraphSubject(){
        var sortedList = ArrayList<String>()
        var totalMap = mutableMapOf<String,Int>()
        totalMap[_welfareInfo.value!!.category1] = _welfareInfo.value!!.category1Total
        totalMap[_welfareInfo.value!!.category2] = _welfareInfo.value!!.category2Total
        totalMap[_welfareInfo.value!!.category3] = _welfareInfo.value!!.category3Total
        totalMap[_welfareInfo.value!!.category4] = _welfareInfo.value!!.category4Total
        totalMap[_welfareInfo.value!!.category5] = _welfareInfo.value!!.category5Total
        totalMap[_welfareInfo.value!!.category6] = _welfareInfo.value!!.category6Total


        //totalMap을 내림 차순으로 정렬한다.
        var sortedMap = totalMap.toList().sortedWith(compareByDescending { it.second }).toMap()

        //정렬된 순서대로 리스트를 만듬
        for ((k,v) in sortedMap){
            sortedList.add(k)
        }

        //그래프 비율 정해주는 곳
        if (totalMap[sortedList[0]]!=0)
            _biasBarHeightSubject1.value=0.6f
        else
            _biasBarHeightSubject1.value=0.01f

        if (totalMap[sortedList[1]]!=0)
            _biasBarHeightSubject2.value=getBias((20/(totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[1]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject2.value=0.01f

        if (totalMap[sortedList[2]]!=0)
            _biasBarHeightSubject3.value=getBias((20/(totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[2]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject3.value=0.01f

        if (totalMap[sortedList[3]]!=0)
            _biasBarHeightSubject4.value=getBias((20/(totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[3]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject4.value=0.01f

        if (totalMap[sortedList[4]]!=0)
            _biasBarHeightSubject5.value=getBias((20/(totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[4]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject5.value=0.01f

        if (totalMap[sortedList[5]]!=0)
            _biasBarHeightSubject6.value=getBias((20/(totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[5]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject6.value=0.01f





        //정렬된 내림차순 순으로 그래프의 주제와 그래프 토탈 텍스트를 변경하기 위해 라이브 데이터 변경
        _currentTxSubject1Total.value=totalMap[sortedList[0]].toString()
        _currentTxSubject2Total.value=totalMap[sortedList[1]].toString()
        _currentTxSubject3Total.value=totalMap[sortedList[2]].toString()
        _currentTxSubject4Total.value=totalMap[sortedList[3]].toString()
        _currentTxSubject5Total.value=totalMap[sortedList[4]].toString()
        _currentTxSubject6Total.value=totalMap[sortedList[5]].toString()

        //카테고리 이름을 2줄인 경우를 예상해서 공백으로 나누너 1_1에는 앞에 1_2는 뒤 값을 넣는다.
        if (sortedList[0].split(" ").size>1){//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[0].split(" ")[0])
            tempList.add(sortedList[0].split(" ")[1])
            _currentTxSubject1.value=tempList
        }else{
            val tempList = ArrayList<String>()
            tempList.add(sortedList[0].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject1.value=tempList
        }


        if (sortedList[1].split(" ").size>1){//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[1].split(" ")[0])
            tempList.add(sortedList[1].split(" ")[1])
            _currentTxSubject2.value=tempList
        }else{
            val tempList = ArrayList<String>()
            tempList.add(sortedList[1].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject2.value=tempList
        }

        if (sortedList[2].split(" ").size>1){//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[2].split(" ")[0])
            tempList.add(sortedList[2].split(" ")[1])
            _currentTxSubject3.value=tempList
        }else{
            val tempList = ArrayList<String>()
            tempList.add(sortedList[2].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject3.value=tempList
        }

        if (sortedList[3].split(" ").size>1){//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[3].split(" ")[0])
            tempList.add(sortedList[3].split(" ")[1])
            _currentTxSubject4.value=tempList
        }else{
            val tempList = ArrayList<String>()
            tempList.add(sortedList[3].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject4.value=tempList
        }
        if (sortedList[4].split(" ").size>1){//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[4].split(" ")[0])
            tempList.add(sortedList[4].split(" ")[1])
            _currentTxSubject5.value=tempList
        }else{
            val tempList = ArrayList<String>()
            tempList.add(sortedList[4].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject5.value=tempList
        }

        if (sortedList[5].split(" ").size>1){//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[5].split(" ")[0])
            tempList.add(sortedList[5].split(" ")[1])
            _currentTxSubject6.value=tempList
        }else{
            val tempList = ArrayList<String>()
            tempList.add(sortedList[5].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject6.value=tempList
        }


        //리사이클러뷰 출력 예시 용 작업
        var dd = ArrayList<MainWelfareResponse>()
        dd.add(_welfareInfo.value!!)
        _currentRcList.value = dd
    }


    /*
    그래프의 높이 비율을 구하기 위한 메소드
    한 단계 당 0.0315f
     */
    private fun getBias(bias:Int):Float{
        when(bias){
            19-> return 0.5985f
            18-> return 0.567f
            17-> return 0.5355f
            16-> return 0.504f
            15-> return 0.4725f
            14-> return 0.441f
            13-> return 0.4095f
            12-> return 0.378f
            11-> return 0.3465f
            10-> return 0.315f
            9-> return 0.2835f
            8-> return 0.252f
            7-> return 0.2205f
            6-> return 0.189f
            5-> return 0.1575f
            4-> return 0.126f
            3-> return 0.0945f
            2-> return 0.063f
            1-> return 0.0315f
            else->return 0.01f
        }
    }
}