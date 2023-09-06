package com.ecw.foodrankpr.heart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.ecw.foodrankpr.MyApplication
import com.ecw.foodrankpr.databinding.ActivityLikeviewBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//내가 누른 좋아요 출력 페이지
class LikeviewActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var binding: ActivityLikeviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikeviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        makeRecyclerView()
    }

    //좋아요 db 가져오기
    private fun makeRecyclerView() {
        mAuth = Firebase.auth
        MyApplication.db.collection("heart").whereEqualTo("heartPoint","1")
            .whereEqualTo("userId",mAuth.uid)
            .get()
            .addOnSuccessListener { result ->

                val itemList = mutableListOf<heartModel>()
                for (document in result) {
                    val item = document.toObject(heartModel::class.java)
                    item.userId = document.id
                    itemList.add(item)
                }
                binding.likeRecyclerView.layoutManager = GridLayoutManager(this, 2)
                binding.likeRecyclerView.adapter = MyAdapter2(this,itemList)
            }
    }
}