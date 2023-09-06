package com.ecw.foodrankpr

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


// DB 불러오기 어플리케이션
class MyApplication : MultiDexApplication() {

    companion object{
        lateinit var mAuth: FirebaseAuth
        var email:String?=null
        var naㅅme:String?=null
        lateinit var userDb: FirebaseDatabase
        lateinit var storage: FirebaseStorage
        lateinit var db : FirebaseFirestore
    }

    fun checkAuth():Boolean{
        var currentUser= mAuth.currentUser
        return currentUser?.let {
            email=currentUser.email
            currentUser.isEmailVerified
        }?: let {
            false
        }
    }

    override fun onCreate() {
        super.onCreate()
        mAuth=Firebase.auth
        userDb=FirebaseDatabase.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }

}