package com.example.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.knowing.ui.view.sign_in.search_pwd.LoadingSearchPwdActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SearchPwdActivityViewModel(application: Application):AndroidViewModel(application) {
    private lateinit var firebaseAuth: FirebaseAuth
    private var mContext = application.applicationContext

    private val _isCorrectEdtName = MutableLiveData<Boolean>() //이름Edt에 입력되었는지 안되었는지 판단하기 위한 라이브데이터
    private val _isCorrectEdtEmail = MutableLiveData<Boolean>()//emailEdt가 이메일 형식으로 제대로 작성되었는지 확인하는 라이브 데이터

    private val _isAllCorrect = MutableLiveData<Boolean>()//모든 조건이 만족하면 메일 보내기 버튼이 활성화 되도록 상태를 저장할 라이브 데이터

    val isCorrectEdtName : MutableLiveData<Boolean>
        get() = _isCorrectEdtName
    val isCorrectEdtEmail : MutableLiveData<Boolean>
        get() = _isCorrectEdtEmail

    val isAllCorrect : MutableLiveData<Boolean>
        get() = _isAllCorrect



    //이름, 이메일 2개다 조건을 만족하면 버튼 상태 true 아니면 false
    fun checkIsAllCorrect(){
        if (_isCorrectEdtEmail.value==true && _isCorrectEdtName.value==true)
            _isAllCorrect.value=true
        else
            _isAllCorrect.value=false
    }

    fun sendEmail(emailAddress:String){
        //firebaseAuth 인스턴스 생성
        firebaseAuth= Firebase.auth

        //메일로 비밀번호 재설정 메일 보내줌
        firebaseAuth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    val intent=Intent(mContext,LoadingSearchPwdActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                    mContext.startActivity(intent)
                }else{
                    Toast.makeText(mContext,"입력하신 이메일은 회원이 아닙니다", Toast.LENGTH_SHORT).show()
                }
            }
    }
}