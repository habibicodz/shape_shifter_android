package com.habibicodz.shapeshifter.ui.appicon

import android.content.ComponentName
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.habibicodz.shapeshifter.R

enum class AppIcon (val aliasName: String, @DrawableRes val icon: Int, @StringRes val label: Int) {
    DEFAULT(".ActivityStart", R.drawable.ic_launcher_preview, R.string.app_name),
    BLACK(".ActivityStartBlack", R.drawable.ic_launcher_black, R.string.app_name),
    BLUE(".ActivityStartBlue", R.drawable.ic_launcher_blue, R.string.app_name),
    RED(".ActivityStartRed", R.drawable.ic_launcher_red, R.string.app_name),
}

fun AppIcon.getComponentName(context: Context): ComponentName {
    return ComponentName(context.applicationContext, "com.habibicodz.shapeshifter$aliasName")
}