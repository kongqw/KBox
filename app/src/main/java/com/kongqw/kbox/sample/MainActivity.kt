package com.kongqw.kbox.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kongqw.kbox.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * CommonLibrary测试入口
     */
    fun onCommonLibrary(view: View) = startActivity(Intent(applicationContext, CommonLibraryActivity::class.java))

    /**
     * UILibrary测试入口
     */
    fun onUILibrary(view: View) = startActivity(Intent(applicationContext, UILibraryActivity::class.java))
}
