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

class NativeInlineAdsAdapter(
    private var context: Context,
    originList: MutableList<ItemDemoModel>
) : RecyclerView.Adapter<NativeInlineAdsAdapter.ViewHolder>() {
    private var mList: MutableList<ItemDemoModel> = resolveAds(originList)
    private var nativeManager: NativeInlineManager? = null
    private val isPro: Boolean = SharedPreferences.isProApp(context)
    private var itemClickListener: ItemClickListener? = null

    companion object {
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
        val itemDemoModel: ItemDemoModel = mList[itemPosition]

        holder.binding.apply {
            if (shouldShowAd(itemDemoModel)) {
                showAd(holder, itemPosition)
            } else {
                showContent()
            }

            loadImage(itemDemoModel.image)

            image.setOnClickListener {
                itemClickListener?.eventClick(itemDemoModel)
            }
        }
    }

    private fun ItemDemoNativeInlineBinding.showAd(holder: ViewHolder, position: Int) {
        nativeManager =
            NativeInlineManager.newInstance(context, context.getString(R.string.native_ad_unit_id))
        layoutView.goneView()
        layoutAds.visibleView()

        layoutShimmer.shimmerView.startShimmer()
        nativeManager?.loadNativeAd { nativeAd ->
            layoutShimmer.shimmerView.stopShimmer()
            layoutShimmer.shimmerView.goneView()
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
        if (position != RecyclerView.NO_POSITION && position < mList.size && mList[position].type == AD_TYPE) {
            mList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mList.size)
        }
        holder.binding.layoutAds.goneView()
    }

    private fun ItemDemoNativeInlineBinding.showContent() {
        layoutView.visibleView()
        layoutAds.goneView()
        layoutShimmer.shimmerView.goneView()
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
        if (isPro) return data
        return data.flatMapIndexed { index, item ->
            if (index > 0 && index % 4 == 0) listOf(
                ItemDemoModel(type = "ADS_INLINE"),
                item
            ) else listOf(item)
        }.toMutableList()
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