package com.kongqw.kbox.network

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build
import android.util.Log
import com.kongqw.kbox.network.callback.NetworkCallbackImpl
import com.kongqw.kbox.network.enums.NetworkType
import com.kongqw.kbox.network.listener.INetworkStateChangedListener
import com.kongqw.kbox.network.receiver.NetworkStateBroadcastReceiver


class NetworkManager {

    private object NetworkManagerHolder {
        var application: Application? = null
        val mNetworkManager = NetworkManager()
    }

    companion object {

        private var mNetworkStateBroadcastReceiver: NetworkStateBroadcastReceiver? = null
        private var mNetworkCallbackImpl: NetworkCallbackImpl? = null

        fun init(application: Application) {
            NetworkManagerHolder.application = application
            // 判断一下，如果是在5.0 以前的版本，就使用广播的方式监听网络状态，如果是在5.0 以后的版本，就用

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mNetworkCallbackImpl = NetworkCallbackImpl()
                val builder = NetworkRequest.Builder()
                val request = builder.build()
                val connectivityManager = getConnectivityManager()
                connectivityManager?.registerNetworkCallback(request, mNetworkCallbackImpl)
            } else {
                mNetworkStateBroadcastReceiver = NetworkStateBroadcastReceiver()
                val filter = IntentFilter().apply {
                    addAction(Constants.ANDROID_NET_CHANGE_ACTION)
                }
                application.registerReceiver(mNetworkStateBroadcastReceiver, filter)
            }
        }

        fun getInstance(): NetworkManager = NetworkManagerHolder.mNetworkManager

        /**
         * 获取ConnectivityManager
         */
        fun getConnectivityManager(): ConnectivityManager? {
            return NetworkManagerHolder.application?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
        }

        /**
         * 判断当前活跃网络是否有网络连接
         */
        fun isNetworkConnected(): Boolean = getConnectivityManager()?.activeNetworkInfo?.isConnected ?: false

        /**
         * 获取当前设备是否有网络连接
         */
        @Suppress("DEPRECATION")
        fun isNetworkAvailable(): Boolean {
            getConnectivityManager()?.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Log.i("NetworkManager", "allNetworks.size = ${allNetworks.size}")
                    allNetworks.forEach {

                        if (getNetworkInfo(it).state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                } else {
                    allNetworkInfo.forEach {
                        if (it.state == NetworkInfo.State.CONNECTED) {
                            return true
                        }
                    }
                }
            }

            return false
        }

        @SuppressLint("DefaultLocale")
        fun getCurrentNetworkType(): NetworkType {
            try {
                getConnectivityManager()?.apply {
                    when (activeNetworkInfo.type) {
                        ConnectivityManager.TYPE_MOBILE -> {
                            return when (activeNetworkInfo.extraInfo.toLowerCase()) {
                                "cmnet" -> NetworkType.CMNET
                                else -> NetworkType.CMWAP
                            }
                        }
                        ConnectivityManager.TYPE_WIFI -> {
                            return NetworkType.WIFI
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return NetworkType.NONE
        }
    }

    /**
     * 添加网络变化监听
     */
    fun addNetworkStateChangedListener(listener: INetworkStateChangedListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNetworkCallbackImpl?.addNetworkStateChangedListener(listener)
        } else {
            mNetworkStateBroadcastReceiver?.addNetworkStateChangedListener(listener)
        }
    }

    /**
     * 移除网络状态监听
     */
    fun removeNetworkStateChangedListener(listener: INetworkStateChangedListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mNetworkCallbackImpl?.removeNetworkStateChangedListener(listener)
        } else {
            mNetworkStateBroadcastReceiver?.removeNetworkStateChangedListener(listener)
        }
    }


}