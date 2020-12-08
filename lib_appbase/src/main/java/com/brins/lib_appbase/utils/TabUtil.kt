package com.brins.lib_appbase.utils

import com.brins.lib_appbase.R
import com.brins.lib_appbase.bean.TabBean
import java.util.*

/**
 * Created by lipeilin
 * on 2020/12/7
 */
class TabUtil private constructor() {


    companion object {
        @Volatile
        private var instance: TabUtil? = null

        fun getTabInstance(): TabUtil {
            if (instance == null) {
                synchronized(TabUtil::class.java) {
                    if (instance != null) {
                        instance = TabUtil()
                    }
                }
            }
            return instance!!
        }

        private val TITLES = arrayOf<String>(
            UIUtils.getString(R.string.appbase_home_tab),
            UIUtils.getString(R.string.appbase_location_tab),
            UIUtils.getString(R.string.appbase_mine_tab)
        )
        private val mTabIcons = intArrayOf(
            R.drawable.appbase_icon_home,
            R.drawable.appbase_icon_card,
            R.drawable.appbase_icon_mine
        )
        private val mSelectedTabIcons = intArrayOf(
            R.drawable.appbase_icon_selected_home,
            R.drawable.appbase_icon_card,
            R.drawable.appbase_icon_selected_mine
        )

    }

    private val mUpdateTabs: MutableList<TabBean> = ArrayList<TabBean>()

    fun initTab(callBack: CallBack) {
        if (mUpdateTabs.isEmpty()) {
            val list = arrayListOf<TabBean>()
            TITLES.forEachIndexed { index, s ->
                run {
                    list.add(TabBean(s, mTabIcons[index], mSelectedTabIcons[index]))
                }
            }
            mUpdateTabs.addAll(list)
        }
        callBack.onTabLoadedCallback(mUpdateTabs)
    }

    interface CallBack {
        fun onTabLoadedCallback(arrayList: List<TabBean?>?)
    }
}