package com.kongqw.kbox.common

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera

/**
 * 获取设备信息的工具类
 */
object DeviceInfoUtils {

    /**
     * 判断设备是否有相机
     */
    @JvmStatic
    fun hasCamera(context: Context): Boolean {

        val packageManager = context.packageManager

        return (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                || Camera.getNumberOfCameras() > 0)
    }
}