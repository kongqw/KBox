package com.kongqw.kbox.common

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore

/**
 * Intent 获取工具
 */
object IntentUtils {

    /**
     * 获取打开相机的意图
     */
    @JvmStatic
    fun getOpenCameraIntent(outputUri: Uri): Intent {
        return Intent().apply {
            // 设置意图为拍照
            action = MediaStore.ACTION_IMAGE_CAPTURE
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // 将拍出的照片保存到指定的URI
            putExtra(MediaStore.EXTRA_OUTPUT, outputUri)
        }
    }

    /**
     * 获取打开图库的意图
     */
    @JvmStatic
    fun getOpenAlbumIntent(): Intent {
        return Intent(Intent.ACTION_PICK).apply {
            type = "image/*"
        }
    }
}