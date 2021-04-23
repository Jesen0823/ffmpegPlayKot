package com.jesen.code.ffmpegplaykot.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jesen.code.ffmpegplaykot.R
import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.databean.MediaPrev
import com.jesen.code.ffmpegplaykot.util.PicResizeUtil
import com.jesen.code.ffmpegplaykot.util.extend.simpleStartActivity
import com.jesen.code.ffmpegplaykot.view.activity.PreviewActivity

class GridAdapter(context: Context) : RecyclerView.Adapter<GridAdapter.GridViewHolder>() {

    private val mContext = context;
    private var mGridDataList = ArrayList<MediaData>()
    private var mPreviewDataList = ArrayList<MediaPrev>()
    private var mItemSize = mContext.resources.displayMetrics.widthPixels / 3

    class GridViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.item_img)
        val duration = itemView.findViewById<TextView>(R.id.item_duration)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewHolder {
        return GridViewHolder(
            LayoutInflater.from(mContext).inflate(
                R.layout.item_grid_view, parent
                , false
            )
        )
    }

    override fun getItemCount(): Int {
        return mGridDataList.size
    }

    override fun onBindViewHolder(holder: GridViewHolder, position: Int) {
        mGridDataList[position].let {
            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            layoutParams.width = mItemSize
            layoutParams.height = mItemSize
            holder.image.layoutParams = layoutParams

            val iterator = it
            it.takeIf {
                it.isPicture()
            }?.let {
                it.filePath.let {
                    holder.duration.visibility = View.GONE
                    holder.image.setImageBitmap(
                        it?.let { it1 ->
                            PicResizeUtil.resize(it1, mItemSize, mItemSize)
                        }
                    )
                    holder.image.visibility = View.VISIBLE
                }
            }

            it.takeIf { it.isVideo() }?.let {
                holder.duration.text = iterator.formatDuration()
                holder.duration.visibility = View.VISIBLE
                it.previewPath?.let {
                    holder.image.setImageBitmap(PicResizeUtil.resize(it, mItemSize, mItemSize))
                }

            }

            holder.image.setOnClickListener {
                val bundle = Bundle()
                    .apply {
                        putParcelableArrayList("previewDataList", mPreviewDataList)
                        putInt("position", position)
                    }
                simpleStartActivity<PreviewActivity>(mContext) {
                    putExtra("bundle", bundle)
                }
            }
        }
    }

    fun addData(list: List<MediaData>) {
        mGridDataList.addAll(list)
        mGridDataList = mGridDataList.sortedByDescending { it.createTime }
            .toMutableList() as ArrayList<MediaData>
        generateMediaPre()
        notifyDataSetChanged()
    }

    private fun generateMediaPre() {
        mPreviewDataList.clear()
        for (mediaData in mGridDataList) {
            mPreviewDataList.add(
                MediaPrev(
                    mediaData.mimeType,
                    mediaData.filePath,
                    mediaData.previewPath,
                    mediaData.createTime
                )
            )
        }
        mPreviewDataList.sortByDescending { it.createTime }
    }
}