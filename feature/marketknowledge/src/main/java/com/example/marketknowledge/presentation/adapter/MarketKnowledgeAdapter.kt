package com.example.marketknowledge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.model.exchangerate.ExchangeRateCategory
import com.example.domain.model.exchangerate.MarketData
import com.example.ui.R
import com.example.marketknowledge.databinding.ItemMarketBinding
import java.util.Locale
import kotlin.text.format

typealias dataType = MarketData

class MarketKnowledgeAdapter :
    ListAdapter<dataType, MarketKnowledgeAdapter.ViewHolder>(CategoryDiffCallback()) {


    class ViewHolder(private val binding: ItemMarketBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: dataType, isLastItem: Boolean) {
            val trFormat = Locale("tr", "TR")


            val layoutParams = binding.viewDivider.layoutParams as ViewGroup.MarginLayoutParams

            binding.tvName.text = data.name

            if (data.category == ExchangeRateCategory.HEADER) {

                binding.tvBuy.text =
                    binding.root.context.getString(com.example.marketknowledge.R.string.market_header_buy)
                binding.tvSell.text =
                    binding.root.context.getString(com.example.marketknowledge.R.string.market_header_sell)
            } else {

                binding.tvSell.setTextColor(binding.root.context.getColor(R.color.black))
                binding.tvBuy.setTextColor(binding.root.context.getColor(R.color.black))

                binding.root.setBackgroundColor(binding.root.context.getColor(R.color.market_knowledge_item_bg_color))
                if (!isLastItem) {
                    layoutParams.setMargins(15, 0, 15, 0)
                }
                val formattedValue = if (data.buy > 100) "2f" else "3f"
                binding.tvBuy.text = String.format(trFormat, "%.${formattedValue}", data.buy)
                binding.tvSell.text = String.format(trFormat, "%.${formattedValue}", data.sell)


            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position].category){
            ExchangeRateCategory.HEADER -> ItemType.HEADER.ordinal
            else -> ItemType.NORMAL.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val isLastItem =
            if (currentList.size == position + 1) true else currentList[position + 1].category == ExchangeRateCategory.HEADER
        holder.bind(currentList[position], isLastItem)


    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<dataType>() {
        override fun areItemsTheSame(oldItem: dataType, newItem: dataType): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: dataType, newItem: dataType): Boolean {
            return oldItem.buy == newItem.buy
        }
    }

    enum class ItemType {
        HEADER,
        NORMAL
    }

}