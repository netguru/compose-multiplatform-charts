package com.netguru.multiplatform.charts.common.locale

import androidx.compose.ui.text.intl.Locale
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

object LocaleProvider {

    val locale: StateFlow<String>
        get() = _locale

    private val _locale = MutableStateFlow(Locale.current.language)

    fun setLocale(localeTag: String) {
        _locale.update { localeTag }
        setApplicationLocale(localeTag)
    }
}
