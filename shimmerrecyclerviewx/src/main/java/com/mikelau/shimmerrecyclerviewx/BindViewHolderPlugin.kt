package com.mikelau.shimmerrecyclerviewx

import android.view.View

interface BindViewHolderPlugin {
    fun hookToOnBindViewHolder(rootView: View?)
    fun hookToShimmerLayout(shimmerLayout: View?)
}