package com.brins.lib_bridge.app

import android.content.Context
import com.brins.lib_appbase.utils.UIUtils

/**
 * Created by lipeilin
 * on 2020/11/11
 */
class DefaultAppBridge : AppBridgeInterface {
    override fun getAppContext(): Context {
        return UIUtils.getApplication().applicationContext
    }

/*    override fun getAccessToken(): String? {
        return LoginCache.UserCookie
    }

    override fun setAccessToken(accessToken: String?) {
        LoginCache.UserCookie = accessToken ?: ""
    }*/
}