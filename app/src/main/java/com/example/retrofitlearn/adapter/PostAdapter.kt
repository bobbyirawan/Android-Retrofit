package com.example.retrofitlearn.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitlearn.databinding.ItemMainactivityBinding
import com.example.retrofitlearn.model.posts.Posts

class PostAdapter : RecyclerView.Adapter<PostAdapter.MyViewHolder>() {

    private var list = Posts()

    inner class MyViewHolder(binding: ItemMainactivityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val userId = binding.tvUserId
        val id = binding.tvId
        val title = binding.tvTitle
        val body = binding.tvBody
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemMainactivityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.userId.text = list[position].userId.toString()
            holder.id.text = list[position].id.toString()
            holder.title.text = list[position].title
            holder.body.text = list[position].body
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(newList: Posts) {
        list = newList
        notifyDataSetChanged()
    }

}