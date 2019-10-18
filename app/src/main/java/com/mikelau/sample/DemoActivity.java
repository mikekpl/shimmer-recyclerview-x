/**
 * Copyright 2017 Harish Sridharan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mikelau.sample;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mikelau.sample.adapters.CardAdapter;
import com.mikelau.sample.utils.BaseUtils;
import com.mikelau.sample.utils.DemoConfiguration;
import com.mikelau.views.shimmer.BindViewHolderPlugin;
import com.mikelau.views.shimmer.ShimmerRecyclerViewX;

public class DemoActivity extends AppCompatActivity {

    public static final String EXTRA_TYPE = "type";

    private ShimmerRecyclerViewX shimmerRecycler;
    private CardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int type = getType();

        RecyclerView.LayoutManager layoutManager;

        final DemoConfiguration demoConfiguration = BaseUtils.getDemoConfiguration(type, this);
        setTheme(demoConfiguration.getStyleResource());
        setContentView(demoConfiguration.getLayoutResource());
        layoutManager = demoConfiguration.getLayoutManager();
        setTitle(demoConfiguration.getTitleResource());

        shimmerRecycler = findViewById(R.id.shimmer_recycler_view);

        if (demoConfiguration.getItemDecoration() != null) {
            shimmerRecycler.addItemDecoration(demoConfiguration.getItemDecoration());
        }

        mAdapter = new CardAdapter();
        mAdapter.setType(type);

        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setAdapter(mAdapter);
        shimmerRecycler.showShimmerAdapter();

        BindViewHolderPlugin plugin = new BindViewHolderPlugin() {
            @Override
            public void hookToOnBindViewHolder(View rootView) {
                Log.d(getClass().getSimpleName(), "hookToOnBindViewHolder");
                resizeCellAtRuntime(rootView);
            }

            @Override
            public void hookToShimmerLayout(View shimmerLayout) {
                resizeCellAtRuntime(shimmerLayout);
            }
        };

        shimmerRecycler.setBindViewHolderPlugin(plugin);

        shimmerRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCards();
            }
        }, 3000);
    }

    private void resizeCellAtRuntime(View rootView) {
        if (rootView instanceof LinearLayout) {

            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            //we need to display 1 and the half of the cell
            double roundUpOffset = 0.5;
            int oneAndAHalfOfACell = ((int) ((displaymetrics.widthPixels / 1.5f) + roundUpOffset));

            rootView.getLayoutParams().width = oneAndAHalfOfACell;
        }
    }

    private void loadCards() {
        int type = getType();

        mAdapter.setCards(BaseUtils.getCards(getResources(), type));
        shimmerRecycler.hideShimmerAdapter();
    }

    private int getType() {
        return getIntent().getIntExtra(EXTRA_TYPE, BaseUtils.TYPE_LIST);
    }
}
