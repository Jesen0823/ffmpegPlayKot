package com.jesen.code.ffmpegplaykot

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jesen.code.ffmpegplaykot.util.extend.simpleStartActivity
import com.jesen.code.ffmpegplaykot.view.activity.PickerActivity
import com.jesen.code.ffmpegplaykot.widget.FFVideoView

class MainActivity : AppCompatActivity() {

    private var VIDEOPATH = ""
    private val CHOICE_REQUEST_CODE = 10

    private lateinit var mTextView: TextView
    private lateinit var choicePlayBtn: ImageButton
    private lateinit var mVideoView: FFVideoView
    private lateinit var openAlbum: TextView


    companion object {
        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Example of a call to a native method
        mTextView = findViewById(R.id.sample_text)
        mTextView.text = stringFromJNI()
        mVideoView = findViewById(R.id.videoView)
        choicePlayBtn = findViewById(R.id.choice_play)
        choicePlayBtn.setOnClickListener(View.OnClickListener {
            choiceVideo()
        })

        openAlbum = findViewById<TextView>(R.id.open_album)
        openAlbum.setOnClickListener {
            simpleStartActivity<PickerActivity>(this) {}
        }
    }

    private fun choiceVideo() {
        val i = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(i, CHOICE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CHOICE_REQUEST_CODE && resultCode == Activity.RESULT_OK && null != data) {
            val selectedVideo = data.data
            val filePathColumn = arrayOf(MediaStore.Video.Media.DATA)
            val cursor = contentResolver.query(
                selectedVideo!!,
                filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            VIDEOPATH = cursor.getString(columnIndex)
            mTextView.text = VIDEOPATH
            cursor.close()
            mVideoView.playVideo(VIDEOPATH)
        }
        if (resultCode != Activity.RESULT_OK) {
            return
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String?
}