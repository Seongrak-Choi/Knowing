package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.viewpager2.widget.ViewPager2
import com.teamteam.knowing.ui.adapter.OnboardingActivityViewPager2Adapter
import com.teamteam.knowing.ui.view.login.JoinUpSNSActivity

class OnboardingActivityViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var viewpager2: ViewPager2 //activity의 viewpager2 저장할 변수
    private val mApplication = application //view의 application저장할 변수
    private val _currentBtnText = MutableLiveData<String>()

    //온보딩 화면이 끝나면 join에서 뒤로가기 해도 다시 볼 수 없도록 finish하기 위한 데이터를 저장할 라이브데이터
    private val _isSuccessGo = MutableLiveData<Boolean>()


    val currentBtnText:MutableLiveData<String>
        get() = _currentBtnText

    val isSuccessGo:MutableLiveData<Boolean>
        get() = _isSuccessGo

    init {
        _currentBtnText.value="다음"
    }


    /*
    viewPager2 Adapter 설정
     */
    fun setViewPager2Adapter(viewPager2: ViewPager2, fragmentActivity: FragmentActivity) {
        this.viewpager2 = viewPager2
        this.viewpager2.adapter = OnboardingActivityViewPager2Adapter(fragmentActivity)
    }


    /*
    '다음' 버튼 클릭 이벤트
     */
    fun onNextFragment() {
        //버튼의 텍스가 다음인지 시작하기인지 판단하여 시작하기인 경우 joinupsnsactivity로 이동
        if(_currentBtnText.value=="다음"){
            viewpager2.setCurrentItem(getCurrentItem()+1, true)
            _currentBtnText.postValue("시작하기")
        }else{
            var intent = Intent(mApplication.applicationContext, JoinUpSNSActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
            mApplication.startActivity(intent)

            //onboarding activity finish 하기 위해 라이브데이터 true로 변환
            _isSuccessGo.value=true
        }

    }


    /*
    버튼의 텍스트를 변경하기위해 livedata를 update하는 함수
     */
    fun updateBtnText(position:Int){
        when(position){
            0->_currentBtnText.postValue("다음")
            1->_currentBtnText.postValue("시작하기")
       }
    }



    /*
    viewPager2의 현재 아이템 position을 넘겨받는 함수
     */
    fun getCurrentItem(): Int {
        return viewpager2.currentItem
    }

}