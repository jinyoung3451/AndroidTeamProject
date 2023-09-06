package com.ecw.foodrankpr.comment

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecw.foodrankpr.databinding.CommentRecyclerviewBinding

// 댓글 어뎁터
class MyCViewHolder(val binding: CommentRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class commenAdapter(val context: Context, val commList: MutableList<commenModel>):RecyclerView.Adapter<MyCViewHolder> (){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyCViewHolder(CommentRecyclerviewBinding.inflate(layoutInflater))
    }

    override fun getItemCount(): Int {
        return commList.size
    }

    //댓글 데이터 가져오기
    override fun onBindViewHolder(holder: MyCViewHolder, position: Int) {
        val data = commList.get(position)
        holder.binding.run {
                commentNick.text = data.name
                CommentTextView.text = data.comment
        }
    }
}