package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.ui.view.main.LoadingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivityViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application.applicationContext

    private val _allIsCorrect = MutableLiveData<Boolean>() //이메일과 패스워드의 참 여부를 판단하여 로그인하기 버튼의 활성화를 책임지는 라이브 데이터
    private val _emailIsCorrect = MutableLiveData<Boolean>()  //입력한 email이 정상인지 체크하는 라이브 데이터
    private val _pwdIsCorrect = MutableLiveData<Boolean>() //입력한 패스워드가 정상인지 체크하는 라이브 데이터


    private lateinit var firebaseAuth: FirebaseAuth //파이버베이스 로그인 정보 저장할 변수


    val allIsCorrect : MutableLiveData<Boolean>
        get() = _allIsCorrect

    val emailIsCorrect : MutableLiveData<Boolean>
        get() = _emailIsCorrect

    val pwdIsCorrect : MutableLiveData<Boolean>
        get() = _pwdIsCorrect

    init {
        _allIsCorrect.value=false
        _emailIsCorrect.value=false
        _pwdIsCorrect.value=false
    }


    /*
    emailIsCorrect와 pwdIsCorrect가 참인 경우 allIsCorrect를 true로 변경해주기 위한 메소드
     */
    fun isAllCorrect(){
        if (_emailIsCorrect.value==true && _pwdIsCorrect.value==true) //둘다 true이면
            _allIsCorrect.value=true //로그인하기 버튼의 활성화를 담당하는 라이브데이터를 true로 변경
        else
            _allIsCorrect.value=false //로그인하기 버튼의 활성화를 담당하는 라이브데이터를 false로 변경
    }


    /*
    firebase 로그인 요청 메소드
     */
    fun tryLoginWithFirebase(email:String,pwd:String){
        firebaseAuth= Firebase.auth
        firebaseAuth.signInWithEmailAndPassword(email,pwd)
            .addOnCompleteListener {
                Log.d("INFO","Firebase로그인 결과 ${it.exception}")
                if (it.isSuccessful){
                    Log.d("INFO","이메일로 로그인 firebase로그인 성공")

                    //전역변수에 UID를 저장해 둠
                    ApplicationClass.USER_UID =firebaseAuth.currentUser?.uid.toString()

                    //firebase에 로그인 한 후 해당 uid를 받아서 sp에 저장
                    val editor = ApplicationClass.sp.edit()
                    editor.putString(ApplicationClass.UID_KEY,firebaseAuth.currentUser?.uid.toString())
                    editor.apply()

                    //로딩해주는 화면으로 이동
                    val intent = Intent(mContext, LoadingActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                    mContext.startActivity(intent)
                }else{
                    Toast.makeText(mContext,"이메일 또는 비밀번호가 일치하지 않습니다.",Toast.LENGTH_SHORT).show()
                    Log.e("ERROR","이메일로 로그인 firebase로그인 실패")
                }
            }
    }

}