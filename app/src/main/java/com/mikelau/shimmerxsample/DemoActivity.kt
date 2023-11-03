package com.mikelau.shimmerxsample

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikelau.shimmerrecyclerviewx.BindViewHolderPlugin
import com.mikelau.shimmerrecyclerviewx.ShimmerRecyclerViewX
import com.mikelau.shimmerxsample.adapters.CardAdapter
import com.mikelau.shimmerxsample.utils.BaseUtils
import com.mikelau.shimmerxsample.utils.DemoConfiguration

class DemoActivity : AppCompatActivity() {
    private var shimmerRecycler: ShimmerRecyclerViewX? = null
    private var mAdapter: CardAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = type
        val layoutManager: RecyclerView.LayoutManager
        val demoConfiguration: DemoConfiguration = BaseUtils.getDemoConfiguration(type, this)!!
        setTheme(demoConfiguration.styleResource)
        setContentView(demoConfiguration.layoutResource)
        layoutManager = demoConfiguration.layoutManager!!
        setTitle(demoConfiguration.titleResource)
        shimmerRecycler = findViewById(R.id.shimmer_recycler_view)
        if (demoConfiguration.itemDecoration != null) {
            shimmerRecycler?.addItemDecoration(demoConfiguration.itemDecoration!!)
        }
        mAdapter = CardAdapter()
        mAdapter?.setType(type)
        if (type == 4) {
            shimmerRecycler?.setDemoLayoutManager(ShimmerRecyclerViewX.LayoutMangerType.LINEAR_HORIZONTAL)
        }
        shimmerRecycler?.setLayoutManager(
            if (type == 4) LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            ) else layoutManager
        )
        shimmerRecycler?.setAdapter(mAdapter)
        shimmerRecycler?.showShimmerAdapter()
        val plugin: BindViewHolderPlugin = object : BindViewHolderPlugin {
            override fun hookToOnBindViewHolder(rootView: View?) {
                Log.d(javaClass.simpleName, "hookToOnBindViewHolder")
                resizeCellAtRuntime(rootView)
            }

            override fun hookToShimmerLayout(shimmerLayout: View?) {
                resizeCellAtRuntime(shimmerLayout)
            }
        }
        shimmerRecycler?.setBindViewHolderPlugin(plugin)
        shimmerRecycler?.postDelayed(Runnable { loadCards() }, 3000)
    }

    private fun resizeCellAtRuntime(rootView: View?) {
        if (rootView is LinearLayout) {
            val displaymetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displaymetrics)
            //we need to display 1 and the half of the cell
            val roundUpOffset = 0.5
            val oneAndAHalfOfACell = (displaymetrics.widthPixels / 1.5f + roundUpOffset).toInt()
            rootView.getLayoutParams().width = oneAndAHalfOfACell
        }
    }

    private fun loadCards() {
        val type = type
        mAdapter?.setCards(BaseUtils.getCards(getResources(), type))
        shimmerRecycler?.hideShimmerAdapter()
    }

    private val type: Int
        get() = intent.getIntExtra(EXTRA_TYPE, BaseUtils.TYPE_LIST)

    companion object {
        const val EXTRA_TYPE = "type"
    }
}
