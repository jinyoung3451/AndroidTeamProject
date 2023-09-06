package com.ecw.foodrankpr.user

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.ecw.foodrankpr.LoginMainActivity
import com.ecw.foodrankpr.MyApplication
import com.ecw.foodrankpr.databinding.ActivityUserMypageBinding
import com.ecw.foodrankpr.heart.LikeviewActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class UserMypageActivity : AppCompatActivity() {

    lateinit var binding: ActivityUserMypageBinding
    lateinit var mAuth: FirebaseAuth
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dao = UserDao()
        myRef = MyApplication.userDb.reference
        mAuth = Firebase.auth

        myRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(UserModel::class.java)

                    if(mAuth.currentUser?.uid == currentUser?.uId){
                        Log.d("myLog","${currentUser?.uId}")
                        binding.profileTextviewName.text = currentUser?.name
                        binding.profileTextviewEmail.text = currentUser?.email
                        binding.profileTextviewPhone.text = currentUser?.tel
                        binding.profileTextviewAddress.text = currentUser?.adr

                        // 회원탈퇴
                        binding.delUser.setOnClickListener {
                            val builder = AlertDialog.Builder(this@UserMypageActivity)
                                .setTitle("회원탈퇴")
                                .setMessage("정말로 탈퇴하시겠습니까?")
                                .setPositiveButton("확인",
                                    DialogInterface.OnClickListener{ dialog, which ->
                                        val skey:String = currentUser?.uId.toString()
                                        dao.userDelete(skey).addOnSuccessListener {

                                            Toast.makeText(this@UserMypageActivity,"회원탈퇴 성공",Toast.LENGTH_LONG).show()
                                            val intent = Intent(this@UserMypageActivity,
                                                LoginMainActivity::class.java)
                                            mAuth.currentUser?.delete()
                                            startActivity(intent)
                                            finish()

                                        }.addOnFailureListener {
                                            Toast.makeText(this@UserMypageActivity,"회원탈퇴 실패",Toast.LENGTH_LONG).show()
                                        }

                                    })
                                .setNegativeButton("취소",
                                    DialogInterface.OnClickListener { dialog, which ->

                                    })
                            builder.show()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        } )

        binding.memberInfoChange.setOnClickListener {
            val intent = Intent(this, UserDetailActivity::class.java)
            startActivity(intent)
        }

        binding.likeCheck.setOnClickListener {
            val intent = Intent(this, LikeviewActivity::class.java)
            startActivity(intent)
        }

    }
}