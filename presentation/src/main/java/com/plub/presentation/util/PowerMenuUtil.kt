package com.plub.presentation.util

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.skydoves.powermenu.CustomPowerMenu
import com.skydoves.powermenu.OnMenuItemClickListener

object PowerMenuUtil {
    fun getPlubPowerMenu(
        context: Context,
        lifecycleOwner: LifecycleOwner,
        onMenuItemClickListener: OnMenuItemClickListener<String>
    ): CustomPowerMenu<String, PlubPopUpMenuAdapter> {
        var popupMenu: CustomPowerMenu<String, PlubPopUpMenuAdapter>? = null

        popupMenu = CustomPowerMenu.Builder<String, PlubPopUpMenuAdapter>(context, PlubPopUpMenuAdapter())
            .addItem("수정")
            .addItem("삭제")
            .setMenuRadius(10.px.toFloat())
            .setBackgroundAlpha(0f)
            .setWidth(86.px)
            .setPadding(8.px)
            .setLifecycleOwner(lifecycleOwner)
            .setOnBackgroundClickListener {
                popupMenu?.dismiss()
            }
            .setOnMenuItemClickListener(onMenuItemClickListener).build()

        return popupMenu
    }
}