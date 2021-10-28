package com.example.knowing.ui.view.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import com.example.knowing.R
import com.example.knowing.databinding.ActivityJoinUpSnsBinding
import com.example.knowing.ui.base.BaseActivity
import com.example.knowing.ui.viewmodel.JoinUpSNSActivityViewModel
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class JoinUpSNSActivity : BaseActivity<ActivityJoinUpSnsBinding>(ActivityJoinUpSnsBinding::inflate),
    GoogleApiClient.OnConnectionFailedListener {
    private lateinit var joinUpSNSActivityViewModel : JoinUpSNSActivityViewModel
    private lateinit var auth : FirebaseAuth
    private lateinit var googleApiClient : GoogleApiClient

    companion object{
        private val REQ_SIGN_GOOGLE = 100 //구글 로그인 결과 코드드
   }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleApiClient = GoogleApiClient.Builder(this)
            .enableAutoManage(this,this)
            .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
            .build()

        auth=FirebaseAuth.getInstance()//파이어베이스 인증 객체 초기화

        joinUpSNSActivityViewModel = ViewModelProvider(this).get(JoinUpSNSActivityViewModel::class.java) //뷰모델 장착

        //sstatusbar 투명하게 설정(네비게이션bar는 투명해지지 않음)
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

        binding.btnLoginGoogle.setOnClickListener {
            val intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
            startActivityForResult(intent, REQ_SIGN_GOOGLE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) { //구글 로그인 인증을 요청 했을 때 결과 값을 되돌려 받는 곳.
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode== REQ_SIGN_GOOGLE){
            var result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess){//인증결과가 성공인지 확인
                var account = result.signInAccount //accout라는 데이터는 구글 로그인 정보를 담고있다. (닉네임,포르필사진url,이메일주소...)
                resultLogin(account)
            }
        }
    }

    private fun resultLogin(account : GoogleSignInAccount){
        var credential:AuthCredential = GoogleAuthProvider.getCredential(account.idToken,null) //구글 로그인을 firebase와 연동
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this, object:OnCompleteListener<AuthResult> {
                override fun onComplete(task: Task<AuthResult>) {
                    if(task.isSuccessful){ //firebase와 연동이 성공해서 로그인이 되었으면...
                        Toast.makeText(applicationContext,"구글 로그인 성공",Toast.LENGTH_SHORT).show()

                    }else{ //로그인이 실패했으면....
                        Toast.makeText(applicationContext,"구글 로그인 실패",Toast.LENGTH_SHORT).show()
                    }
                }

            })
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
}