package com.teamteam.knowing.ui.viewmodel

import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.data.model.network.response.SearchEmailResponse
import com.teamteam.knowing.data.remote.api.SearchEmailInterface
import com.teamteam.knowing.ui.view.sign_in.search_email.LoadingSearchEmailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchEmailActivityViewModel(application: Application):AndroidViewModel(application) {
    private val mContext=application.applicationContext

    private val _isCorrectEdtName = MutableLiveData<Boolean>() //이름Edt에 입력되었는지 안되었는지 판단하기 위한 라이브데이터
    private val _isCorrectEdtPhoneNum=MutableLiveData<Boolean>()

    private val _isAllCorrect=MutableLiveData<Boolean>() //모든 입력이 완료 되었는지 정보를 가지고 있는 라이브데이터

    val isCorrectEdtName : MutableLiveData<Boolean>
        get() = _isCorrectEdtName
    val isCorrectEdtPhoneNum : MutableLiveData<Boolean>
        get() = _isCorrectEdtPhoneNum

    val isAllCorrect : MutableLiveData<Boolean>
        get() = _isAllCorrect


    //이름, 이메일 2개다 조건을 만족하면 버튼 상태 true 아니면 false
    fun checkIsAllCorrect(){
        if (isCorrectEdtPhoneNum.value==true && _isCorrectEdtName.value==true)
            _isAllCorrect.value=true
        else
            _isAllCorrect.value=false
    }


    /*
    이름과 전화번호로 이메일을 찾기 위한 api통신 메소드
     */
    fun tryGetSearchEmail(name:String,phNum:String){
        val phoneNum = phNum.replace("-","")
        val searchEmailInterface = ApplicationClass.sRetrofit.create(SearchEmailInterface::class.java)
        searchEmailInterface.getSearchEmail(name,phoneNum).enqueue(object :
            Callback<SearchEmailResponse> {
            override fun onResponse(
                call: Call<SearchEmailResponse>,
                response: Response<SearchEmailResponse>
            ) {
                if (response.isSuccessful){
                    val result = response.body() as SearchEmailResponse
                    if (result.emailSearchResult.email!="none"){
                        val intent = Intent(mContext, LoadingSearchEmailActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                        intent.putExtra("email",result.emailSearchResult.email)//api로 받아온 email주소를 intent로 넘겨준다.
                        intent.putExtra("name",name)
                        mContext.startActivity(intent)
                    }else{
                        Toast.makeText(mContext,"일치하는 이메일이 존재하지 않습니다.",Toast.LENGTH_SHORT).show()
                        Log.e("ERROR","이메일 찾기 api 결과 실패")
                    }
                }
            }

            override fun onFailure(call: Call<SearchEmailResponse>, t: Throwable) {
                Toast.makeText(mContext,"죄송합니다 통신에 오류가 있습니다. 다시 시도해주세요",Toast.LENGTH_SHORT).show()
                Log.e("ERROR","이메일 찾기 api 통신 실패")
            }
        })
    }

}