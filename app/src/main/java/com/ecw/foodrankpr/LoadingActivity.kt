package com.ecw.foodrankpr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.ecw.foodrankpr.databinding.ActivityLoadingBinding

//메인 로딩 페이지
class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent= Intent( this, LoginMainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
}
