package com.jesen.code.ffmpegplaykot.view.fragment

import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.jesen.code.ffmpegplaykot.R
import com.jesen.code.ffmpegplaykot.databean.MediaPrev
import com.jesen.code.ffmpegplaykot.view.ZoomVideoView
import java.io.File

class VideoFragment :Fragment(){

    private lateinit var mFilePath:String
    private lateinit var mThumbnailPath:String

    private lateinit var mVideoView:ZoomVideoView
    private lateinit var mPlayPic:ImageView
    private lateinit var mFirstFrame:ImageView

    private var mPrepared:Boolean = false

    companion object{
        fun newInstance(mediaPrev: MediaPrev):Fragment{
            val fragment = VideoFragment()
            val bundle = Bundle()
            bundle.putString("filePath", mediaPrev.filePath)
            bundle.putString("thumbNailPath", mediaPrev.thumbnailPath)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mFilePath = it.getString("filePath").toString()
            mThumbnailPath = it.getString("thumbNailPath").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_preview, container, false)

        initView(view)

        resizeVideo(view)

        setListener()

        return view
    }

    private fun initView(view: View){
        mVideoView = view.findViewById(R.id.zoom_video_view)
        mFirstFrame = view.findViewById(R.id.preview_frame)
        mPlayPic = view.findViewById(R.id.choice_play)

        mThumbnailPath?.let {
            mFirstFrame?.setImageURI(Uri.fromFile(File(it)))
        }

        mVideoView.setVideoPath(mFilePath)
    }

    private fun resizeVideo(view: View){
        val sizeArray = resize()
        mVideoView.resetVideoView(sizeArray[0] - (view.paddingLeft + view.paddingRight),
        sizeArray[1] - (view.paddingTop + view.paddingBottom))

        mFirstFrame.let {
            val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT)
            layoutParams.width = sizeArray[0]
            layoutParams.height = sizeArray[1]
            layoutParams.gravity = Gravity.CENTER
            it.layoutParams = layoutParams
        }
    }

    private fun resize(): IntArray {
        val result = IntArray(2){0}
        mThumbnailPath.let {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(mThumbnailPath, options)

            val screenWidth = resources.displayMetrics.widthPixels
            val screenHeight = resources.displayMetrics.heightPixels

            val originWidth = options.outWidth
            val originHeight = options.outHeight

            (originHeight > originWidth).let {
                if (it){
                    result[1] = maxOf(originHeight, screenHeight)
                    result[0] = result[1] * originWidth / originHeight
                }else{
                    result[0] = maxOf(originWidth,screenWidth)
                    result[1] = result[0] * originHeight / originWidth
                }
            }

        }
        return result
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (!isVisibleToUser){
            pause()
        }
    }

    override fun onPause() {
        super.onPause()
        pause()
    }

    private fun pause(){
        mVideoView.pause()
        mVideoView.seekTo(0)
        resetStatus()
    }

    private fun resetStatus(){
        mPlayPic.visibility = View.VISIBLE
        mFirstFrame.visibility = View.VISIBLE
    }

    private fun setListener(){
        mPlayPic.setOnClickListener {
            mVideoView.visibility = View.VISIBLE
            mPlayPic.visibility = View.GONE
            if (mPrepared){
                mFirstFrame.visibility = View.GONE
            }
            mVideoView.start()
        }

        mVideoView.setOnPreparedListener{
            it.setOnInfoListener(MediaPlayer.OnInfoListener{
                mp,what,extra->
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START){
                    mPrepared = true
                    mFirstFrame.visibility = View.GONE
                }
                return@OnInfoListener true
            })
        }

        mVideoView.setOnCompletionListener {
            pause()
        }
    }
}