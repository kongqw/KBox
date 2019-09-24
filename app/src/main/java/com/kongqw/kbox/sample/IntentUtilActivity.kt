package com.kongqw.kbox.sample

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kongqw.kbox.R
import com.kongqw.kbox.common.IntentUtils
import java.io.File

class IntentUtilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent_util)
    }

    fun onOpenSystemCamera(view:View){
        IntentUtils.getOpenCameraIntent(Uri.fromFile(File("")))
    }

    fun onOpenSystemAlbum(view:View){
        val intentUtil = IntentUtils.getOpenAlbumIntent()
        startActivity(intentUtil)
    }
}
