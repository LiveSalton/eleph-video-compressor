//package com.salton123.eleph.video.compressor.adapter
//
//import android.content.Context
//import com.salton123.adapter.abslistview.MultiItemTypeAdapter
//import com.salton123.adapter.abslistview.ViewHolder
//import com.salton123.adapter.abslistview.base.ItemViewDelegate
//import com.salton123.eleph.video.compressor.model.IMultiType
//
///**
// * Time:2022/1/29 4:40 上午
// * Author:
// * Description:
// */
//class VideoListAdapterStyle(context: Context) : MultiItemTypeAdapter<IMultiType>(context) {
//    init {
//        addItemViewDelegate(object : ItemViewDelegate<IMultiType> {
//            override fun isForViewType(item: IMultiType?, position: Int): Boolean {
//            }
//
//            override fun convert(holder: ViewHolder?, t: IMultiType?, position: Int) {
//            }
//
//            override fun getItemViewLayoutId(position: Int): Int {
//                return getItemViewType(position)
//            }
//        })
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return super.getItemViewType(position)
//    }
//}