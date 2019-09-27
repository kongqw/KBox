package com.kongqw.kbox.network.callback

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.kongqw.kbox.network.NetworkManager
import com.kongqw.kbox.network.enums.NetworkType
import com.kongqw.kbox.network.listener.INetworkStateChangedListener

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class NetworkCallbackImpl : ConnectivityManager.NetworkCallback() {

    private val mNetworkStateChangedListenerList = ArrayList<INetworkStateChangedListener>()
    private val mHandler = Handler(Looper.getMainLooper())

    /**
     * 网络连接方式的集合
     */
    private var mNetworkList = ArrayList<Network>()

    init {
        NetworkManager.getConnectivityManager()?.allNetworks?.apply {
            mNetworkList.addAll(this)
        }
    }

    /**
     * 网络活跃回调（网络已连接）
     */
    override fun onAvailable(network: Network?) {
        super.onAvailable(network)
        Log.i("NetworkCallbackImpl", "onAvailable network = $network")

        if (null != network && !mNetworkList.contains(network)) {
            mNetworkList.add(network)
        }
    }

    /**
     * 当前的网络连接方式丢失
     */
    override fun onLost(network: Network?) {
        super.onLost(network)

        Log.i("NetworkCallbackImpl", "onLost network = $network")

        if (mNetworkList.contains(network)) {
            mNetworkList.remove(network)
        }

        if (mNetworkList.isNullOrEmpty()) {
            mHandler.post {
                mNetworkStateChangedListenerList.forEach {
                    it.onNetworkDisconnected()
                }
            }
        }
    }

    override fun onCapabilitiesChanged(network: Network?, networkCapabilities: NetworkCapabilities?) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        Log.i("NetworkCallbackImpl", "onCapabilitiesChanged network = $network  networkCapabilities = $networkCapabilities")
        if (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) == true) {

            val networkType: NetworkType = when {
                // WIFI
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkType.WIFI
                // 蜂窝网络
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkType.CMWAP
                //
                else -> NetworkType.NONE
            }

            mHandler.post {
                mNetworkStateChangedListenerList.forEach {
                    it.onNetworkConnected(networkType)
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