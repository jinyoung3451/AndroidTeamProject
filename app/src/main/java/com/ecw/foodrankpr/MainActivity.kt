package com.ecw.foodrankpr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.ecw.foodrankpr.store.ItemData
import com.ecw.foodrankpr.databinding.ActivityMainBinding
import com.ecw.foodrankpr.google.GoogleMapsActivity
import com.ecw.foodrankpr.store.StoreAddActivity
import com.ecw.foodrankpr.user.UserModel
import com.ecw.foodrankpr.user.UserMypageActivity
import com.ecw.foodrankpr.viewPage.ViewPagerAdapter
import com.example.myfirebaseapp.util.myCheckPermission

import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

// 로그인 후 메인페이지
class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    //헤더에 유저를 불러오기 위해 변수 타입선언
    lateinit var mAuth: FirebaseAuth
    private lateinit var myRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //이미지슬라이드 어뎁터 적용
        binding.viewPagerFood.adapter = ViewPagerAdapter(this, getFoodList())
        binding.viewPagerFood.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        //퍼미션 체크
        myCheckPermission(this)

        // 네비게이션 클릭 리스너
        val navigationView: NavigationView = findViewById(R.id.mainNavi)
        navigationView.setNavigationItemSelectedListener {
                menuItem ->
            menuItem.isChecked = true

            when (menuItem.itemId){
                R.id.move_addreview->{
                    Log.d("myLog","리뷰추가")
                    val intent = Intent(this, StoreAddActivity::class.java)
                    startActivity(intent)
                }
                R.id.move_myinfo->{
                    Log.d("myLog","나의정보")
                    val intent = Intent(this, UserMypageActivity::class.java)
                    startActivity(intent)
                }
                R.id.move_logout->{
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(this,"로그아웃 완료",Toast.LENGTH_SHORT).show()
                    Log.d("myLog","로그아웃")
                    val intent = Intent(this, LoginMainActivity::class.java)
                    startActivity(intent)
                }
                R.id.move_holdserch->{
                    val intent = Intent(this, GoogleMapsActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }


        // 헤더에 유저 이름을 표기하기 위해 각 변수 초기화
        myRef = MyApplication.userDb.reference
        mAuth = Firebase.auth

        //데이터 꺼내오기
        myRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //네비바 헤더 바인딩
                val header = navigationView.getHeaderView(0)
                var text1 = header.findViewById<TextView>(R.id.header_text)

                //db에서 유저정보 꺼내오기
                for (postSnapshot in snapshot.children) {

                    //현재 유저 정보 모델에 담기
                    val currentUser = postSnapshot.getValue(UserModel::class.java)

                    //현재 유저와 접속한 유저가 같을때
                    if (mAuth.currentUser?.uid == currentUser?.uId) {

                        //헤더 텍스트 부분에 이름담기
                        text1.text = currentUser?.name+"님,"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }



    // 뷰 페이저에 들어갈 아이템
    private fun getFoodList(): ArrayList<Int> {
        return arrayListOf<Int>(R.drawable.slideinfo, R.drawable.slidemypage, R.drawable.slidereview)
    }

    override fun onStart() {
        super.onStart()
        makeRecyclerView()
    }

    // 메인페이지 리뷰 리사이클러 출력
    private fun makeRecyclerView(){
        MyApplication.db.collection("news")
            .get()
            .addOnSuccessListener { result->
                val itemList= mutableListOf<ItemData>()
                for(document in result){
                    val item=document.toObject(ItemData::class.java)
                    item.uid=document.id
                    itemList.add(item)

                }
                binding.mainRecyclerView.layoutManager = GridLayoutManager(this,2)
                binding.mainRecyclerView.adapter = MyAdapter(this,itemList)
            }
            .addOnFailureListener{ exception->
                Log.d("pgm","error..getting document",exception)
                Toast.makeText(this,"서버 데이터 획득 실패..", Toast.LENGTH_SHORT).show()
            }
    }
}