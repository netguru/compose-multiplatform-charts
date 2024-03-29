package com.netguru.multiplatform.charts.common.locale

import android.content.res.Resources
import java.util.Locale

internal actual fun setApplicationLocale(localeTag: String) {
    val resources = Resources.getSystem()
    val configuration = resources.configuration

    val locale = Locale(localeTag)
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}
