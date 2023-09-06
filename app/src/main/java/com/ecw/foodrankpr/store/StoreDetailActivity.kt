package com.ecw.foodrankpr.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ecw.foodrankpr.MyApplication


import com.ecw.foodrankpr.comment.commenAdapter
import com.ecw.foodrankpr.comment.commenModel
import com.ecw.foodrankpr.databinding.ActivityDetailBinding
import com.ecw.foodrankpr.heart.heartModel

import com.ecw.foodrankpr.user.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StoreDetailActivity : AppCompatActivity() {


    lateinit var heartId:heartModel
    lateinit var binding: ActivityDetailBinding
    lateinit var mAuth: FirebaseAuth
    private lateinit var myRef:DatabaseReference
    lateinit var currentUser: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // store 데이터 값 가져와 출력하기
        val secondIntent = intent


        binding.restaurantName.text = secondIntent.getStringExtra("storeName")
        binding.writerName.text = secondIntent.getStringExtra("name")
        binding.title.text = secondIntent.getStringExtra("mapaddress")
        binding.addReview.text = secondIntent.getStringExtra("reviewText")
        binding.content.text = secondIntent.getStringExtra("storeTime")
        binding.category.text = secondIntent.getStringExtra("storeCategory")
        binding.ratingAddScore.text = secondIntent.getStringExtra("ratingbar")



        // store 이미지 데이터 값 가져와 출력하기
        val storeimage = secondIntent.getStringExtra("id")
        val imgRef = MyApplication.storage.reference.child("images/${storeimage}.jpg")
        imgRef.downloadUrl.addOnCompleteListener {tast->
            Glide.with(this)
                .load(tast.result)
                .into(binding.storeImg)

        }

        //좋아요 db 가져오기
        heartId = heartModel()
        var heartuid = MyApplication.db.collection("heart").document().id
        var boardid = intent.getStringExtra("id")

        var storeboardId:String?=null   //리뷰페이지 id
        var heartpointTF:String?=null    // 좋아요 ON/OFF 확인용
        var heartuserID:String?=null    // 좋아요 누른 UserID

        // 좋아요 DB 데이터 값 불러오기
        var heatuidpr = MyApplication.db.collection("heart")
            .whereEqualTo("boardId",boardid)
            .get()
            .addOnSuccessListener {result->
                val itemList= mutableListOf<heartModel>()
                for(document in result){
                    val item=document.toObject(heartModel::class.java)
                    storeboardId=item.boardId.toString()
                    heartpointTF = item.heartPoint.toString()
                    heartuserID = item.userId.toString()
                }
            }

        // 쓰레드 지연 코드 (IF문 이 위에 db데이터 값 불러오기전에 실행되기때문에 지연 코드 작성)
        GlobalScope.launch {
            delay(2000)
            runOnUiThread {// 지연코드 작성시 VIEW페이지가 그냥 종료되어 VIEW페이지 시작하는 코드
                // 좋아요 눌렀을때 발생
                if (storeboardId == boardid && heartpointTF == "1" && heartuserID == mAuth.uid ) {
                    binding.fbUnlike.visibility = View.GONE
                    binding.fbLike.visibility = View.VISIBLE

                } else { //좋아요 안눌렀을때 발생

                    binding.fbLike.visibility = View.GONE
                    // 좋아요 누를시 DB에 데이터 저장
                    binding.fbUnlike.setOnClickListener {
                        var bid = intent.getStringExtra("id")
                        var like1 = MyApplication.db.collection("news").document(bid.toString())

                        like1.update("likePoint", FieldValue.increment(1))
                            .addOnSuccessListener {
                                var data = mapOf(
                                    "boardId" to bid.toString(),
                                    "heartPoint" to "1",
                                    "userId" to currentUser.uId,
                                    "uId" to heartuid,
                                    "storeName" to intent.getStringExtra("storeName").toString(),
                                    "mapaddress" to intent.getStringExtra("mapaddress").toString(),
                                    "storeTime" to intent.getStringExtra("storeTime").toString()
                                )

                                MyApplication.db.collection("heart").document(heartuid)
                                    .set(data)
                            }

                        // 종료 후 재시작
                        finish()
                        GlobalScope.launch {
                            delay(1000)
                            startActivity(intent)
                        }
                    }
                }
            }
        }

        binding.commentSave.setOnClickListener {
            saveComment()
        }

        mAuth = Firebase.auth
        myRef = MyApplication.userDb.reference

        myRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                for (postSnapshot in snapshot.children) {
                    val currentUser1 = postSnapshot.getValue(UserModel::class.java)
                    if (mAuth.currentUser?.uid == currentUser1?.uId) {
                        if (currentUser1 != null) {
                            currentUser = currentUser1
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    //댓글 DB에 저장
    private fun saveComment() {
        val intent2 = intent
        var data = mapOf(
            "name" to currentUser.name,
            "storeId" to intent2.getStringExtra("id"),
            "comment" to binding.comment.text.toString()
        )

        MyApplication.db.collection("comment")
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this,"저장되었습니다",Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Log.d("mylog","등록실패")
            }
    }

    override fun onStart() {
        super.onStart()
        commentRecyclerView()
    }

    // DB 값 받아와서 출력
    private fun commentRecyclerView() {
        val id = intent.getStringExtra("id")
        MyApplication.db.collection("comment")
            .whereEqualTo("storeId",id)
            .get()
            .addOnSuccessListener {result->
                val itemList= mutableListOf<commenModel>()
                for(document in result){
                    val item=document.toObject(commenModel::class.java)
                    itemList.add(item)
                }
                binding.commentRecyclerView.layoutManager = LinearLayoutManager(this)
                binding.commentRecyclerView.adapter = commenAdapter(this,itemList)
            }
    }

}