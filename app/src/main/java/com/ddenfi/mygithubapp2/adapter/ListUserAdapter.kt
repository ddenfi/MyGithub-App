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

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.UserViewHolder>() {
    private lateinit var onItemClickCallback: OnItemCLickCallBack
    private val listUser: ArrayList<DetailUser> = ArrayList()

    fun setOnItemClickCallBack(onItemClickCallback: OnItemCLickCallBack) {
        this.onItemClickCallback = onItemClickCallback
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DetailUser>) {
        listUser.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        var tvUsername: TextView = itemView.findViewById(R.id.tv_item_username)
        var tvType: TextView = itemView.findViewById(R.id.tv_item_type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val list = listUser[position]
        Glide.with(holder.itemView.context)
            .load(list.avatarUrl)
            .circleCrop()
            .into(holder.imgPhoto)
        holder.tvUsername.text = list.login
        holder.tvType.text = list.type
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemCLickCallBack {
        fun onItemClicked(user: DetailUser)
    }

}