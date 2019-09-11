package com.aula.mitap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aula.mitap.databinding.ItemMessageBinding

class ListChat(
    var list: MutableList<Message>
) : RecyclerView.Adapter<ItemMessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMessageViewHolder {
        val binding = DataBindingUtil.inflate<ItemMessageBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_message,
            parent,
            false
        )
        return ItemMessageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    override fun onBindViewHolder(holder: ItemMessageViewHolder, position: Int) {
        holder.binding.message = list[position]
    }
}