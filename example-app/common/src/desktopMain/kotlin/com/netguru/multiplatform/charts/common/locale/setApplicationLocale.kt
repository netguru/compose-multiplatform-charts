package com.netguru.multiplatform.charts.common.locale

import java.util.Locale

internal actual fun setApplicationLocale(localeTag: String) {
    Locale.setDefault(Locale(localeTag))
}
