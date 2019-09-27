package com.kongqw.kbox.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.kongqw.kbox.R
import com.kongqw.kbox.network.NetworkManager
import com.kongqw.kbox.network.enums.NetworkType
import com.kongqw.kbox.network.listener.INetworkStateChangedListener
import kotlinx.android.synthetic.main.activity_network_library.*

class NetworkLibraryActivity : AppCompatActivity(), INetworkStateChangedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_library)
        val currentNetworkType = NetworkManager.getCurrentNetworkType()
        showNetworkState(String.format("当前网络类型：%s", currentNetworkType.name))

        NetworkManager.getInstance().addNetworkStateChangedListener(this)
    }

    override fun onDestroy() {
        NetworkManager.getInstance().removeNetworkStateChangedListener(this)
        super.onDestroy()
    }

    private fun showNetworkState(state: String) {
        stv.text = state
    }

    /**
     * 获取当前网络状态
     */
    fun onGetCurrentNetworkType(view: View) {
        val currentNetworkType = NetworkManager.getCurrentNetworkType()
        Toast.makeText(applicationContext, "当前网络状态：${currentNetworkType.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onNetworkConnected(type: NetworkType) {
        Toast.makeText(applicationContext, "网络连接成功：${type.name}", Toast.LENGTH_SHORT).show()
        showNetworkState(String.format("当前网络类型：%s", type.name))
    }

    override fun onNetworkDisconnected() {
        Toast.makeText(applicationContext, "网络连接断开", Toast.LENGTH_SHORT).show()
        showNetworkState("网络连接断开")
    }
}
