package com.ecw.foodrankpr.store

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log

import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

import com.ecw.foodrankpr.store.StoreAddressApiActivity.Companion.REQUEST_CODE
import com.ecw.foodrankpr.MyApplication
import com.ecw.foodrankpr.databinding.ActivityAddBinding
import com.ecw.foodrankpr.user.UserModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

import java.io.File

//리뷰 추가 페이지
class StoreAddActivity : AppCompatActivity() {
    
    lateinit var binding: ActivityAddBinding
    lateinit var filePath: String
    lateinit var mAuth: FirebaseAuth
    private lateinit var myRef:DatabaseReference
    lateinit var currentUser: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //별점
        binding.ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser  ->
            binding.ratingBarScore.text = "${rating}점"
        }


        //주소찾기 페이지 이동
        binding.mapSearchBtn.setOnClickListener {
            val intent = Intent(this, StoreAddressApiActivity::class.java)
            startActivityForResult(intent,REQUEST_CODE)
        }

        //이미지 선택 페이지 이동
        binding.photoBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            requestLauncher.launch(intent)
        }


        // ImageUpload 완료시 내용 저장 가능
        binding.addBtn.setOnClickListener {
            if(binding.imageText1.text.isEmpty()) {
                Toast.makeText(this, "이미지를 추가해주세요", Toast.LENGTH_SHORT).show()
            } else {
                saveStore()
            }
        }

        mAuth = Firebase.auth
        myRef = MyApplication.userDb.reference
        myRef.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val currentUser1 = postSnapshot.getValue(UserModel::class.java)
                    Log.d("mylog","${currentUser1}")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE ->{
                if (resultCode== RESULT_OK){
                    var addressData = data?.extras?.getString("dataAdr")
                    Log.d("meLog2","${addressData}")
                    binding.mapEt.setText(addressData)
                }
            }
        }
    }

    // 이미지 선택시 텍스트에 저장
    val requestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    {
        if(it.resultCode === android.app.Activity.RESULT_OK){
            val cursor = contentResolver.query(it.data?.data as Uri,
                arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
            cursor?.moveToFirst().let {
                filePath=cursor?.getString(0) as String
                binding.imageText1.text = filePath
            }
        }
    }

    private fun saveStore(){
        // Firestore DB에 저장
        var data= mapOf(
            "name" to currentUser.name,
            "storeName" to binding.storeName.text.toString(),
            "storeCategory" to binding.storeCategoryEditText.text.toString(),
            "storeTime" to binding.storeTime.text.toString(),
            "mapaddress" to binding.mapEt.text.toString(),
            "ratingbar" to binding.ratingBar.rating.toString(),
            "reviewText" to binding.reviewtext.text.toString(),
            "likePoint" to 0

        )
        MyApplication.db.collection("news")
            .add(data)
            .addOnSuccessListener {
                uploadImage(it.id)
            }
            .addOnFailureListener{
                Log.d("mylog","리뷰추가 db 저장 실패",it)
            }
    }


    // 업로드 이미지 코드
    private fun uploadImage(uid: String){
        //add............................
        val storage= MyApplication.storage
        val storageRef=storage.reference
        val imgRef = storageRef.child("images/${uid}.jpg")
        val file = Uri.fromFile(File(filePath))
        imgRef.putFile(file)//파일 업로드
            .addOnSuccessListener {
                Toast.makeText(this,"save ok", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener{
                Log.d("pgm","file save error",it)
            }
    }

}
