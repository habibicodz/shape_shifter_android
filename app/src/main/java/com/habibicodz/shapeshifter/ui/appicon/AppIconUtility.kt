package com.habibicodz.shapeshifter.ui.appicon

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log

class AppIconUtility(context: Context) {
    private val applicationContext: Context = context.applicationContext
    private val pm: PackageManager = applicationContext.packageManager

    val currentAppIcon: AppIcon by lazy { getCurrentAppIconFromPm() }

    //Currently selected AppIcon or default if non
    fun getCurrentAppIconFromPm(): AppIcon {
        val currentAppIcon = enumValues<AppIcon>().firstOrNull {
            val componentName = it.getComponentName(applicationContext)
            val componentEnabledSetting = pm.getComponentEnabledSetting(componentName)

            if (it == AppIcon.DEFAULT && componentEnabledSetting == PackageManager.COMPONENT_ENABLED_STATE_DEFAULT) {
                return it
            }

            componentEnabledSetting == PackageManager.COMPONENT_ENABLED_STATE_ENABLED
        }

        return if (currentAppIcon == null) {
            setNewAppIcon(AppIcon.DEFAULT)
            AppIcon.DEFAULT
        } else {
            currentAppIcon
        }
    }

    fun currentAppIconComponentName(): ComponentName {
        return currentAppIcon.getComponentName(applicationContext)
    }

    fun setNewAppIcon(desiredAppIcon: AppIcon) {
        Log.d("analyze", "Setting new app icon.")
        pm.setComponentEnabledSetting(desiredAppIcon.getComponentName(applicationContext), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
        Log.d("analyze", "${desiredAppIcon.name} enabled.")
        val previousAppIcon = currentAppIcon
        pm.setComponentEnabledSetting(previousAppIcon.getComponentName(applicationContext), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
        Log.d("analyze", "${previousAppIcon.name} disabled.")
        enumValues<AppIcon>().filterNot { it == desiredAppIcon || it == previousAppIcon }.forEach {
            pm.setComponentEnabledSetting(it.getComponentName(applicationContext), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
            Log.d("analyze", "${it.name} disabled.")
        }
    }
}