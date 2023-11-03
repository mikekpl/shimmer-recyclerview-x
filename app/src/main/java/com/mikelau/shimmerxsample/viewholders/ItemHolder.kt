package com.mikelau.shimmerxsample.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mikelau.shimmerxsample.R
import com.mikelau.shimmerxsample.models.ItemCard
import com.mikelau.shimmerxsample.utils.BaseUtils

class ItemHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val mTitleView: TextView
    private val mDescView: TextView
    private val mThumbnailView: ImageView
    private val mSummaryView: TextView

    init {
        mTitleView = itemView.findViewById(R.id.card_title)
        mDescView = itemView.findViewById(R.id.card_subtitle)
        mSummaryView = itemView.findViewById(R.id.card_summary)
        mThumbnailView = itemView.findViewById(R.id.card_image)
    }

    fun bind(card: ItemCard) {
        mTitleView.text = card.title
        mDescView.text = card.description
        mSummaryView.text = card.summaryText
        mThumbnailView.load(card.thumbnailUrl)
    }

    companion object {
        @JvmStatic
        fun newInstance(container: ViewGroup, type: Int): ItemHolder {
            val root = LayoutInflater.from(container.context).inflate(
                getLayoutResourceId(type),
                container, false
            )
            return ItemHolder(root)
        }

        private fun getLayoutResourceId(type: Int): Int {
            val selectedLayoutResource: Int = when (type) {
                BaseUtils.TYPE_LIST, BaseUtils.TYPE_LIST_HORIZONTAL -> R.layout.layout_news_card
                BaseUtils.TYPE_SECOND_LIST -> R.layout.layout_second_news_card
                BaseUtils.TYPE_GRID, BaseUtils.TYPE_SECOND_GRID -> R.layout.layout_ecom_item
                else -> 0
            }
            return selectedLayoutResource
        }
    }
}
