package com.jesen.code.ffmpegplaykot.view.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.jesen.code.ffmpegplaykot.databean.MediaPrev
import com.jesen.code.ffmpegplaykot.util.PicResizeUtil

class PictureFragment :Fragment(){
    private var mFilePath:String? = null

    companion object{
        fun newInstance(mediaPrev: MediaPrev):PictureFragment{
            val fragment = PictureFragment()
            val bundle = Bundle()
            bundle.putString("filePath", mediaPrev.filePath)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mFilePath = it.getString("filePath")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageView = ImageView(context)
        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.layoutParams = layoutParams

        val margin:Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f,
            resources.displayMetrics).toInt()

        imageView.setPadding(margin, 0, margin,0)

        mFilePath?.let {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(mFilePath, options)

            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels

            val resizeW = if (options.outWidth > screenWidth) screenWidth else options.outWidth
            val resizeH = if (options.outHeight > screenHeight) screenHeight else options.outHeight

            mFilePath?.let {
                imageView.setImageBitmap(PicResizeUtil.resize(it, resizeW, resizeH))
            }
        }
    return imageView
    }
}