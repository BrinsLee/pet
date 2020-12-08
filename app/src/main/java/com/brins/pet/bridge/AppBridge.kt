package com.brins.pet.bridge

import android.content.Context
import com.brins.lib_bridge.app.AppBridgeInterface
import com.brins.pet.BaseApplication

/**
 * Created by lipeilin
 * on 2020/12/8
 */
class AppBridge : AppBridgeInterface {
    override fun getAppContext(): Context {
        return BaseApplication.getInstance().baseContext
    }
}