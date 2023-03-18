package com.ddenfi.mygithubapp2.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ddenfi.mygithubapp2.R
import com.ddenfi.mygithubapp2.database.DetailUser

class ListFollowAdapter(private val listUser: ArrayList<DetailUser> = ArrayList()) :
    RecyclerView.Adapter<ListFollowAdapter.FollowViewHolder>() {

    class FollowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        var tvType: TextView = itemView.findViewById(R.id.tv_item_type)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<DetailUser>) {
        this.listUser.clear()
        this.listUser.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return FollowViewHolder(view)
    }

    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        val list = listUser[position]
        Glide.with(holder.itemView.context)
            .load(list.avatarUrl)
            .circleCrop()
            .into(holder.imgPhoto)
        holder.tvUsername.text = list.login
        holder.tvType.text = list.type
    }

    override fun getItemCount(): Int = listUser.size
}