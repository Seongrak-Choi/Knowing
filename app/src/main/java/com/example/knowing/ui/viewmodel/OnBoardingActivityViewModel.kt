package com.example.knowing.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.knowing.config.ApplicationClass
import com.example.knowing.ui.view.login.OnBoardingActivity
import com.kakao.sdk.auth.LoginClient

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

class OnBoardingActivityViewModel(application: Application) : AndroidViewModel(application){
    private val mOAuthLoginInstance = OAuthLogin.getInstance() //네아로 인스턴스 생성
    private val myApplication = application //application을 전역변수로 사용할 수 있도록 세팅
    private val myContext = myApplication.applicationContext //Context를 전역변수로 사용할 수 있도록 세팅
    private lateinit var activity: OnBoardingActivity




    /*
    네아로 로그인 버튼 클릭 시 실행
    네이버로그인 API로 로그인을 진행하고 토큰을 받아온다.
     */
    fun getNaverToken(){
        //인스턴스 초기화 init(context,id,secret,name)
        mOAuthLoginInstance.init(myContext,ApplicationClass().naver_client_id,ApplicationClass().naver_client_secret,ApplicationClass().naver_client_name)
        mOAuthLoginInstance.startOauthLoginActivity(activity,mOAuthLoginHandler)
    }

    /*
    startOauthLoginActivity메소드를 호출할 때 필요한 핸들러 이 곳에서 로그인 성공과 실패 했을 경우를 정의한다.
     */
    private val mOAuthLoginHandler : OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler(){
        override fun run(success: Boolean) {
            if(success){
                //로그인이 성공했을 경우
                val accessToken = mOAuthLoginInstance.getAccessToken(myContext) //로그인 결과로 얻은 접근 토큰을 반환
                val refreshToken = mOAuthLoginInstance.getRefreshToken(myContext) //refresh토큰 값을 저장
                val expiresAt = mOAuthLoginInstance.getExpiresAt(myContext) //토큰 만료기간을 저장
                val tokenType = mOAuthLoginInstance.getTokenType(myContext) //토큰의 타입을 저장
            }
            else{
                //로그인이 실패했을 경우
                val errorCode = mOAuthLoginInstance.getLastErrorCode(myContext).code //에러 코드 저장
                val errorDesc = mOAuthLoginInstance.getLastErrorDesc(myContext)//에러 내용 저장
                Toast.makeText(myContext, "errorCode:$errorCode, errorDesc:$errorDesc",Toast.LENGTH_SHORT).show()//Toast로 오류 코드와 메세지 출력
            }
        }

    }



    /*
    카카오계정 로그인 버튼 클릭시 실행
    카카오계정 로그인 진행하고 토큰값 받아오는 함수
     */
    fun getKakaoToken(){

//        //우리는 JWT를 SharedPreference에 저장해서 로그인의 여부를 확인할 것이기 때문에 필요 없다.
//        //로그인 정보를 확인한다. 로그인 정보가 남아있다면 자동 로그인 성공
//        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
//            if (error != null) {
//                Toast.makeText(myContext, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
//            }
//            else if (tokenInfo != null) {
//                Toast.makeText(myContext, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
//                //액티비티 이동
//            }
//        }


        //로그인 진행하고 동작할 callback 정의
        val callback: (OAuthToken?,Throwable?)->Unit = {token,error ->
            if (error != null){ //에러에 데이터가 담겨있다면 아래 when문 실행
                when{ //로그인 종류에 따라 오류 내용 출력
                    error.toString() == AccessDenied.toString() -> {
                        Toast.makeText(myContext, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidClient.toString() -> {
                        Toast.makeText(myContext, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidGrant.toString() -> {
                        Toast.makeText(myContext, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidRequest.toString() -> {
                        Toast.makeText(myContext, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidScope.toString() -> {
                        Toast.makeText(myContext, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Misconfigured.toString() -> {
                        Toast.makeText(myContext, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == ServerError.toString() -> {
                        Toast.makeText(myContext, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Unauthorized.toString() -> {
                        Toast.makeText(myContext, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(myContext, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token !=null){ //token값이 null이 아니면 실행
                Toast.makeText(myContext,"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show()

                UserApiClient.instance.me { user, error ->
                    println("닉네임: ${user?.kakaoAccount?.profile?.nickname}")
                    println("e-mail: ${user?.kakaoAccount?.email}")
                    println("e-mail: ${user?.kakaoAccount?.ageRange}")
                    println("e-mail: ${user?.kakaoAccount?.birthday}")
                    println("e-mail: ${user?.kakaoAccount?.gender}")

                }

                //액티비티 이동
            }
        }

        //카카오계정 로그인 진행
        if(LoginClient.instance.isKakaoTalkLoginAvailable(myContext)){ //카카오톡(어플)으로 로그인 가능한지 확인
            LoginClient.instance.loginWithKakaoTalk(myContext,callback = callback) //위에서 만든 callback 장착
        }else{ //카카오톡(어플)으로 로그인이 되지 않는 다면 웹뷰 dialog를 통해 카카오 계정 연결하는 방식
            LoginClient.instance.loginWithKakaoAccount(myContext,callback = callback)
        }
    }



    /*
    onBoardingActivity의 activity를 받아와 viewModel(자신)클래스에 저장한다. startOauthLoginActivity메소드를 호출할 때 필요함.
     */
    fun setActivity(activity: OnBoardingActivity){
        this.activity = activity
    }


}