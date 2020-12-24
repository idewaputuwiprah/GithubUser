package com.dicoding.githubuser.following

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.githubuser.R
import com.dicoding.githubuser.retrofit.UserResponse
import kotlinx.android.synthetic.main.item_detail.view.*

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {
    private val followingData = ArrayList<UserResponse>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(item: UserResponse){
            with(itemView) {
                tv_name_detail.text = item.login
                Glide.with(context)
                    .load(item.avatar_url)
                    .centerCrop()
                    .into(img_user_detail)
            }
        }
    }

    fun setData(items: ArrayList<UserResponse>) {
        followingData.clear()
        followingData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(followingData[position])
    }

    override fun getItemCount(): Int {
        return followingData.size
    }
}