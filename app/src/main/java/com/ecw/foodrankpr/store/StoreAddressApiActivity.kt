package com.ecw.foodrankpr.store

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.ecw.foodrankpr.databinding.ActivityAddressApiBinding

// 주소찾기 페이지
class StoreAddressApiActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 100
    }

    private var webView: WebView? = null
    var TAG = "AddressApiActivity";
    lateinit var activityAddressApiBinding : ActivityAddressApiBinding

    // 주소찾기 자바 스크립트
    inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(dataA: String?) {
            val intent = Intent()
            intent.putExtra("dataAdr", dataA)
            setResult(RESULT_OK,intent)
            Log.d("myLog5","${intent}")
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  페이지 url
        val blogspot = "https://chayubeen.blogspot.com/p/blog-page.html"

        activityAddressApiBinding = ActivityAddressApiBinding.inflate(layoutInflater)
        setContentView(activityAddressApiBinding.root)
        activityAddressApiBinding.webView!!.settings.javaScriptEnabled = true
        activityAddressApiBinding.webView!!.addJavascriptInterface(MyJavaScriptInterface(), "Android")
        activityAddressApiBinding.webView!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                //  script 호출
                view.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }
        //  페이지를 호출
        activityAddressApiBinding.webView!!.loadUrl(blogspot)
    }
}