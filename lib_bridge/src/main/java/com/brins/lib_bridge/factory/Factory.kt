package com.brins.lib_bridge.factory

import com.brins.lib_bridge.BridgeInterface

interface Factory {
    fun <T : BridgeInterface> create(bridgeClazz: Class<T>) : T
}