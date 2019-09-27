package com.kongqw.kbox.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.kongqw.kbox.R
import com.kongqw.kbox.common.FileUtils

class FileUtilsActivity : AppCompatActivity() {

    companion object {

        // SD卡根目录
        private val CACHE_FILE_PATH = Environment.getExternalStorageDirectory().toString() + "/cachefile.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_utils)
    }

    /**
     * 测试文件写入
     */
    fun onFileWrite(view: View) {
        val writeFile = FileUtils.writeFile(CACHE_FILE_PATH, "Hello word!hahaha")
        Toast.makeText(applicationContext, if (writeFile) "写入成功" else "写入失败", Toast.LENGTH_SHORT).show()
    }

    /**
     * 测试文件读取
     */
    fun onFileRead(view: View) {
        val readFile = FileUtils.readFile(CACHE_FILE_PATH)
        Toast.makeText(applicationContext, readFile, Toast.LENGTH_SHORT).show()
    }
}
