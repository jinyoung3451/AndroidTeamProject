package com.ecw.foodrankpr.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ecw.foodrankpr.MainActivity
import com.ecw.foodrankpr.MyApplication
import com.ecw.foodrankpr.databinding.ActivityUserLoginBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// 유저 로그인 페이지
class UserLoginActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //초기화
        mAuth = Firebase.auth

        binding.btnSign.setOnClickListener {
            var email = binding.Email.text.toString()
            var password = binding.Password.text.toString()
            login(email,password)
        }

    }


    //로그인
    private fun login(email:String,password:String){
        MyApplication.mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 성공시
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"로그인 성공", Toast.LENGTH_LONG).show()
                    finish()

                } else {
                    // 실패시
                    Toast.makeText(this,"로그인 실패", Toast.LENGTH_LONG).show()
                    Log.d("myLog","error:${task.exception}")

                }
            }
    }
}