package com.example.knowing.ui.view.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.databinding.ActivityJoinUpSnsBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.view.sign_in.SignInActivity
import com.example.knowing.ui.view.sign_up.SignUpActivity
import com.example.knowing.ui.viewmodel.JoinUpSNSActivityViewModel
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.common.util.Utility
import java.util.*


class JoinUpSNSActivity : BaseActivity<ActivityJoinUpSnsBinding>(ActivityJoinUpSnsBinding::inflate),
    GoogleApiClient.OnConnectionFailedListener {
    private lateinit var joinUpSNSActivityViewModel : JoinUpSNSActivityViewModel
    private lateinit var auth : FirebaseAuth
    private lateinit var googleApiClient : GoogleApiClient
    private lateinit var callbackManager : CallbackManager

    companion object{
        private val REQ_SIGN_GOOGLE = 100 //구글 로그인 결과 코드
        private val REQ_SIGN_FACEBOOK = 200 //facebook 로그인 코드
   }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //해시키 값을 구하기 위한 코드
//        var keyHash = Utility.getKeyHash(this)
//        println("keyHash: $keyHash")


        //firebase customToken으로 로그인
//        firebaseAuth.signInWithCustomToken("")
//            .addOnCompleteListener {
//                if(it.isSuccessful){
//                    var user = firebaseAuth.currentUser
//                    println("user: $user")
//                }else{
//                    Toast.makeText(this,"커스텀 토큰으로 로그인 실패",Toast.LENGTH_SHORT).show()
//                }
//            }


        //구글 로그인을 위한 빌더 설정
        var googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        //구글 로그인을 위한 Api클라이언트 인스턴스 생성
        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
            .build()


        //facebook로그인을 위한 콜백매니저 생성
        callbackManager= CallbackManager.Factory.create()


        //파이어베이스 인증 객체 초기화
        auth=FirebaseAuth.getInstance()


        joinUpSNSActivityViewModel = ViewModelProvider(this).get(JoinUpSNSActivityViewModel::class.java) //뷰모델 장착

        //statusbar 투명하게 설정(네비게이션bar는 투명해지지 않음)
        window?.decorView?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = Color.TRANSPARENT

        //네이버아이디로 로그인 버튼 클릭 리스너
        binding.btnLoginNaver.setOnClickListener {
            joinUpSNSActivityViewModel.setActivity(this) //activity의 정보를 viewModel로 넘겨준다. 네이버아이디로로그인 할 때 필요함.
            joinUpSNSActivityViewModel.getNaverToken() //네이버 로그인을 진행한다.
        }

        //카카오계정 로그인 버튼 클릭 리스너
        binding.btnLoginKakao.setOnClickListener {
            joinUpSNSActivityViewModel.getKakaoToken()
        }

        //구글계정 로그인 버튼 클릭 리스너
        binding.btnLoginGoogle.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient) //구글 로그인 화면 intent객체 생성
            startActivityForResult(intent, REQ_SIGN_GOOGLE) //구글 로그인 intent로 이동
        }

        //facebook 로그인 버튼 클릭 리스너
        binding.btnLoginFacebook.setOnClickListener {
            facebookLogin()
        }


        //이메일로 로그인 버튼 클릭 리스너
        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        //이메일로 회원가입 버튼 클릭 리스너
        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //구글 로그인 인증을 요청 했을 때 결과 값을 되돌려 받는 곳.
        super.onActivityResult(requestCode, resultCode, data)

        //돌려 받은 값이 google로그인 코드인 경우
        if(requestCode== REQ_SIGN_GOOGLE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess){//인증결과가 성공인지 확인
                var account = result.signInAccount //accout라는 데이터는 구글 로그인 정보를 담고있다. (닉네임,포르필사진url,이메일주소...)

                //firebase에 google계정 등록하려면 activity가 필요하대.... 이러면 안될거 같지만 일단 이렇게 처리함.
                joinUpSNSActivityViewModel.setActivity(this)
                //돌려받은 결과와 firebase를 연결해주는 메소드 호출
                joinUpSNSActivityViewModel.resultLogin(account)
            }
        }else{//돌려 받은 값이 google이 아닌 facebook인 경우
            callbackManager.onActivityResult(requestCode,resultCode,data)
        }
    }

    /*
      facebooklogin을 하기위해 로그인 activity로 갔다가 callback을 받아서 성공했을 경우
      firebase와 연결하는 메소드로 이동. callbackManager때문에 이 함수는 뷰모델에서 사용하기 어려움....
       */
    fun facebookLogin() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, listOf("email","public_profile"))

        LoginManager.getInstance()
            .registerCallback(callbackManager, object: FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    if (result?.accessToken != null) {
                        // facebook 계정 정보를 firebase 서버에게 전달(로그인)
                        val accessToken = result.accessToken
                        joinUpSNSActivityViewModel.firebaseAuthWithFacebook(accessToken)
                    } else {
                        Log.d("Facebook", "Fail Facebook Login")
                    }
                }
                override fun onCancel() {
                    //취소가 된 경우 할일
                }
                override fun onError(error: FacebookException?) {
                    Log.d("FacebookError", error.toString())                }
            })
    }



    /*
    구글 계정 로그인 하기 위해 필요한 override 메소드인데 어디에 쓰는지 아직 잘 모름
     */
    override fun onConnectionFailed(p0: ConnectionResult) {
    }
}