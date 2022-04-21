package com.netguru.common.locale

import java.util.*

internal actual fun setApplicationLocale(localeTag: String) {
    Locale.setDefault(Locale(localeTag))
}
