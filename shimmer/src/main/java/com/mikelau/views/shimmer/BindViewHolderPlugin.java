package com.mikelau.views.shimmer;

import android.view.View;

public interface BindViewHolderPlugin {
    void hookToOnBindViewHolder(View rootView);

    void hookToShimmerLayout(View shimmerLayout);
}
