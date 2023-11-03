package com.mikelau.shimmerrecyclerviewx

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ShimmerRecyclerViewX : RecyclerView {
    enum class LayoutMangerType {
        LINEAR_VERTICAL,
        LINEAR_HORIZONTAL,
        GRID
    }

    /**
     * Retrieves the actual adapter that contains the data set or null if no adapter is set.
     *
     * @return The actual adapter
     */
    var actualAdapter: Adapter<*>? = null
        private set
    private var mShimmerAdapter: ShimmerAdapter? = null
    private var mShimmerLayoutManager: LayoutManager? = null
    private var mActualLayoutManager: LayoutManager? = null
    private var mLayoutMangerType: LayoutMangerType? = null
    private var mCanScroll = false
    var layoutReference = 0
        private set
    private var mGridCount = 0
    private var mShimmerAngle = 0
    private var mShimmerColor = 0
    private var mShimmerDuration = 0
    private var mShimmerMaskWidth = 0f
    private var isAnimationReversed = false
    private var mShimmerItemBackground: Drawable? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        mShimmerAdapter = ShimmerAdapter()
        val a = context.obtainStyledAttributes(attrs, R.styleable.ShimmerRecyclerView, 0, 0)
        try {
            setDemoLayoutReference(
                a.getResourceId(
                    R.styleable.ShimmerRecyclerView_shimmer_demo_layout,
                    R.layout.layout_sample_view
                )
            )
            setDemoChildCount(
                a.getInteger(
                    R.styleable.ShimmerRecyclerView_shimmer_demo_child_count,
                    10
                )
            )
            setGridChildCount(
                a.getInteger(
                    R.styleable.ShimmerRecyclerView_shimmer_demo_grid_child_count,
                    2
                )
            )
            val value =
                a.getInteger(R.styleable.ShimmerRecyclerView_shimmer_demo_layout_manager_type, 0)
            when (value) {
                0 -> setDemoLayoutManager(LayoutMangerType.LINEAR_VERTICAL)
                1 -> setDemoLayoutManager(LayoutMangerType.LINEAR_HORIZONTAL)
                2 -> setDemoLayoutManager(LayoutMangerType.GRID)
                else -> throw IllegalArgumentException("This value for layout manager is not valid!")
            }
            mShimmerAngle = a.getInteger(R.styleable.ShimmerRecyclerView_shimmer_demo_angle, 0)
            mShimmerColor = a.getColor(
                R.styleable.ShimmerRecyclerView_shimmer_demo_shimmer_color,
                getColor(R.color.default_shimmer_color)
            )
            mShimmerItemBackground =
                a.getDrawable(R.styleable.ShimmerRecyclerView_shimmer_demo_view_holder_item_background)
            mShimmerDuration =
                a.getInteger(R.styleable.ShimmerRecyclerView_shimmer_demo_duration, 1500)
            mShimmerMaskWidth =
                a.getFloat(R.styleable.ShimmerRecyclerView_shimmer_demo_mask_width, 0.5f)
            isAnimationReversed =
                a.getBoolean(R.styleable.ShimmerRecyclerView_shimmer_demo_reverse_animation, false)
        } finally {
            a.recycle()
        }
        setupShimmerProperties()
    }

    private fun setupShimmerProperties() {
        mShimmerAdapter?.setShimmerAngle(mShimmerAngle)
        mShimmerAdapter?.setShimmerColor(mShimmerColor)
        mShimmerAdapter?.setShimmerMaskWidth(mShimmerMaskWidth)
        mShimmerAdapter?.setShimmerItemBackground(mShimmerItemBackground)
        mShimmerAdapter?.setShimmerDuration(mShimmerDuration)
        mShimmerAdapter?.setAnimationReversed(isAnimationReversed)
    }

    fun setBindViewHolderPlugin(plugin: BindViewHolderPlugin?) {
        mShimmerAdapter?.setBindViewHolderPlugin(plugin)
    }

    /**
     * Specifies the number of child should exist in any row of the grid layout.
     *
     * @param count - count specifying the number of child.
     */
    fun setGridChildCount(count: Int) {
        mGridCount = count
    }

    /**
     * Sets the layout manager for the shimmer adapter.
     *
     * @param type layout manager reference
     */
    fun setDemoLayoutManager(type: LayoutMangerType?) {
        mLayoutMangerType = type
    }

    /**
     * Sets the number of demo views should be shown in the shimmer adapter.
     *
     * @param count - number of demo views should be shown.
     */
    fun setDemoChildCount(count: Int) {
        mShimmerAdapter?.setMinItemCount(count)
    }

    /**
     * Specifies the animation duration of shimmer layout.
     *
     * @param duration - count specifying the duration of shimmer in millisecond.
     */
    fun setDemoShimmerDuration(duration: Int) {
        mShimmerAdapter?.setShimmerDuration(duration)
    }

    /**
     * Specifies the the width of the shimmer line.
     *
     * @param maskWidth - float specifying the width of shimmer line. The value should be from 0 to less or equal to 1.
     * The default value is 0.5.
     */
    fun setDemoShimmerMaskWidth(maskWidth: Float) {
        mShimmerAdapter?.setShimmerMaskWidth(maskWidth)
    }

    /**
     * Sets the shimmer adapter and shows the loading screen.
     */
    fun showShimmerAdapter() {
        mCanScroll = false
        if (mShimmerLayoutManager == null) {
            initShimmerManager()
        }
        setLayoutManager(mShimmerLayoutManager)
        setAdapter(mShimmerAdapter)
    }

    /**
     * Hides the shimmer adapter
     */
    fun hideShimmerAdapter() {
        mCanScroll = true
        setLayoutManager(mActualLayoutManager)
        setAdapter(actualAdapter)
    }

    override fun setLayoutManager(manager: LayoutManager?) {
        if (manager == null) {
            mActualLayoutManager = null
        } else if (manager !== mShimmerLayoutManager) {
            mActualLayoutManager = manager
        }
        super.setLayoutManager(manager)
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        if (adapter == null) {
            actualAdapter = null
        } else if (adapter !== mShimmerAdapter) {
            actualAdapter = adapter
        }
        super.setAdapter(adapter)
    }

    val shimmerAdapter: Adapter<*>?
        get() = mShimmerAdapter

    /**
     * Sets the demo layout reference
     *
     * @param mLayoutReference layout resource id of the layout which should be shown as demo.
     */
    fun setDemoLayoutReference(mLayoutReference: Int) {
        layoutReference = mLayoutReference
        mShimmerAdapter?.setLayoutReference(layoutReference)
    }

    private fun initShimmerManager() {
        if (mLayoutMangerType == LayoutMangerType.LINEAR_VERTICAL) {
            mShimmerLayoutManager =
                object : LinearLayoutManager(
                    context
                ) {
                    override fun canScrollVertically(): Boolean {
                        return mCanScroll
                    }
                }
        } else if (mLayoutMangerType == LayoutMangerType.LINEAR_HORIZONTAL) {
            mShimmerLayoutManager =
                object : LinearLayoutManager(
                    context, HORIZONTAL, false
                ) {
                    override fun canScrollHorizontally(): Boolean {
                        return mCanScroll
                    }
                }
        } else if (mLayoutMangerType == LayoutMangerType.GRID) {
            mShimmerLayoutManager = object : GridLayoutManager(
                context, mGridCount
            ) {
                override fun canScrollVertically(): Boolean {
                    return mCanScroll
                }
            }
        }
    }

    private fun getColor(id: Int): Int {
        return context.getColor(id)
    }
}
