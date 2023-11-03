package com.mikelau.shimmerxsample.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mikelau.shimmerxsample.models.ItemCard
import com.mikelau.shimmerxsample.utils.BaseUtils
import com.mikelau.shimmerxsample.viewholders.ItemHolder
import com.mikelau.shimmerxsample.viewholders.ItemHolder.Companion.newInstance

class CardAdapter : RecyclerView.Adapter<ItemHolder>() {
    private var mCards: List<ItemCard> = ArrayList()
    private var mType = BaseUtils.TYPE_LIST
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return newInstance(parent, mType)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(mCards[position])
    }

    override fun getItemCount(): Int {
        return mCards.size
    }

    fun setCards(cards: List<ItemCard>?) {
        if (cards == null) {
            return
        }
        mCards = cards
    }

    fun setType(type: Int) {
        mType = type
    }
}
