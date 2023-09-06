package com.ecw.foodrankpr.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ecw.foodrankpr.LoginMainActivity
import com.ecw.foodrankpr.databinding.ActivityUserJoinBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// 회원가입 페이지
class UserJoinActivity : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth
    private lateinit var userDb: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUserJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //인증초기화
        mAuth = Firebase.auth

        //유저db 초기화
        userDb = Firebase.database.reference

        binding.btnSign.setOnClickListener {
            val email = binding.Email.text.toString().trim()
            val pasword = binding.Password.text.toString().trim()
            val name = binding.Username.text.toString().trim()
            val tel = binding.Tel.text.toString().trim()
            val adr = binding.Address.text.toString().trim()
            signUp(email,pasword,name,tel,adr)
        }
    }

    // 회원가입 값 확인 및 저장
    private fun signUp(email:String, password:String, name:String, tel:String, adr:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //성공시
                    Toast.makeText(this,"회원가입 성공", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, LoginMainActivity::class.java)
                    startActivity(intent)
                    addUserDb(email,name,tel,adr,mAuth.currentUser?.uid!!)

                } else {
                    //실패시
                    Toast.makeText(this,"회원가입 실패", Toast.LENGTH_LONG).show()
                }
            }
    }

    // 회원가입 db 저장
    private fun addUserDb(email:String,name:String,tel:String,adr:String,uId:String){
        userDb.child("user").child(uId).setValue(UserModel(email,name,tel,adr,uId))
    }




}