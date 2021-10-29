package com.example.knowing.ui.view.login

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.facebook.*
import com.facebook.login.LoginResult
import org.json.JSONArray
import org.json.JSONObject

class LoginCallback : FacebookCallback<LoginResult> {
    override fun onSuccess(result: LoginResult?) {
        Log.e("Callback::","onSuccess")
        requestMe(result!!.accessToken)
    }

    override fun onCancel() {
        Log.e("Callback::","onCancel")
    }

    override fun onError(error: FacebookException?) {
        Log.e("Callback::","onError:"+error!!.message)
    }

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
