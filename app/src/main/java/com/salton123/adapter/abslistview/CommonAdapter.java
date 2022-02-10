package com.salton123.adapter.abslistview;

import android.content.Context;

import com.salton123.adapter.abslistview.base.ItemViewDelegate;

import org.jetbrains.annotations.NotNull;

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {

    public CommonAdapter(Context context, final int layoutId) {
        super(context);
        addItemViewDelegate(new ItemViewDelegate<T>() {

            @Override
            public int getItemViewLayoutId(int position) {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position) {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position) {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(@NotNull ViewHolder viewHolder, @NotNull T item, int position);

}
