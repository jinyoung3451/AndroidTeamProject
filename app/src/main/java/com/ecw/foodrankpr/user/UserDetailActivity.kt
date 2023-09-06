package com.ecw.foodrankpr.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ecw.foodrankpr.MyApplication
import com.ecw.foodrankpr.databinding.ActivityUserDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

// 유저 상세정보 페이지
class UserDetailActivity: AppCompatActivity() {

    lateinit var binding: ActivityUserDetailBinding
    lateinit var mAuth: FirebaseAuth
    private lateinit var myRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dao = UserDao()

        mAuth = Firebase.auth
        myRef = MyApplication.userDb.reference

        myRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(postSnapshot in snapshot.children){
                    Log.d("myLog3","!!!!!!!!!!!!!!!!!!!!")
                    val currentUser = postSnapshot.getValue(UserModel::class.java)

                    if(mAuth.currentUser?.uid == currentUser?.uId){
                        Log.d("myLog","${currentUser?.uId}")
                        binding.tvEmail.text = currentUser?.email
                        binding.tvName.setText(currentUser?.name)
                        binding.tvTel.setText(currentUser?.tel)
                        binding.tvAdr.setText(currentUser?.adr)

                        binding.btnUpdate.setOnClickListener {
                            val sKey:String = currentUser?.uId.toString()
                            val uTel = binding.tvTel.text.toString()
                            val uAdr = binding.tvAdr.text.toString()
                            val uName = binding.tvName.text.toString()
                            val hashMap:HashMap<String,Any> = HashMap()

                            hashMap["tel"] = uTel
                            hashMap["adr"] = uAdr
                            hashMap["name"] = uName

                            dao.userUpdate(sKey,hashMap).addOnSuccessListener {
                                Toast.makeText(applicationContext,"수정성공",Toast.LENGTH_LONG).show()
                                val intent = Intent(this@UserDetailActivity, UserMypageActivity::class.java)
                                startActivity(intent)
                                finish()

                            }.addOnFailureListener {
                                Toast.makeText(applicationContext,"수정실패",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        } )
    }
}