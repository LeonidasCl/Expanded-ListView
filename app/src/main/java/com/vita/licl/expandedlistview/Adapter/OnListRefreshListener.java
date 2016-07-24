package com.vita.licl.expandedlistview.Adapter;

/**
 * Created by pc on 2016/7/24.
 */
public interface OnListRefreshListener {

    /**
     * 下拉刷新
     */
    void onDownPullRefresh();

    /**
     * 上拉加载更多
     */
    void onLoadingMore();
}
