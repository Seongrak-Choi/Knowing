package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.data.model.network.response.MainWelfareResponse
import com.teamteam.knowing.data.model.network.response.WelfareInfo
import java.text.DecimalFormat

class CustomWelfareFragmentViewModel(application: Application) : AndroidViewModel(application) {
    //API로 받아온 복지 정보
    private val _welfareInfo: MutableLiveData<MainWelfareResponse> by lazy { MutableLiveData<MainWelfareResponse>() }

    private val mContext = application.applicationContext

    //그래프 중 막대바 위에 총 몇건인지 표시할 라이브데이터
    private val _currentTxSubject1Total: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject2Total: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject3Total: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject4Total: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject5Total: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentTxSubject6Total: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //그래프 중 주제를 표시할 라이브데이터
    private val _currentTxSubject1: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject2: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject3: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject4: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject5: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }
    private val _currentTxSubject6: MutableLiveData<ArrayList<String>> by lazy { MutableLiveData<ArrayList<String>>() }


    //그래프 중 막대바의 높이를 비율로 정하기 위해 높이에 곱해질 비율을 저장할 라이브 데이터
    private val _biasBarHeightSubject1: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject2: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject3: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject4: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject5: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }
    private val _biasBarHeightSubject6: MutableLiveData<Float> by lazy { MutableLiveData<Float>() }


    //상단에 최대금, 최소금액 표시하기 위한 라이브데이터 모음
    private val _currentMaxMoney: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    private val _currentMinMoney: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //수혜 예상 복지 건수 표시하기 위한 라이브데이터
    private val _currentTotalWelfare: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    //최대 금액에 해당하는 복지 데이터 저장할 라이브데이터
    private val _maxMoneyWelfareInfo: MutableLiveData<WelfareInfo> by lazy { MutableLiveData<WelfareInfo>() }

    //맞춤복지 리사이클러뷰 출력할 라이브데이터
    private val _currentRcList = MutableLiveData<ArrayList<WelfareInfo>>()


    val welfareInfo: MutableLiveData<MainWelfareResponse>
        get() = _welfareInfo

    val currentTxSubject1Total: MutableLiveData<String>
        get() = _currentTxSubject1Total
    val currentTxSubject2Total: MutableLiveData<String>
        get() = _currentTxSubject2Total
    val currentTxSubject3Total: MutableLiveData<String>
        get() = _currentTxSubject3Total
    val currentTxSubject4Total: MutableLiveData<String>
        get() = _currentTxSubject4Total
    val currentTxSubject5Total: MutableLiveData<String>
        get() = _currentTxSubject5Total
    val currentTxSubject6Total: MutableLiveData<String>
        get() = _currentTxSubject6Total


    val currentTxSubject1: MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject1
    val currentTxSubject2: MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject2
    val currentTxSubject3: MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject3
    val currentTxSubject4: MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject4
    val currentTxSubject5: MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject5
    val currentTxSubject6: MutableLiveData<ArrayList<String>>
        get() = _currentTxSubject6


    val biasBarHeightSubject1: MutableLiveData<Float>
        get() = _biasBarHeightSubject1
    val biasBarHeightSubject2: MutableLiveData<Float>
        get() = _biasBarHeightSubject2
    val biasBarHeightSubject3: MutableLiveData<Float>
        get() = _biasBarHeightSubject3
    val biasBarHeightSubject4: MutableLiveData<Float>
        get() = _biasBarHeightSubject4
    val biasBarHeightSubject5: MutableLiveData<Float>
        get() = _biasBarHeightSubject5
    val biasBarHeightSubject6: MutableLiveData<Float>
        get() = _biasBarHeightSubject6


    val currentMaxMoney: MutableLiveData<String>
        get() = _currentMaxMoney
    val currentMinMoney: MutableLiveData<String>
        get() = _currentMinMoney

    val currentTotalWelfare: MutableLiveData<String>
        get() = _currentTotalWelfare

    val maxMoneyWelfareInfo: MutableLiveData<WelfareInfo>
        get() = _maxMoneyWelfareInfo


    val currentRcList: MutableLiveData<ArrayList<WelfareInfo>>
        get() = _currentRcList


    init {
    }

    /*
    복지 정보를 알맞게 라이브데이터에 넣도록 호출하는 메소드
     */
    fun settingAllView() {

        //최대 수혜 금액, 최소 수혜 금액, 수혜 예상 복지 건수 구해서 라이브 데이터에 저장하는 메소드
        getMaxMinMoney()

        //그래프 관련 데이터 세팅하는 메소드
        getBiasAndTxGraphSubject()

        changeRcToStudent()//학생 지원이 맨 처음 보이도록 세팅
    }


    fun getBiasAndTxGraphSubject() {
        var sortedList = ArrayList<String>()
        var totalMap = mutableMapOf<String, Int>()
        totalMap["학생"] = _welfareInfo.value!!.mainWelfareResult.studentCategory.size
        totalMap["취업"] = _welfareInfo.value!!.mainWelfareResult.employCategory.size
        totalMap["창업"] = _welfareInfo.value!!.mainWelfareResult.foundationCategory.size
        totalMap["주거 금융"] = _welfareInfo.value!!.mainWelfareResult.residentCategory.size
        totalMap["생활 복지"] = _welfareInfo.value!!.mainWelfareResult.lifeCategory.size
        totalMap["코로나 19"] = _welfareInfo.value!!.mainWelfareResult.covidCategory.size

        //totalMap을 내림 차순으로 정렬한다.
        var sortedMap = totalMap.toList().sortedWith(compareByDescending { it.second }).toMap()

        //정렬된 순서대로 리스트를 만듬
        for ((k, v) in sortedMap) {
            sortedList.add(k)
        }

        //그래프 비율 정해주는 곳
        if (totalMap[sortedList[0]] != 0)
            _biasBarHeightSubject1.value = 0.567f
        else
            _biasBarHeightSubject1.value = 0.01f

        if (totalMap[sortedList[1]] != 0)
            _biasBarHeightSubject2.value =
                getBias((20 / (totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[1]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject2.value = 0.01f

        if (totalMap[sortedList[2]] != 0)
            _biasBarHeightSubject3.value =
                getBias((20 / (totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[2]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject3.value = 0.01f

        if (totalMap[sortedList[3]] != 0)
            _biasBarHeightSubject4.value =
                getBias((20 / (totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[3]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject4.value = 0.01f

        if (totalMap[sortedList[4]] != 0)
            _biasBarHeightSubject5.value =
                getBias((20 / (totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[4]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject5.value = 0.01f

        if (totalMap[sortedList[5]] != 0)
            _biasBarHeightSubject6.value =
                getBias((20 / (totalMap[sortedList[0]]!!.toFloat() / totalMap[sortedList[5]]!!.toFloat())).toInt())
        else
            _biasBarHeightSubject6.value = 0.01f


        //정렬된 내림차순 순으로 그래프의 주제와 그래프 토탈 텍스트를 변경하기 위해 라이브 데이터 변경
        _currentTxSubject1Total.value = totalMap[sortedList[0]].toString()
        _currentTxSubject2Total.value = totalMap[sortedList[1]].toString()
        _currentTxSubject3Total.value = totalMap[sortedList[2]].toString()
        _currentTxSubject4Total.value = totalMap[sortedList[3]].toString()
        _currentTxSubject5Total.value = totalMap[sortedList[4]].toString()
        _currentTxSubject6Total.value = totalMap[sortedList[5]].toString()

        //카테고리 이름을 2줄인 경우를 예상해서 공백으로 나누너 1_1에는 앞에 1_2는 뒤 값을 넣는다.
        if (sortedList[0].split(" ").size > 1) {//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[0].split(" ")[0])
            tempList.add(sortedList[0].split(" ")[1])
            _currentTxSubject1.value = tempList
        } else {
            val tempList = ArrayList<String>()
            tempList.add(sortedList[0].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject1.value = tempList
        }


        if (sortedList[1].split(" ").size > 1) {//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[1].split(" ")[0])
            tempList.add(sortedList[1].split(" ")[1])
            _currentTxSubject2.value = tempList
        } else {
            val tempList = ArrayList<String>()
            tempList.add(sortedList[1].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject2.value = tempList
        }

        if (sortedList[2].split(" ").size > 1) {//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[2].split(" ")[0])
            tempList.add(sortedList[2].split(" ")[1])
            _currentTxSubject3.value = tempList
        } else {
            val tempList = ArrayList<String>()
            tempList.add(sortedList[2].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject3.value = tempList
        }

        if (sortedList[3].split(" ").size > 1) {//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[3].split(" ")[0])
            tempList.add(sortedList[3].split(" ")[1])
            _currentTxSubject4.value = tempList
        } else {
            val tempList = ArrayList<String>()
            tempList.add(sortedList[3].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject4.value = tempList
        }
        if (sortedList[4].split(" ").size > 1) {//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[4].split(" ")[0])
            tempList.add(sortedList[4].split(" ")[1])
            _currentTxSubject5.value = tempList
        } else {
            val tempList = ArrayList<String>()
            tempList.add(sortedList[4].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject5.value = tempList
        }

        if (sortedList[5].split(" ").size > 1) {//나눴을 때 사이즈가 1 이상이면 2줄 짜리 카테고리
            val tempList = ArrayList<String>()
            tempList.add(sortedList[5].split(" ")[0])
            tempList.add(sortedList[5].split(" ")[1])
            _currentTxSubject6.value = tempList
        } else {
            val tempList = ArrayList<String>()
            tempList.add(sortedList[5].split(" ")[0])
            tempList.add(" ")
            _currentTxSubject6.value = tempList
        }
    }


    /*
    학생 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
     */
    fun changeRcToStudent() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.studentCategory
    }

    /*
  취업 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToEmploy() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.employCategory
    }

    /*
  창업 지원으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToFoundation() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.foundationCategory
    }

    /*
    주거 금융으로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToResident() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.residentCategory
    }

    /*
  생활 복지로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToLife() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.lifeCategory
    }

    /*
  코로나 19로 리사이클러뷰 변환시키기위해 라이브데이터 변경하는 메소드
   */
    fun changeRcToCovid() {
        _currentRcList.value = _welfareInfo.value?.mainWelfareResult?.covidCategory
    }


    /*
    최대 수혜 금액, 최소 수혜 금액, 수혜 예상 복지 건수 구해서 라이브 데이터에 저장하는 메소드
     */
    fun getMaxMinMoney() {

        //수혜 예상 복지 건수를 계산해서 라이브데이터에 저장
        _currentTotalWelfare.value = "${
            _welfareInfo.value?.mainWelfareResult?.studentCategory?.size!! + _welfareInfo.value?.mainWelfareResult?.employCategory?.size!! +
                    _welfareInfo.value?.mainWelfareResult?.foundationCategory?.size!! + _welfareInfo.value?.mainWelfareResult?.residentCategory?.size!! +
                    _welfareInfo.value?.mainWelfareResult?.lifeCategory?.size!! + _welfareInfo.value?.mainWelfareResult?.covidCategory?.size!!
        }"


        //최대 수혜 금액 계산해서 라이브데이터에 저장
        var maxMoneyOfCategory = ArrayList<Int>()//카테고리별 가장 높은 금액을 저장할 리스트
        var minMoneyOfCategory = ArrayList<Int>()//카테고리별 가장 낮은 금액을 저장할 리스트


        //학생 카테고리 중 제일 높은 금액 산출
        var studentCategoryMaxMoney = ArrayList<Int>()
        var studentCategoryMinMoney = ArrayList<Int>()

        for (i in _welfareInfo.value!!.mainWelfareResult.studentCategory) {
            studentCategoryMaxMoney.add(i.maxMoney.toInt())//학생 카테고리의 복지 중 최대금액만 저장하는 리스트에 저장
            if (i.minMoney != "0") //최소 금액이 0이 아닌 최소금액들만 저장
                studentCategoryMinMoney.add(i.minMoney.toInt())
        }
        if (studentCategoryMaxMoney.isNotEmpty())
            maxMoneyOfCategory.add(studentCategoryMaxMoney.maxOrNull()!!)//카테고리에서 산출된 최고 금액을 리스트에 저장
        if (studentCategoryMinMoney.isNotEmpty())
            minMoneyOfCategory.add(studentCategoryMinMoney.minOrNull()!!)//카테고리에서 산출된 최소 금액을 리스트에 저장


        //취업 카테고리 중 제일 높은 금액 산출
        var employCategoryMaxMoney = ArrayList<Int>()
        var employCategoryMinMoney = ArrayList<Int>()
        for (i in _welfareInfo.value!!.mainWelfareResult.employCategory) {
            employCategoryMaxMoney.add(i.maxMoney.toInt())
            if (i.minMoney != "0") //최소 금액이 0이 아닌 최소금액들만 저장
                employCategoryMinMoney.add(i.minMoney.toInt())
        }
        if (employCategoryMaxMoney.isNotEmpty())
            maxMoneyOfCategory.add(employCategoryMaxMoney.maxOrNull()!!)//카테고리에서 산출된 최고 금액을 리스트에 저장
        if (employCategoryMinMoney.isNotEmpty())//최소금액이 다 0이여서 아무런 데이터가 저장되어 있지 않는다면...
            minMoneyOfCategory.add(employCategoryMinMoney.minOrNull()!!)//카테고리에서 산출된 최소 금액을 리스트에 저장


        //창업 카테고리 중 제일 높은 금액 산출
        var foundationCategoryMaxMoney = ArrayList<Int>()
        var foundationCategoryMinMoney = ArrayList<Int>()
        for (i in _welfareInfo.value!!.mainWelfareResult.foundationCategory) {
            foundationCategoryMaxMoney.add(i.maxMoney.toInt())
            if (i.minMoney != "0") //최소 금액이 0이 아닌 최소금액들만 저장
                foundationCategoryMinMoney.add(i.minMoney.toInt())
        }
        if (foundationCategoryMaxMoney.isNotEmpty())
            maxMoneyOfCategory.add(foundationCategoryMaxMoney.maxOrNull()!!)//카테고리에서 산출된 최고 금액을 리스트에 저장
        if (foundationCategoryMinMoney.isNotEmpty())//최소금액이 다 0이여서 아무런 데이터가 저장되어 있지 않는다면...
            minMoneyOfCategory.add(foundationCategoryMinMoney.minOrNull()!!)//카테고리에서 산출된 최소 금액을 리스트에 저장


        //주거 금융 카테고리 중 제일 높은 금액 산출
        var residentCategoryMaxMoney = ArrayList<Int>()
        var residentCategoryMinMoney = ArrayList<Int>()
        for (i in _welfareInfo.value!!.mainWelfareResult.residentCategory) {
            residentCategoryMaxMoney.add(i.maxMoney.toInt())
            if (i.minMoney != "0") //최소 금액이 0이 아닌 최소금액들만 저장
                residentCategoryMinMoney.add(i.minMoney.toInt())
        }
        if (residentCategoryMaxMoney.isNotEmpty())
            maxMoneyOfCategory.add(residentCategoryMaxMoney.maxOrNull()!!)//카테고리에서 산출된 최고 금액을 리스트에 저장
        if (residentCategoryMinMoney.isNotEmpty())//최소금액이 다 0이여서 아무런 데이터가 저장되어 있지 않는다면...
            minMoneyOfCategory.add(residentCategoryMinMoney.minOrNull()!!)//카테고리에서 산출된 최소 금액을 리스트에 저장


        //생활 복지 카테고리 중 제일 높은 금액 산출
        var lifeCategoryMaxMoney = ArrayList<Int>()
        var lifeCategoryMinMoney = ArrayList<Int>()
        for (i in _welfareInfo.value!!.mainWelfareResult.lifeCategory) {
            lifeCategoryMaxMoney.add(i.maxMoney.toInt())
            if (i.minMoney != "0") //최소 금액이 0이 아닌 최소금액들만 저장
                lifeCategoryMinMoney.add(i.minMoney.toInt())
        }
        if (lifeCategoryMaxMoney.isNotEmpty())
            maxMoneyOfCategory.add(lifeCategoryMaxMoney.maxOrNull()!!)//카테고리에서 산출된 최고 금액을 리스트에 저장
        if (lifeCategoryMinMoney.isNotEmpty())//최소금액이 다 0이여서 아무런 데이터가 저장되어 있지 않는다면...
            minMoneyOfCategory.add(lifeCategoryMinMoney.minOrNull()!!)//카테고리에서 산출된 최소 금액을 리스트에 저장


        //코로나 카테고리 중 제일 높은 금액 산출
        var covidCategoryMaxMoney = ArrayList<Int>()
        var covidCategoryMinMoney = ArrayList<Int>()
        for (i in _welfareInfo.value!!.mainWelfareResult.covidCategory) {
            covidCategoryMaxMoney.add(i.maxMoney.toInt())
            if (i.minMoney != "0") //최소 금액이 0이 아닌 최소금액들만 저장
                covidCategoryMinMoney.add(i.minMoney.toInt())
        }
        if (covidCategoryMaxMoney.isNotEmpty())
            maxMoneyOfCategory.add(covidCategoryMaxMoney.maxOrNull()!!)//카테고리에서 산출된 최고 금액을 리스트에 저장
        if (covidCategoryMinMoney.isNotEmpty())//최소금액이 다 0이여서 아무런 데이터가 저장되어 있지 않는다면...
            minMoneyOfCategory.add(covidCategoryMinMoney.minOrNull()!!)//카테고리에서 산출된 최소 금액을 리스트에 저장


        val myFormatter = DecimalFormat("###,###")


        var formattedMaxMoney: String //화면에 출력할 최고금액을 형식에 맞게 저장할 변수수
       if (maxMoneyOfCategory.isNotEmpty()) {
            formattedMaxMoney = myFormatter.format(maxMoneyOfCategory.maxOrNull())
        }else{
            formattedMaxMoney = "0"
        }

        var formattedMinMoney: String //화면에 출력할 최저금액을 형식에 맞게 저장할 변수수
        if (minMoneyOfCategory.isNotEmpty()) {
            formattedMinMoney = myFormatter.format(minMoneyOfCategory.minOrNull())
        }else{
            formattedMinMoney = "0"
        }

        //산출된 카테고리 별 제일 높은 금액 중 가장 높은 금액을 라이브데이터에 저장
        _currentMaxMoney.value = formattedMaxMoney

        //산출된 카테고리 별 제일 낮은 금액 중 가장 낮은 금액을 라이브데이터에 저장
        if (formattedMinMoney != "0")
            _currentMinMoney.value = "최소 ${formattedMinMoney}원"
        else
            _currentMinMoney.value = " "

        //학생 카테고리 중 산출된 가장 높은 금액의 복지가 무엇인지 찾아서 _maxMoneyWelfareInfo에 저장하기 위한 작업
        for (i in _welfareInfo.value!!.mainWelfareResult.studentCategory) {
            if (maxMoneyOfCategory.maxOrNull() == i.maxMoney.toInt()) {
                _maxMoneyWelfareInfo.value = i
            }
        }

        //취업 카테고리 중 산출된 가장 높은 금액의 복지가 무엇인지 찾아서 _maxMoneyWelfareInfo에 저장하기 위한 작업
        for (i in _welfareInfo.value!!.mainWelfareResult.employCategory) {
            if (maxMoneyOfCategory.maxOrNull() == i.maxMoney.toInt()) {
                _maxMoneyWelfareInfo.value = i
            }
        }

        //창업 카테고리 중 산출된 가장 높은 금액의 복지가 무엇인지 찾아서 _maxMoneyWelfareInfo에 저장하기 위한 작업
        for (i in _welfareInfo.value!!.mainWelfareResult.foundationCategory) {
            if (maxMoneyOfCategory.maxOrNull() == i.maxMoney.toInt()) {
                _maxMoneyWelfareInfo.value = i
            }
        }

        //주거 금융 카테고리 중 산출된 가장 높은 금액의 복지가 무엇인지 찾아서 _maxMoneyWelfareInfo에 저장하기 위한 작업
        for (i in _welfareInfo.value!!.mainWelfareResult.residentCategory) {
            if (maxMoneyOfCategory.maxOrNull() == i.maxMoney.toInt()) {
                _maxMoneyWelfareInfo.value = i
            }
        }

        //생활 복지 카테고리 중 산출된 가장 높은 금액의 복지가 무엇인지 찾아서 _maxMoneyWelfareInfo에 저장하기 위한 작업
        for (i in _welfareInfo.value!!.mainWelfareResult.lifeCategory) {
            if (maxMoneyOfCategory.maxOrNull() == i.maxMoney.toInt()) {
                _maxMoneyWelfareInfo.value = i
            }
        }

        //코로나19 카테고리 중 산출된 가장 높은 금액의 복지가 무엇인지 찾아서 _maxMoneyWelfareInfo에 저장하기 위한 작업
        for (i in _welfareInfo.value!!.mainWelfareResult.covidCategory) {
            if (maxMoneyOfCategory.maxOrNull() == i.maxMoney.toInt()) {
                _maxMoneyWelfareInfo.value = i
            }
        }
    }



//    /*
//    최대 수혜 금액 옆에 버튼 클릭하면 해당 복지를 보여줘야 함으로 api로 복지uid 보내고 복지 정보 받아서 detailActivity로 이동하는 메소드
//     */
//    fun tryGetOneOfWelfareInfo(){
//        println(_maxMoneyWelfareInfo.value.toString())
//        val welfareInterface = ApplicationClass.sRetrofit.create(WelfareInterface::class.java)
//        welfareInterface.getOneOfWelfareInfo("R2021021800249").enqueue(object:Callback<OneOfWelfareInfoResponse>{
//            override fun onResponse(call: Call<OneOfWelfareInfoResponse>, response: Response<OneOfWelfareInfoResponse>) {
//                if (response.isSuccessful){
//                    val result = response.body()
//                    val intent = Intent(mContext, WelfareDetailActivity::class.java)
//                    intent.putExtra("welfareInfo",result!!.oneOfWelfareInfoResult)
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
//                    mContext.startActivity(intent)
//                }else{
//                    Log.e("ERROR","복지 정보 하나만 실패")
//                }
//            }
//
//            override fun onFailure(call: Call<OneOfWelfareInfoResponse>, t: Throwable) {
//                Log.e("ERROR","복지 정보 하나만 통신 실패")
//                Log.e("ERROR",t.message.toString())
//            }
//        })
//    }


    /*
    그래프의 높이 비율을 구하기 위한 메소드
    한 단계 당 0.0298f
     */
    private fun getBias(bias: Int): Float {
        when (bias) {
            20 -> return 0.567f
            19 -> return 0.53719f
            18 -> return 0.5074f
            17 -> return 0.47759f
            16 -> return 0.4478f
            15 -> return 0.41799f
            14 -> return 0.38819f
            13 -> return 0.35839f
            12 -> return 0.32859f
            11 -> return 0.29879f
            10 -> return 0.26899f
            9 -> return 0.23919f
            8 -> return 0.20939f
            7 -> return 0.17959f
            6 -> return 0.14979f
            5 -> return 0.11999f
            4 -> return 0.09019f
            3 -> return 0.06039f
            2 -> return 0.03059f
            1 -> return 0.00079f
            else -> return 0.01f
        }
    }
}