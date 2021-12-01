package com.teamteam.knowing.ui.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.teamteam.knowing.config.ApplicationClass
import com.teamteam.knowing.config.ApplicationClass.Companion.UID_KEY
import com.teamteam.knowing.config.ApplicationClass.Companion.USER_UID
import com.teamteam.knowing.config.ApplicationClass.Companion.sp
import com.teamteam.knowing.data.model.network.response.GFDLoginResponse
import com.teamteam.knowing.data.model.network.response.KakaoLoginResponse
import com.teamteam.knowing.data.model.network.response.NaverLoginResponse
import com.teamteam.knowing.data.remote.api.JoinUpSNSInterface
import com.teamteam.knowing.ui.view.login.JoinUpSNSActivity
import com.teamteam.knowing.ui.view.main.LoadingActivity
import com.teamteam.knowing.ui.view.sign_up.SnsSignUpActivity
import com.facebook.AccessToken
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient

import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinUpSNSActivityViewModel(application: Application) : AndroidViewModel(application){
    private val mOAuthLoginInstance = OAuthLogin.getInstance() //네아로 인스턴스 생성
    private val myApplication = application //application을 전역변수로 사용할 수 있도록 세팅
    private val myContext = myApplication.applicationContext //Context를 전역변수로 사용할 수 있도록 세팅
    private lateinit var activity: JoinUpSNSActivity
    private lateinit var firebaseAuth: FirebaseAuth //파이버베이스 로그인 정보 저장할 변수



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
    네이버 로그인
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

                println("Naver accessToken: $accessToken")

                //네이버 acesstoken을 보내서 서버로 부터 customToken을 받기위해 메소드 실행
                tryGetNaverCustomToken(accessToken)
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

        //우리는 JWT를 SharedPreference에 저장해서 로그인의 여부를 확인할 것이기 때문에 필요 없다.
        //로그인 정보를 확인한다. 로그인 정보가 남아있다면 자동 로그인 성공
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
            }
            else if (tokenInfo != null) {
                //액티비티 이동
            }
        }


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
                        Toast.makeText(myContext, "로그인 취소", Toast.LENGTH_SHORT).show()
                        println("error: ${error}")
                    }
                }
            }
            else if (token !=null){ //token값이 null이 아니면 실행
                //accessToken을 서버로 보내 커스터 JWT받기 위해 메소드 호출
                tryGetKakaoCustomToken(token.accessToken)

//                UserApiClient.instance.me { user, error ->
//                    println("닉네임: ${user?.kakaoAccount?.profile?.nickname}")
//                    println("e-mail: ${user?.kakaoAccount?.email}")
//                    println("연령대: ${user?.kakaoAccount?.ageRange}")
//                    println("생년월일: ${user?.kakaoAccount?.birthday}")
//                    println("성별: ${user?.kakaoAccount?.gender}")
//                }

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
    fun setActivity(activity: JoinUpSNSActivity){
        this.activity = activity
    }


    /*
    Naver Acess Toeken을 firebase에 등록하기 위해 서버에 커스텀 JWT를 요청하는 API
     */
    fun tryGetNaverCustomToken(naver_access_token:String){
        val joinUpSNSInterface = ApplicationClass.sRetrofit.create(JoinUpSNSInterface::class.java)
        joinUpSNSInterface.getNaverCustomToken(naver_access_token).enqueue(object:
            Callback<NaverLoginResponse> {
            override fun onResponse(call: Call<NaverLoginResponse>, response: Response<NaverLoginResponse>) {
                if (response.isSuccessful){
                    val result = response.body() as NaverLoginResponse

                    //db에 email이 없으면 신규회워이기 때문에 firebase에 커스텀 JWT 등록 후 추가 정보 입력받는 화면으로 이동
                    if (result.naverLoginresult.status=="신규회원"){
                        //firebaseAuth 초기화
                        firebaseAuth= Firebase.auth

                        //커스텀 jwt로 계정 등록과 동시에 성공했을 경우인 addOnCompleteListener를 달아서 그 안에 성공했을 경우 수행
                        firebaseAuth.signInWithCustomToken(result.naverLoginresult.jwt).addOnCompleteListener {
                            //auth에 로그인 정보가 있는지 확인 후 intent진행
                            if (firebaseAuth.currentUser!=null){

                                //전역변수에 UID를 저장해 둠
                                USER_UID=firebaseAuth.currentUser?.uid.toString()

                                //firebase에 로그인 한 후 해당 uid를 받아서 sp에 저장
                                val editor = sp.edit()
                                editor.putString(UID_KEY,firebaseAuth.currentUser?.uid.toString())
                                editor.apply()

                                //추가 정보를 받기위해 SnsSignUpActivity로 이동
                                val intent = Intent(myContext,SnsSignUpActivity::class.java)
                                intent.putExtra("provider","naver")
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                                myContext.startActivity(intent)
                            }else{
                                Log.e("ERROR","firebaseAuth가 비어있음")
                                Toast.makeText(myContext,"로그인 중 오류가 생겼습니다.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{ //db에 email이 존재하면 기존회원이기 때문에 로그인 진행


                        firebaseAuth= Firebase.auth

                        //커스텀 jwt로 계정 등록과 동시에 성공했을 경우인 addOnCompleteListener를 달아서 그 안에 성공했을 경우 수행
                        firebaseAuth.signInWithCustomToken(result.naverLoginresult.jwt).addOnCompleteListener {
                            //auth에 로그인 정보가 있는지 확인 후 intent진행
                            if (firebaseAuth.currentUser!=null){

                                //전역변수에 UID를 저장해 둠
                                USER_UID=firebaseAuth.currentUser?.uid.toString()

                                //firebase에 로그인 한 후 해당 uid를 받아서 sp에 저장
                                val editor = sp.edit()
                                editor.putString(UID_KEY,firebaseAuth.currentUser?.uid.toString())
                                editor.apply()

                                //로딩해주는 화면으로 이동
                                val intent = Intent(myContext,LoadingActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                                myContext.startActivity(intent)
                            }else{
                                Log.e("ERROR","firebaseAuth가 비어있음")
                                Toast.makeText(myContext,"로그인 중 오류가 생겼습니다.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            override fun onFailure(call: Call<NaverLoginResponse>, t: Throwable) {
                Log.e("ERROR","서버에서 커스텀 토큰을 받아오지 못함")
                Toast.makeText(myContext,"로그인 중 오류가 생겼습니다.",Toast.LENGTH_SHORT).show()
            }
        })
    }



    /*
    Kakao Acess Toeken을 firebase에 등록하거나 기존회원이면 로그인하기 위해 서버에 커스텀 JWT를 요청하는 API
    */
    fun tryGetKakaoCustomToken(kakao_access_token:String) {
        val collegeInterface = ApplicationClass.sRetrofit.create(JoinUpSNSInterface::class.java)
        collegeInterface.getKakaoCustomToken(kakao_access_token).enqueue(object :
            Callback<KakaoLoginResponse> {
            override fun onResponse(
                call: Call<KakaoLoginResponse>,
                response: Response<KakaoLoginResponse>
            ) {
                if (response.isSuccessful){
                    val result = response.body() as KakaoLoginResponse

                    //db에 email이 없으면 신규회워이기 때문에 firebase에 커스텀 JWT 등록 후 추가 정보 입력받는 화면으로 이동
                    if (result.kakaoLoginresult.status=="신규회원"){
                        //firebaseAuth 초기화
                        firebaseAuth= Firebase.auth

                        //커스텀 jwt로 계정 등록과 동시에 성공했을 경우인 addOnCompleteListener를 달아서 그 안에 성공했을 경우 수행
                        firebaseAuth.signInWithCustomToken(result.kakaoLoginresult.jwt).addOnCompleteListener {
                            //auth에 로그인 정보가 있는지 확인 후 intent진행
                            if (firebaseAuth.currentUser!=null){

                                //전역변수에 UID를 저장해 둠
                                USER_UID=firebaseAuth.currentUser?.uid.toString()

                                //firebase에 로그인 한 후 해당 uid를 받아서 sp에 저장
                                val editor = sp.edit()
                                editor.putString(UID_KEY,firebaseAuth.currentUser?.uid.toString())
                                editor.apply()

                                //추가 정보를 받기위해 SnsSignUpActivity로 이동
                                val intent = Intent(myContext,SnsSignUpActivity::class.java)
                                intent.putExtra("provider","kakao")
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                                myContext.startActivity(intent)
                            }else{
                                Log.e("ERROR","firebaseAuth가 비어있음")
                                Toast.makeText(myContext,"로그인 중 오류가 생겼습니다.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{ //db에 email이 존재하면 기존회원이기 때문에 로그인 진행
                        firebaseAuth= Firebase.auth

                        //커스텀 jwt로 계정 등록과 동시에 성공했을 경우인 addOnCompleteListener를 달아서 그 안에 성공했을 경우 수행
                        firebaseAuth.signInWithCustomToken(result.kakaoLoginresult.jwt).addOnCompleteListener {
                            //auth에 로그인 정보가 있는지 확인 후 intent진행
                            if (firebaseAuth.currentUser!=null){

                                //전역변수에 UID를 저장해 둠
                                USER_UID=firebaseAuth.currentUser?.uid.toString()

                                //firebase에 로그인 한 후 해당 uid를 받아서 sp에 저장
                                val editor = sp.edit()
                                editor.putString(UID_KEY,firebaseAuth.currentUser?.uid.toString())
                                editor.apply()

                                //로딩해주는 화면으로 이동
                                val intent = Intent(myContext,LoadingActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                                myContext.startActivity(intent)
                            }else{
                                Log.e("ERROR","firebaseAuth가 비어있음")
                                Toast.makeText(myContext,"로그인 중 오류가 생겼습니다.",Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }else{
                    Log.e("ERROR","카카오 커스텀 토큰 반환 안됨")
                    println("카카오 로그인 실패")
                }
            }

            override fun onFailure(call: Call<KakaoLoginResponse>, t: Throwable) {
                Log.e("ERROR","서버에서 커스텀 토큰을 받아오지 못함")
                Toast.makeText(myContext,"로그인 중 오류가 생겼습니다.",Toast.LENGTH_SHORT).show()
            }
        })
    }


    /*
  Google,facebook,default로 로그인 시 uid가 db에 있는지 판단하고 db에 없으면 회원가입 있으면 로그인으로 진행하기 위해
  api 통신함
  */
    fun tryGetIsGFDInDB(uid:String,provider:String) {
        val collegeInterface = ApplicationClass.sRetrofit.create(JoinUpSNSInterface::class.java)
        collegeInterface.getIsGFDInDB(uid).enqueue(object :
            Callback<GFDLoginResponse> {
            override fun onResponse(
                call: Call<GFDLoginResponse>,
                response: Response<GFDLoginResponse>
            ) {
                if (response.isSuccessful){ //reponse가 성공적이면
                    val result = response.body() as GFDLoginResponse //body를 형변환해서 저장
                    if (result.gfdLoginresult.status){//신규 회원일 경우

                        //전역변수에 UID를 저장해 둠
                        USER_UID=uid

                        //firebase에 로그인 한 후 해당 uid를 받아서 sp에 저장
                        val editor = sp.edit()
                        editor.putString(UID_KEY,uid)
                        editor.apply()

                        val intent = Intent(myContext,SnsSignUpActivity::class.java)
                        intent.putExtra("provider",provider)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                        myContext.startActivity(intent)
                    }else{//신규회원이 아닌 기존회원일 경우

                        //전역변수에 UID를 저장해 둠
                        USER_UID=uid

                        //firebase에 로그인 한 후 해당 uid를 받아서 sp에 저장
                        val editor = sp.edit()
                        editor.putString(UID_KEY,uid)
                        editor.apply()

                        //로그인 진행
                        val intent = Intent(myContext,LoadingActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)//activity가 아닌 곳에서 startActivity를 할 경우 오류가 발생하기 때문에 flag를 지정해준다.
                        myContext.startActivity(intent)
                    }
                }else{//response가 실패면

                }
            }

            override fun onFailure(call: Call<GFDLoginResponse>, t: Throwable) {
                Log.e("ERROR","서버에서 커스텀 토큰을 받아오지 못함")
                Toast.makeText(myContext,"로그인 중 오류가 생겼습니다.",Toast.LENGTH_SHORT).show()
            }
        })
    }



    /*
  구글 로그인 화면에서 로그인이 완료 후 돌려받은 account값을 firebase와 연동하기 위한 메소드
   */
    fun resultLogin(account : GoogleSignInAccount){
        var credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken,null) //구글 로그인을 firebase와 연동

        //auth 객체 인스턴스화
        firebaseAuth= Firebase.auth


        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(activity, object: OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if(task.isSuccessful){ //firebase와 연동이 성공해서 로그인이 되었으면...
                        //계정의 uid가 db에 있는지 없는지 확인하기 위해 api통신
                        tryGetIsGFDInDB(firebaseAuth.currentUser!!.uid,"google")

                    }else{ //로그인이 실패했으면....
                        Toast.makeText(myContext,"구글 로그인 실패",Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }




    /*
    facebook에서 로그인한 토큰을 firebase와 연동하고 서버로 uid보내서 기존회원이지 신규인지 확인하기 위한 메소드
     */
    fun firebaseAuthWithFacebook(accessToken: AccessToken?) {
        // AccessToken 으로 Facebook 인증
        val credential = FacebookAuthProvider.getCredential(accessToken?.token!!)

        //auth 객체 인스턴스화
        firebaseAuth= Firebase.auth

        // 성공 시 Firebase 에 유저 정보 보내기 (로그인)
        firebaseAuth?.signInWithCredential(credential)
            ?.addOnCompleteListener{
                    task ->
                if(task.isSuccessful){ // 정상적으로 email, password 가 전달된 경우
                    // 로그인 처리
                    tryGetIsGFDInDB(firebaseAuth.currentUser!!.uid,"facebook")
                    println("facebook uid: ${firebaseAuth.currentUser!!.uid}")
                } else {
                    // 예외 발생 시 메시지 출력
                    Toast.makeText(myContext, task.exception?.message, Toast.LENGTH_LONG).show()
                    Log.e("facebookWithFirebaseError",task.exception?.message.toString())
                }
            }
    }





}