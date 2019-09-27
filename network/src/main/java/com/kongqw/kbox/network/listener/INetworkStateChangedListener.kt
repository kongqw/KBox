package com.kongqw.kbox.network.listener

import com.kongqw.kbox.network.enums.NetworkType

interface INetworkStateChangedListener {

    /**
     * 网络连接成功
     */
    fun onNetworkConnected(type: NetworkType)

    /**
     * 网络断开
     */
    fun onNetworkDisconnected()
}