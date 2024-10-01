package com.example.analytics1.view.adapter

import android.annotation.SuppressLint
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
import com.example.analytics1.R
import com.example.analytics1.ads.NativeInlineManager
import com.example.analytics1.databinding.ItemDemoNativeInlineBinding
import com.example.analytics1.model.ItemDemoModel
import com.example.analytics1.util.MyUtils.Companion.goneView
import com.example.analytics1.util.MyUtils.Companion.visibleView
import com.example.analytics1.util.SharedPreferences

class NativeInlineSpanCountAdapter(
    private var context: Context,
    originList: MutableList<ItemDemoModel>
) : RecyclerView.Adapter<NativeInlineSpanCountAdapter.ViewHolder>() {
    private var mList: MutableList<ItemDemoModel> = resolveAds(originList)
    private var nativeManager: NativeInlineManager? = null
    private val isPro: Boolean = SharedPreferences.isProApp(context)
    private var itemClickListener: ItemClickListener? = null

    companion object {
        const val VIEW_TYPE_AD = 1
        private const val VIEW_TYPE_CONTENT = 2
        private const val AD_TYPE = "ADS_INLINE"
    }

    class ViewHolder(val binding: ItemDemoNativeInlineBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDemoNativeInlineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, itemPosition: Int) {
        val itemDemoModel = mList[itemPosition]

        holder.binding.apply {
            if (shouldShowAd(itemDemoModel)) {
                showAd(holder, itemPosition)
            } else {
                showContent()
                loadImage(itemDemoModel.image)
            }

            image.setOnClickListener {
                itemClickListener?.eventClick(itemDemoModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (mList[position].type == AD_TYPE) VIEW_TYPE_AD else VIEW_TYPE_CONTENT
    }

    private fun ItemDemoNativeInlineBinding.showAd(
        holder: ViewHolder,
        position: Int
    ) {
        nativeManager =
            NativeInlineManager.newInstance(context, context.getString(R.string.native_ad_unit_id))
        layoutView.goneView()
        layoutAds.visibleView()

        nativeManager?.loadNativeAd { nativeAd ->
            if (nativeAd != null) {
                nativeManager?.loadAdTemplate(
                    nativeAd,
                    R.layout.gnt_small_template_view,
                    holder.binding.layoutAds
                )
            } else {
                removeAd(holder, position)
            }
        }
    }

    private fun removeAd(holder: ViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION && mList[position].type == AD_TYPE) {
            mList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mList.size)
        }
        holder.binding.layoutAds.goneView()
    }

    private fun ItemDemoNativeInlineBinding.showContent() {
        layoutView.visibleView()
        layoutAds.goneView()
    }

    private fun ItemDemoNativeInlineBinding.loadImage(imageData: Int) {
        vLoading.visibleView()
        Glide.with(context).load(imageData)
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
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setClickListener(clickListener: ItemClickListener) {
        this.itemClickListener = clickListener
    }

    private fun resolveAds(data: MutableList<ItemDemoModel>): MutableList<ItemDemoModel> {
        if (isPro || data.size <= 3) return data

        val temp: MutableList<ItemDemoModel> = mutableListOf()
        temp.addAll(data.take(3))

        for (i in 0..(data.size - 3) / 12) {
            temp.add(ItemDemoModel(type = AD_TYPE, id = i))
            for (j in 1..12) {
                if (data.size - 3 >= i * 12 + j) temp.add(data[i * 12 + j + 2])
            }
        }
        return temp
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data: MutableList<ItemDemoModel>) {
        mList = resolveAds(data)
        notifyDataSetChanged()
    }

    private fun shouldShowAd(item: ItemDemoModel) = !isPro && item.type == AD_TYPE

    interface ItemClickListener {
        fun eventClick(itemDemoModel: ItemDemoModel)
    }
}