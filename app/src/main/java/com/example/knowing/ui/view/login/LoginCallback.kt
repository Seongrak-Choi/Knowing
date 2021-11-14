package com.example.knowing.ui.view.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import org.json.JSONArray
import org.json.JSONObject

class LoginCallback : FacebookCallback<LoginResult> {
    //로그인 성공 시 호출된다. Access Token 발급 성공
    override fun onSuccess(result: LoginResult?) {
        Log.e("Callback::","onSuccess")
        requestMe(result!!.accessToken)
    }

    //로그인 창을 닫을 경우, 호출
    override fun onCancel() {
        Log.e("Callback::","onCancel")
    }

    //로그인 실패 시에 호출
    override fun onError(error: FacebookException?) {
        Log.e("Callback::","onError:"+error!!.message)
    }

    //사용자 정보 요청
    fun requestMe(token:AccessToken){
        val graphRequest = GraphRequest.newMeRequest(token,object : GraphRequest.GraphJSONArrayCallback,
            GraphRequest.GraphJSONObjectCallback {
            override fun onCompleted(objects: JSONArray?, response: GraphResponse?) {
                Log.e("result",objects.toString())
            }

            override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
            }
        })

        val parameters = Bundle()
        parameters.putString("fields","id,name,eamil,gender,birthday")
        graphRequest.parameters=parameters
        graphRequest.executeAsync()
    }
}
