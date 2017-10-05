package com.example.goodluck.a4u.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Endless scroll listener. Should be used with {@link GridLayoutManager} or {@link LinearLayoutManager}
 */

public abstract class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = EndlessRecyclerScrollListener.class.getSimpleName();

    /**
     * Minimum number of items before loading
     */
    private static final long VISIBLE_THRESHOLD = 6;

    /**
     * Total number of loaded items
     */
    private int previousTotal = 0;

    /**
     * Inform about data loading. True if still waiting for data to load
     */
    private boolean loading = true;

    /**
     * Total number of loading calls
     */
    private int current_page = 1;

    // Local temporary properties
    private int firstVisibleItem, visibleItemCount, totalItemCount;

    // Possible layout managers
    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;

    /**
     * Create endless scroll listener for {@link RecyclerView}
     *
     * @param gridLayoutManager Specifies {@link GridLayoutManager} for processing
     */
    public EndlessRecyclerScrollListener(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    /**
     * Create endless scroll listener for {@link RecyclerView}
     *
     * @param linearLayoutManager Specifies {@link LinearLayoutManager}
     */
    public EndlessRecyclerScrollListener(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        if (null != gridLayoutManager) {
            totalItemCount = gridLayoutManager.getItemCount();
            firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
        } else {
            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
        }

        if (loading && totalItemCount != previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            // End has been reached
            current_page++;
            Log.d(TAG, "Load more");
            onLoadMore(current_page);
            loading = true;
        }
    }

    /**
     * Method clean scroll listener. Fresh Start
     */
    public void clean() {
        previousTotal = 0;
        loading = true;
        firstVisibleItem = 0;
        visibleItemCount = 0;
        totalItemCount = 0;
        current_page = 1;
    }

    /**
     * Method enable new call for {@link #onLoadMore} method, when loading was cancelled
     */
    public void resetLoading() {
        loading = false;
    }

    /**
     * Method indicate that the end has been reached
     *
     * @param current_page total number of loading calls
     */
    public abstract void onLoadMore(int current_page);
}
