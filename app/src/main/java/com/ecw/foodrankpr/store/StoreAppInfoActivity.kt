package com.ecw.foodrankpr.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ecw.foodrankpr.databinding.ActivityAppInfoBinding

// 인포메이션 페이지
class StoreAppInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}