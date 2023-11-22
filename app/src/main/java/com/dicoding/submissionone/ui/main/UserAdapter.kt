package com.dicoding.submissionone.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.submissionone.data.database.Note
import com.dicoding.submissionone.data.response.ItemsItem
import com.dicoding.submissionone.databinding.ItemRowBinding
import com.dicoding.submissionone.ui.detail.DetailActivity

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val listUsers = ArrayList<Any>()

    fun setUsers(users: List<Any>?) {
        if (users == null) return
        this.listUsers.clear()
        this.listUsers.addAll(users)
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun  getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (val list = listUsers[position]) {
            is ItemsItem -> {
                holder.binding.tvItem.text = list.login
                Glide.with(holder.itemView.context)
                    .load(list.avatarUrl)
                    .circleCrop()
                    .into(holder.binding.imgitemphoto)
                holder.itemView.setOnClickListener {
                    val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.EXTRA_USER, list.login)
                    intentDetail.putExtra(DetailActivity.EXTRA_ID, list.id)
                    intentDetail.putExtra(DetailActivity.EXTRA_URL, list.avatarUrl)
                    holder.itemView.context.startActivity(intentDetail)
                }
            }

            is Note -> {
                holder.binding.tvItem.text = list.login
                Glide.with(holder.itemView.context)
                    .load(list.avatarUrl)
                    .circleCrop()
                    .into(holder.binding.imgitemphoto)
                holder.itemView.setOnClickListener {
                    val intentDetail =
                        Intent(holder.itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.EXTRA_USER, list.login)
                    intentDetail.putExtra(DetailActivity.EXTRA_ID, list.id)
                    intentDetail.putExtra(DetailActivity.EXTRA_URL, list.avatarUrl)
                    holder.itemView.context.startActivity(intentDetail)
                }
            }
        }
    }
}
