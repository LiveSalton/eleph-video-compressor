package com.salton123.adapter.abslistview.base;

import com.salton123.adapter.abslistview.ViewHolder;

public interface ItemViewDelegate<T> {

    int getItemViewLayoutId(int position);

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
