package com.brins.lib_bridge.app

import android.content.Context
import com.brins.lib_bridge.BridgeInterface

interface AppBridgeInterface : BridgeInterface {

    fun getAppContext(): Context

/*    fun getAccessToken(): String?

    fun setAccessToken(accessToken: String?)*/
}