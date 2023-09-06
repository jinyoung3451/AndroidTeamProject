package com.ecw.foodrankpr

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecw.foodrankpr.store.ItemData
import com.ecw.foodrankpr.databinding.ItemRecyclerviewBinding
import com.ecw.foodrankpr.store.StoreDetailActivity

// 메인 페이지 뷰사이클러 어뎁터
class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val itemList: MutableList<ItemData>): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(ItemRecyclerviewBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = itemList.get(position)
        holder.binding.run {
            shopname1.text=data.storeName
            point1.text=data.ratingbar
            like1.text=data.likePoint.toString()
        }

        holder.binding.imageview1.setOnClickListener {
            val intent = Intent(context, StoreDetailActivity::class.java)

            intent.putExtra("name",data.name)
            intent.putExtra("storeName",data.storeName)
            intent.putExtra("ratingbar",data.ratingbar)
            intent.putExtra("reviewText",data.reviewText)
            intent.putExtra("storeTime",data.storeTime)
            intent.putExtra("mapaddress",data.mapaddress)
            intent.putExtra("storeCategory",data.storeCategory)
            intent.putExtra("id",data.uid)


            intent.toString()
            context.startActivity(intent)
        }

        //스토리지 이미지 다운로드........................
        val imgRef = MyApplication.storage.reference.child("images/${data.uid}.jpg")
        imgRef.downloadUrl.addOnCompleteListener {tast->
            Glide.with(context)
                .load(tast.result)
                .into(holder.binding.imageview1)
        }
    }
}