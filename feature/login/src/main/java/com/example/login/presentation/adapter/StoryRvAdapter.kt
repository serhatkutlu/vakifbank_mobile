package com.example.login.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.story.StoryUiData
import com.example.login.databinding.ItemStoryBinding
import com.example.ui.extensions.extension.loadImageWithGlide

class StoryRvAdapter(
    private var list: List<StoryUiData> = listOf(),
    val onclick: (Int) -> Unit
) : RecyclerView.Adapter<StoryRvAdapter.LoginViewHolder>() {


    fun updateList(list: List<StoryUiData>) {
        this.list = list
        notifyDataSetChanged()
    }

     class LoginViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoryUiData,onclick: () -> Unit) {
            binding.tvStoryName.text = item.title
            binding.ivStoryImage.loadImageWithGlide(item.iconUrl)
            binding.root.setOnClickListener {
                onclick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoginViewHolder {
        val binding = ItemStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoginViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: LoginViewHolder, position: Int) {
        holder.bind(list[position]){
            onclick(position)
        }
    }


}