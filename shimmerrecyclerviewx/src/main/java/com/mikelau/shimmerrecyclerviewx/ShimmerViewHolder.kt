package com.mikelau.shimmerrecyclerviewx

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.supercharge.shimmerlayout.ShimmerLayout

class ShimmerViewHolder(inflater: LayoutInflater, parent: ViewGroup?, innerViewResId: Int) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.viewholder_shimmer, parent, false)) {
    private val mShimmerLayout: ShimmerLayout = itemView as ShimmerLayout

    init { inflater.inflate(innerViewResId, mShimmerLayout, true) }

    fun setShimmerAngle(angle: Int) = mShimmerLayout.setShimmerAngle(angle)
    fun setShimmerColor(color: Int) = mShimmerLayout.setShimmerColor(color)
    fun setShimmerMaskWidth(maskWidth: Float) = mShimmerLayout.setMaskWidth(maskWidth)
    fun setShimmerViewHolderBackground(viewHolderBackground: Drawable?) = viewHolderBackground?.let { setBackground(it) }
    fun setShimmerAnimationDuration(duration: Int) = mShimmerLayout.setShimmerAnimationDuration(duration)
    fun setAnimationReversed(animationReversed: Boolean) = mShimmerLayout.setAnimationReversed(animationReversed)
    fun getShimmerLayout(): ShimmerLayout = mShimmerLayout
    fun bind() = mShimmerLayout.startShimmerAnimation()

    private fun setBackground(background: Drawable) {
        mShimmerLayout.background = background
    }
}
