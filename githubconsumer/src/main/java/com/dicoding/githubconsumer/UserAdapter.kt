package com.dicoding.githubconsumer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_users.view.*

class UserAdapter: RecyclerView.Adapter<UserAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var users = ArrayList<UserModel>()

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: UserModel){
            with(itemView) {
                tv_name.text = item.name
                tv_username.text = item.username
                val avatar = item.avatar_int ?: item.avatar_string
                Glide.with(context)
                    .load(avatar)
                    .apply(RequestOptions().override(55,55))
                    .into(img_user)

                itemView.setOnClickListener { onItemClickCallback.onItemClick(item) }
            }
        }
    }

    fun setData(items: ArrayList<UserModel>) {
        users.clear()
        users.addAll(items)
        notifyDataSetChanged()
    }

    fun setItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_users, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(users[position])
    }

    interface OnItemClickCallback {
        fun onItemClick(data: UserModel){

        }
    }
}