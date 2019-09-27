package com.kongqw.kbox.network.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.kongqw.kbox.network.Constants
import com.kongqw.kbox.network.NetworkManager
import com.kongqw.kbox.network.enums.NetworkType
import com.kongqw.kbox.network.listener.INetworkStateChangedListener

class NetworkStateBroadcastReceiver : BroadcastReceiver() {

    private val mNetworkStateChangedListenerList = ArrayList<INetworkStateChangedListener>()
    private var mCurrentNetworkType: NetworkType = NetworkType.NONE

    private val mHandler = Handler(Looper.getMainLooper())

    /**
     *
     */
    override fun onReceive(context: Context?, intent: Intent?) {

        when (intent?.action) {
            Constants.ANDROID_NET_CHANGE_ACTION -> {
                if (NetworkManager.isNetworkConnected()) {

                    mCurrentNetworkType = NetworkManager.getCurrentNetworkType()

                        // 有网络
                    mHandler.post {
                        mNetworkStateChangedListenerList.forEach {
                            it.onNetworkConnected(mCurrentNetworkType)
                        }
                    }
                } else {
                    // 无网络
                    mCurrentNetworkType = NetworkType.NONE
                    mHandler.post {
                        mNetworkStateChangedListenerList.forEach {
                            it.onNetworkDisconnected()
                        }
                    }
                }
            }
        }
    }

    /**
     * 添加网络变化监听
     */
    fun addNetworkStateChangedListener(listener: INetworkStateChangedListener) {
        if (!mNetworkStateChangedListenerList.contains(listener)) {
            mNetworkStateChangedListenerList.add(listener)
        }
    }

    /**
     * 移除网络状态监听
     */
    fun removeNetworkStateChangedListener(listener: INetworkStateChangedListener) {
        if (mNetworkStateChangedListenerList.contains(listener)) {
            mNetworkStateChangedListenerList.remove(listener)
        }
    }
}