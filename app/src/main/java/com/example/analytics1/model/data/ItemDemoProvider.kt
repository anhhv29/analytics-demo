package com.example.analytics1.model.data

import com.example.analytics1.R
import com.example.analytics1.enums.TypeData
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.util.Constants.TypeData.Companion.ADS
import com.example.analytics1.util.Constants.TypeData.Companion.FREE

class ItemDemoProvider {
    fun getListInterstitialDemo(): MutableList<ItemDemoModel> {
        val mList: MutableList<ItemDemoModel> = mutableListOf()
        mList.add(ItemDemoModel(1, R.drawable.ic_item_1, FREE))
        mList.add(ItemDemoModel(2, R.drawable.ic_item_2, FREE))
        mList.add(ItemDemoModel(3, R.drawable.ic_item_3, FREE))
        mList.add(ItemDemoModel(4, R.drawable.ic_item_4, FREE))
        mList.add(ItemDemoModel(5, R.drawable.ic_item_5, FREE))
        mList.add(ItemDemoModel(6, R.drawable.ic_item_6, FREE))
        mList.add(ItemDemoModel(7, R.drawable.ic_item_7, FREE))
        mList.add(ItemDemoModel(8, R.drawable.ic_item_8, FREE))
        mList.add(ItemDemoModel(9, R.drawable.ic_item_9, ADS))
        return mList
    }

    fun getListRewardedDemo(): MutableList<ItemDemoModel> {
        val mList: MutableList<ItemDemoModel> = mutableListOf()
        mList.add(ItemDemoModel(1, R.drawable.ic_item_1, TypeData.FREE.name))
        mList.add(ItemDemoModel(2, R.drawable.ic_item_2, TypeData.FREE.name))
        mList.add(ItemDemoModel(3, R.drawable.ic_item_3, TypeData.ADS.name))
        mList.add(ItemDemoModel(4, R.drawable.ic_item_4, TypeData.ADS.name))
        mList.add(ItemDemoModel(5, R.drawable.ic_item_5, TypeData.FREE.name))
        mList.add(ItemDemoModel(6, R.drawable.ic_item_6, TypeData.ADS.name))
        mList.add(ItemDemoModel(7, R.drawable.ic_item_7, TypeData.FREE.name))
        mList.add(ItemDemoModel(8, R.drawable.ic_item_8, TypeData.ADS.name))
        mList.add(ItemDemoModel(9, R.drawable.ic_item_9, TypeData.FREE.name))
        return mList
    }

    fun getListNativeInlineDemo(): MutableList<ItemDemoModel>{
        val mList: MutableList<ItemDemoModel> = mutableListOf()
        mList.add(ItemDemoModel(1, R.drawable.ic_item_1, FREE))
        mList.add(ItemDemoModel(2, R.drawable.ic_item_2, FREE))
        mList.add(ItemDemoModel(3, R.drawable.ic_item_3, FREE))
        mList.add(ItemDemoModel(4, R.drawable.ic_item_4, FREE))
        mList.add(ItemDemoModel(5, R.drawable.ic_item_5, FREE))
        mList.add(ItemDemoModel(6, R.drawable.ic_item_6, FREE))
        mList.add(ItemDemoModel(7, R.drawable.ic_item_7, FREE))
        mList.add(ItemDemoModel(8, R.drawable.ic_item_8, FREE))
        mList.add(ItemDemoModel(9, R.drawable.ic_item_9, FREE))
        mList.add(ItemDemoModel(10, R.drawable.ic_item_1, FREE))
        mList.add(ItemDemoModel(11, R.drawable.ic_item_2, FREE))
        mList.add(ItemDemoModel(12, R.drawable.ic_item_3, FREE))
        mList.add(ItemDemoModel(13, R.drawable.ic_item_4, FREE))
        mList.add(ItemDemoModel(14, R.drawable.ic_item_5, FREE))
        mList.add(ItemDemoModel(15, R.drawable.ic_item_6, FREE))
        mList.add(ItemDemoModel(16, R.drawable.ic_item_7, FREE))
        mList.add(ItemDemoModel(17, R.drawable.ic_item_8, FREE))
        mList.add(ItemDemoModel(18, R.drawable.ic_item_9, FREE))
        return mList
    }

    fun getListNativeSpanCountDemo(): MutableList<ItemDemoModel>{
        val mList: MutableList<ItemDemoModel> = mutableListOf()
        mList.add(ItemDemoModel(1, R.drawable.ic_item_1, FREE))
        mList.add(ItemDemoModel(2, R.drawable.ic_item_2, FREE))
        mList.add(ItemDemoModel(3, R.drawable.ic_item_3, FREE))
        mList.add(ItemDemoModel(4, R.drawable.ic_item_4, FREE))
        mList.add(ItemDemoModel(5, R.drawable.ic_item_5, FREE))
        mList.add(ItemDemoModel(6, R.drawable.ic_item_6, FREE))
        mList.add(ItemDemoModel(7, R.drawable.ic_item_7, FREE))
        mList.add(ItemDemoModel(8, R.drawable.ic_item_8, FREE))
        mList.add(ItemDemoModel(9, R.drawable.ic_item_9, FREE))
        mList.add(ItemDemoModel(10, R.drawable.ic_item_1, FREE))
        mList.add(ItemDemoModel(11, R.drawable.ic_item_2, FREE))
        mList.add(ItemDemoModel(12, R.drawable.ic_item_3, FREE))
        mList.add(ItemDemoModel(13, R.drawable.ic_item_4, FREE))
        mList.add(ItemDemoModel(14, R.drawable.ic_item_5, FREE))
        mList.add(ItemDemoModel(15, R.drawable.ic_item_6, FREE))
        mList.add(ItemDemoModel(16, R.drawable.ic_item_7, FREE))
        mList.add(ItemDemoModel(17, R.drawable.ic_item_8, FREE))
        mList.add(ItemDemoModel(18, R.drawable.ic_item_9, FREE))
        mList.add(ItemDemoModel(19, R.drawable.ic_item_1, FREE))
        mList.add(ItemDemoModel(20, R.drawable.ic_item_2, FREE))
        mList.add(ItemDemoModel(21, R.drawable.ic_item_3, FREE))
        mList.add(ItemDemoModel(22, R.drawable.ic_item_4, FREE))
        mList.add(ItemDemoModel(23, R.drawable.ic_item_5, FREE))
        mList.add(ItemDemoModel(24, R.drawable.ic_item_6, FREE))
        mList.add(ItemDemoModel(25, R.drawable.ic_item_7, FREE))
        mList.add(ItemDemoModel(26, R.drawable.ic_item_8, FREE))
        mList.add(ItemDemoModel(27, R.drawable.ic_item_9, FREE))
        mList.add(ItemDemoModel(28, R.drawable.ic_item_1, FREE))
        mList.add(ItemDemoModel(29, R.drawable.ic_item_2, FREE))
        mList.add(ItemDemoModel(30, R.drawable.ic_item_3, FREE))
        mList.add(ItemDemoModel(31, R.drawable.ic_item_4, FREE))
        mList.add(ItemDemoModel(32, R.drawable.ic_item_5, FREE))
        mList.add(ItemDemoModel(33, R.drawable.ic_item_6, FREE))
        mList.add(ItemDemoModel(34, R.drawable.ic_item_7, FREE))
        mList.add(ItemDemoModel(35, R.drawable.ic_item_8, FREE))
        mList.add(ItemDemoModel(36, R.drawable.ic_item_9, FREE))
        return mList
    }
}