package com.brins.lib_bridge.factory

import com.brins.lib_bridge.BridgeInterface
import java.lang.Exception
import java.lang.RuntimeException

class NewInstanceFactory : Factory {

    companion object {
        val intance: NewInstanceFactory by lazy { NewInstanceFactory() }
    }

    override fun <T : BridgeInterface> create(bridgeClazz: Class<T>): T = try {
        bridgeClazz.newInstance()
    } catch (e: Exception) {
        throw RuntimeException("Cannot create an instance of $bridgeClazz", e)
    }
}