package com.ecw.foodrankpr.user

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ecw.foodrankpr.databinding.ActivitySearchBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

// 비밀번호 찾기 페이지
class UserSearchActivity: AppCompatActivity() {
    lateinit var auth:FirebaseAuth
    lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth


        binding.searchPwBtn.setOnClickListener{
            send()
        }

        binding.pwReturnImg.setOnClickListener {
            val intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }

        binding.pwReturnText.setOnClickListener {
            val intent = Intent(this, UserLoginActivity::class.java)
            startActivity(intent)
        }

    }

    // 비밀번호찾기 이메일로 보내기
    private fun send() {
        val email = binding.searchPwEt.text.toString()
        if (email.length !=0){
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener{task->
                    if (task.isSuccessful){
                        Toast.makeText(this,"이메일 보내기 완료",Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show()
        }
    }

}