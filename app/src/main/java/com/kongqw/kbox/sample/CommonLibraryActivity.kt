package com.kongqw.kbox.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.kongqw.kbox.R

class CommonLibraryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_library)
    }

    /**
     * IntentUtils.ktt 测试入口
     */
    fun onIntentUtil(view: View) = startActivity(Intent(applicationContext, IntentUtilActivity::class.java))
}
