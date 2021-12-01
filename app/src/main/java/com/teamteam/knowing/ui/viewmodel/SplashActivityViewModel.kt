package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass.Companion.IS_FIRST_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.ui.view.agree_terms.AgreeTermsActivity
import com.teamteam.knowing.ui.view.login.JoinUpSNSActivity
import com.teamteam.knowing.ui.view.main.LoadingActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application.applicationContext

    private val _isSuccessMove = MutableLiveData<Boolean>()

    val isSuccessMove : MutableLiveData<Boolean>
        get() = _isSuccessMove

    /*
    기기에 uid가 있는 경우 호출 될 메소드
     */
    fun goLoadingActivity(){
        CoroutineScope(Dispatchers.Main).launch {
            delay(1500L) //
            var intent = Intent(mContext,LoadingActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
            mContext.startActivity(intent)
            _isSuccessMove.value=true
        }
    }


    /*
    기기에 uid가 없는 경우 호출 될 메소드
     */
    fun goJoinSnsUpActivity(){
        //앱이 처음 실행되었는지 확인
        if(sp.getBoolean(IS_FIRST_KEY,false)){ //만약 처음 실행된게 아니라면 onJoinUpSnsActivity으로 이동
            CoroutineScope(Dispatchers.Main).launch {
                delay(1500L) //
                var intent = Intent(mContext,JoinUpSNSActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                mContext.startActivity(intent)
                _isSuccessMove.value=true
            }
        }else{//앱이 처음 실행 되었다면 onBoarding으로 이동
            CoroutineScope(Dispatchers.Main).launch {
                delay(1500L) //
                var intent = Intent(mContext,AgreeTermsActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                mContext.startActivity(intent)
                _isSuccessMove.value=true
            }
        }
    }
}