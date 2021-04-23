package com.jesen.code.ffmpegplaykot.view.activity

import android.content.pm.PackageManager
import android.os.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesen.code.ffmpegplaykot.R
import com.jesen.code.ffmpegplaykot.adapter.GridAdapter
import com.jesen.code.ffmpegplaykot.databean.MediaData
import com.jesen.code.ffmpegplaykot.databean.MediaType
import com.jesen.code.ffmpegplaykot.util.FilterUtil
import com.jesen.code.ffmpegplaykot.util.PicScanHelper
import com.jesen.code.ffmpegplaykot.util.VideoScanHelper
import java.lang.ref.WeakReference

class PickerActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var gridAdapter: GridAdapter
    private lateinit var album2MediaMap:Map<String, MutableList<MediaData>>

    private val permissionRequestStorage = 1
    private val storagePermissions: Array<String> = arrayOf(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picker)

        initView()

        initData()

        initScanEnvironment()
    }

    private fun initView(){
        recyclerView = findViewById(R.id.picker_recyclerview)
    }

    private fun initData(){
        gridAdapter = GridAdapter(this)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = gridAdapter
        album2MediaMap = HashMap()
    }

    private fun initScanEnvironment(){
        if (checkPermission(storagePermissions)){
            startScan()
        }
    }

    private fun startScan(){
        val handler = ScanHandler(this)
        VideoScanHelper.start(this, handler)
        PicScanHelper.start(this, handler)

    }

    private class ScanHandler(activity: PickerActivity) :Handler(){
        val weakActivity = WeakReference<PickerActivity>(activity)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            weakActivity.get()?.run {
                when(msg.what){
                    MediaType.MEDIA_TYPE_VIDEO -> onHandleVideoScan(msg.obj as MutableList<MediaData>)
                    MediaType.MEDIA_TYPE_PICTURE -> onHandlePicScan(msg.obj as MutableList<MediaData>)
                }
            }
        }
    }

    private fun onHandleVideoScan(list: MutableList<MediaData>){
        FilterUtil.getDefaultVideoFilterManager().doFilter(list)
        gridAdapter.addData(list)
    }

    private fun onHandlePicScan(list: MutableList<MediaData>){
        FilterUtil.getDefaultPicFilterManager().doFilter(list)
        gridAdapter.addData(list)
    }

    private fun checkPermission(permissions:Array<String>):Boolean{
        if (Build.VERSION.SDK_INT < 23){
            return true
        }
        var allGranted = true
        for (permission in permissions){
            if (ContextCompat.checkSelfPermission(this,permission) == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)){
                    Toast.makeText(this, "need storage...",Toast.LENGTH_SHORT).show()
                }else{
                    ActivityCompat.requestPermissions(this, arrayOf(permission), permissionRequestStorage)
                }
                allGranted = false
            }
        }
        return allGranted
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            permissionRequestStorage -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                startScan()
            } else {
                Toast.makeText(this, "permission denied!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}