package com.ecw.foodrankpr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ecw.foodrankpr.databinding.ActivityLoginmainBinding
import com.ecw.foodrankpr.user.UserJoinActivity
import com.ecw.foodrankpr.user.UserLoginActivity
import com.ecw.foodrankpr.user.UserSearchActivity

// 로그인 메인페이지
class LoginMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =  ActivityLoginmainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로그인페이지 이동
        binding.LoginBtn.setOnClickListener {
            val intent= Intent( this@LoginMainActivity, UserLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        //회원가입 페이지 이동
        binding.JoinBtn.setOnClickListener {
            val intent= Intent( this@LoginMainActivity, UserJoinActivity::class.java)
            startActivity(intent)
        }

        //비밀번호 찾기 페이지 이동
        binding.FindPassBtn.setOnClickListener {
            val  intent = Intent(this@LoginMainActivity, UserSearchActivity::class.java)
        startActivity(intent)
        }



    }
}