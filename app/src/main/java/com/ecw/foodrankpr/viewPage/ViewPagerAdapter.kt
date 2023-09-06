package com.ecw.foodrankpr.viewPage

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ecw.foodrankpr.*
import com.ecw.foodrankpr.store.StoreAddActivity
import com.ecw.foodrankpr.store.StoreAppInfoActivity
import com.ecw.foodrankpr.user.UserMypageActivity

// 이미지 슬라이드 어뎁터
class ViewPagerAdapter(context1: Context,foodList: ArrayList<Int>) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {
    var context: Context
    var item = foodList

    init {
        context=context1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PagerViewHolder((parent))

    override fun getItemCount(): Int = item.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {

        holder.food.setImageResource(item[position])
        //이미지 슬라이드 페이지 이동
        holder.food.setOnClickListener{
            Log.d("abc", "${position}")
            if(position==0) {
                val intent = Intent(context, StoreAppInfoActivity::class.java)
                context.startActivity(intent)
            } else if(position==1) {
                val intent = Intent(context, UserMypageActivity::class.java)
                context.startActivity(intent)
            } else if (position==2) {
                val intent = Intent(context, StoreAddActivity::class.java)
                context.startActivity(intent)
            } else {
                Log.d("error", "error")
            }
        }

    }

    inner class PagerViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.image_list_item, parent, false)){
        val food = itemView.findViewById<ImageView>(R.id.imageView_food)
    }
}