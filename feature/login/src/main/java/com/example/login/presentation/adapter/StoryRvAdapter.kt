package com.example.login.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.login.databinding.ItemStoryBinding

class StoryRvAdapter(private val list: List<String>,  val onclick:(String)->Unit): RecyclerView.Adapter<StoryRvAdapter.LoginViewHolder>() {


    inner class LoginViewHolder(val binding: ItemStoryBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.tvStoryName.text=item
            //binding.cvStoryImage
            binding.root.setOnClickListener {
                onclick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginViewHolder {
        val binding=ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoginViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: LoginViewHolder, position: Int) {
        holder.bind(list[position])
    }




}