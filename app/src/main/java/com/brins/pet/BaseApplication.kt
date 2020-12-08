package com.brins.pet

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.brins.lib_appbase.config.MAIN_PROCESS_NAME
import com.brins.lib_appbase.manager.ActivityManager
import com.brins.lib_appbase.utils.AppUtils
import com.brins.lib_appbase.utils.UIUtils
import com.brins.lib_appbase.utils.getCurrProcessName
import com.brins.lib_bridge.BridgeInterface
import com.brins.lib_bridge.factory.Factory
import com.brins.lib_bridge.provider.BridgeProviders
import com.brins.pet.bridge.AppBridge
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Created by lipeilin
 * on 2020/12/8
 */
@HiltAndroidApp
class BaseApplication : Application() {

    @Inject
    lateinit var mBridge: BridgeProviders

    companion object {
        @JvmStatic
        var sInstance: BaseApplication? = null

        fun getInstance(): BaseApplication {
            if (sInstance == null) {
                throw NullPointerException("please inherit com.brins.pet.BaseApplication or call setApplication.")
            }
            return sInstance!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        setApplication(this)
        if (isMainProcess(this)) {
            UIUtils.init(this)
            AppUtils.init(this)
            initARouter()
            registerBridge()
        }
    }

    /**
     * 注册组件Bridge
     *
     */
    @Suppress("UNCHECKED_CAST")
    private fun registerBridge() {
        mBridge.register(AppBridge::class.java, object : Factory {
            override fun <T : BridgeInterface> create(bridgeClazz: Class<T>): T {
                return AppBridge() as T
            }
        })
    }

    /**
     * 注册Activity生命周期
     *
     * @param application
     */
    private fun setApplication(application: BaseApplication) {
        sInstance = application
        application.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {

            }

            override fun onActivityResumed(activity: Activity?) {
            }

            override fun onActivityStarted(activity: Activity?) {
            }

            override fun onActivityDestroyed(activity: Activity?) {
                ActivityManager.getInstance().removeActivity(activity)
            }

            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
            }

            override fun onActivityStopped(activity: Activity?) {
            }

            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                ActivityManager.getInstance().addActivity(activity)
            }

        })
    }

    /**
     * 判断是否在主进程
     *
     * @param context
     * @return
     */
    private fun isMainProcess(context: Context): Boolean {
        return MAIN_PROCESS_NAME == getCurrProcessName(context)
    }

    /**
     * 初始化ARouter
     *
     */
    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}