package com.plub.presentation.util

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.powermenu.CustomPowerMenu
import com.skydoves.powermenu.OnMenuItemClickListener

object PowerMenuUtil {

    private const val MENU_RADIUS = 10
    private const val BACKGROUND_ALPHA = 0f
    private const val MENU_WIDTH = 90
    private const val MENU_PADDING = 8

    fun getPlubPowerMenu(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        items: List<String>,
        onMenuItemClickListener: OnMenuItemClickListener<String>
    ): CustomPowerMenu<String, PlubPopUpMenuAdapter> {
        return CustomPowerMenu.Builder<String, PlubPopUpMenuAdapter>(context, PlubPopUpMenuAdapter())
            .addItemList(items)
            .setMenuRadius(MENU_RADIUS.px.toFloat())
            .setBackgroundAlpha(BACKGROUND_ALPHA)
            .setWidth(MENU_WIDTH.px)
            .setPadding(MENU_PADDING.px)
            .setLifecycleOwner(lifecycleOwner)
            .setOnMenuItemClickListener(onMenuItemClickListener).build()
    }
}