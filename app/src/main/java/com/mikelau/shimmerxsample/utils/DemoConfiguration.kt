package com.mikelau.shimmerxsample.utils

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.recyclerview.widget.RecyclerView

class DemoConfiguration {

    @StyleRes
    var styleResource = 0

    @LayoutRes
    var layoutResource = 0

    @StringRes
    var titleResource = 0

    var layoutManager: RecyclerView.LayoutManager? = null
    var itemDecoration: RecyclerView.ItemDecoration? = null

}
