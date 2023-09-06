package com.ecw.foodrankpr.heart


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecw.foodrankpr.MyApplication
import com.ecw.foodrankpr.databinding.LikeviewRecyclerviewBinding

// 좋아요 어뎁터
class MyViewHolder2(val binding: LikeviewRecyclerviewBinding): RecyclerView.ViewHolder(binding.root)

class MyAdapter2(val context: Context, val itemList: MutableList<heartModel>): RecyclerView.Adapter<MyViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder2(LikeviewRecyclerviewBinding.inflate(layoutInflater))
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    // 데이터 불러오기
    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        val data = itemList.get(position)

        holder.binding.run {
            likestorereview.text = data.storeName
            likeAddress.text = data.mapaddress
            likeTime.text = data.storeTime

        }
        val imgRef= MyApplication.storage.reference.child("images/.jpg")


    }




}