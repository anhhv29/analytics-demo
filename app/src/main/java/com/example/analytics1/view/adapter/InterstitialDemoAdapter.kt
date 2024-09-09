package com.example.analytics1.view.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.analytics1.databinding.ItemDemoBinding
import com.example.analytics1.enums.TypeData
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.util.MyUtils.Companion.goneView
import com.example.analytics1.util.MyUtils.Companion.visibleView
import com.example.analytics1.util.SharedPreferences

class InterstitialDemoAdapter(
    private var context: Context,
    originList: MutableList<ItemDemoModel>
) : RecyclerView.Adapter<InterstitialDemoAdapter.ViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener
    private var mList = originList

    class ViewHolder(val binding: ItemDemoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        holder.binding.apply {
            Glide.with(context).load(mList[itemPosition].image)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        vLoading.goneView()
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        vLoading.goneView()
                        return false
                    }

                }).into(image)

            if (!SharedPreferences.isProApp(context) && mList[itemPosition].type == TypeData.ADS.name) {
                holder.binding.tvAds.visibleView()
            } else {
                holder.binding.tvAds.goneView()
            }

            image.setOnClickListener {
                itemClickListener.eventClick(mList[itemPosition])
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    interface ItemClickListener {
        fun eventClick(itemDemoModel: ItemDemoModel)
    }

    fun setClickListener(clickListener: ItemClickListener) {
        this.itemClickListener = clickListener
    }
}