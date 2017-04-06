package com.example.leszek.simpledagger2mvp.presentation.users.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Leszek Janiszewski
 */


public final class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private int firstVisibleItemPosition;
    private int visibleItemCount;
    private int totalItemCount;
    private boolean loading = true;
    private LinearLayoutManager linearLayoutManager;
    private OnLoadMoreListener onLoadMoreListener;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager, OnLoadMoreListener onLoadMoreListener) {
        this.linearLayoutManager = linearLayoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        visibleItemCount = recyclerView.getChildCount();
        totalItemCount = linearLayoutManager.getItemCount();
        firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItemPosition + visibleThreshold)) {
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore();
                loading = true;
            }
        }
    }

    public void reset(int previousTotal, boolean loading) {
        this.previousTotal = previousTotal;
        this.loading = loading;
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}