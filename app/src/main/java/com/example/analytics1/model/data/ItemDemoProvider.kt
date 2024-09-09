package com.example.analytics1.model.data

import com.example.analytics1.R
import com.example.analytics1.enums.TypeData
import com.example.analytics1.model.ItemDemoModel

class ItemDemoProvider {
    fun getListInterstitialDemo(): MutableList<ItemDemoModel> {
        val mList: MutableList<ItemDemoModel> = mutableListOf()
        mList.add(ItemDemoModel(1, R.drawable.ic_item_1, TypeData.FREE.name))
        mList.add(ItemDemoModel(2, R.drawable.ic_item_2, TypeData.FREE.name))
        mList.add(ItemDemoModel(3, R.drawable.ic_item_3, TypeData.FREE.name))
        mList.add(ItemDemoModel(4, R.drawable.ic_item_4, TypeData.FREE.name))
        mList.add(ItemDemoModel(5, R.drawable.ic_item_5, TypeData.FREE.name))
        mList.add(ItemDemoModel(6, R.drawable.ic_item_6, TypeData.FREE.name))
        mList.add(ItemDemoModel(7, R.drawable.ic_item_7, TypeData.FREE.name))
        mList.add(ItemDemoModel(8, R.drawable.ic_item_8, TypeData.FREE.name))
        mList.add(ItemDemoModel(9, R.drawable.ic_item_9, TypeData.FREE.name))
        return mList
    }
}