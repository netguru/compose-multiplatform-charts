package com.netguru.multiplatform.charts.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.common.locale.LocaleProvider
import com.netguru.multiplatform.charts.theme.ChartColors
import com.netguru.multiplatform.charts.theme.LocalChartColors

val LocalAppShapes = staticCompositionLocalOf {
    AppShapes(
        cornersRounded = RoundedCornerShape(ZeroCornerSize)
    )
}

val LocalWindowSize = compositionLocalOf { WindowSize.COMPACT }

val LocalResources = compositionLocalOf { ResourcesImpl("EN") }

val LocalAppTypography = staticCompositionLocalOf {
    Typography()
}

val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        background = Color.Unspecified,
        surface = Color.Unspecified,
        primaryText = Color.Unspecified,
        secondaryText = Color.Unspecified,
        borders = Color.Unspecified,
        success = Color.Unspecified,
        danger = Color.Unspecified,
        yellow = Color.Unspecified,
        green = Color.Unspecified,
        blue = Color.Unspecified,
    )
}

val LocalAppDimens = staticCompositionLocalOf {
    smallDimens
}

private val appShapes = AppShapes(
    cornersRounded = RoundedCornerShape(8.dp)
)

private val appDarkColors = AppColors(
    primary = Color(0xFF00D563),
    onPrimary = Color(0xFF002C70),
    primaryText = Color(0xFFFFFFFF),
    secondaryText = Color(0xFF938F99),
    borders = Color(0x66D4D5D9),
    surface = Color(0xFF000000),
    background = Color(0xFF001409),
    success = Color(0xFF00D563),
    danger = Color(0xFFFF3B30),
    yellow = Color(0xFFFFCC00),
    green = Color(0xFF00D563),
    blue = Color(0xFF32ADE6),
)

private val appLightColors = AppColors(
    primary = Color(0xFF00D563),
    onPrimary = Color(0xFFFFFFFF),
    primaryText = Color(0xFF001409),
    secondaryText = Color(0xFF505159),
    borders = Color(0x66D4D5D9),
    surface = Color(0xFFF2F4F7),
    background = Color(0xFFFFFFFF),
    success = Color(0xFF00D563),
    danger = Color(0xFFFF3B30),
    yellow = Color(0xFFFFCC00),
    green = Color(0xFF00D563),
    blue = Color(0xFF32ADE6),
)

val lightChartColors = ChartColors(
    primary = appLightColors.primary,
    grid = appLightColors.borders,
    surface = appLightColors.background,
    fullGasBottle = appLightColors.success,
    emptyGasBottle = appLightColors.danger,
    overlayLine = appLightColors.danger
)

val darkChartColors = ChartColors(
    primary = appDarkColors.primary,
    grid = appDarkColors.borders,
    surface = appDarkColors.background,
    fullGasBottle = appDarkColors.success,
    emptyGasBottle = appDarkColors.danger,
    overlayLine = appDarkColors.danger
)

@Composable
private fun appTypography() = Typography(
    defaultFontFamily = FontFamily(
        fontResources("aeonik_regular.otf", FontWeight.Normal, FontStyle.Normal),
        fontResources("aeonik_medium.otf", FontWeight.W500, FontStyle.Normal),
        fontResources("aeonik_bold.otf", FontWeight.Bold, FontStyle.Normal)
    )
)

val smallDimens = AppDimens(
    grid_0_25 = 1.5f.dp,
    grid_0_5 = 3.dp,
    grid_1 = 6.dp,
    grid_1_5 = 9.dp,
    grid_2 = 12.dp,
    grid_2_5 = 15.dp,
    grid_3 = 18.dp,
    grid_3_5 = 21.dp,
    grid_4 = 24.dp,
    grid_4_5 = 27.dp,
    grid_5 = 30.dp,
    grid_5_5 = 33.dp,
    grid_6 = 36.dp
)

val sw600Dimens = AppDimens(
    grid_0_25 = 2.dp,
    grid_0_5 = 4.dp,
    grid_1 = 8.dp,
    grid_1_5 = 12.dp,
    grid_2 = 16.dp,
    grid_2_5 = 20.dp,
    grid_3 = 24.dp,
    grid_3_5 = 28.dp,
    grid_4 = 32.dp,
    grid_4_5 = 36.dp,
    grid_5 = 40.dp,
    grid_5_5 = 44.dp,
    grid_6 = 48.dp
)

val sw840Dimens = AppDimens(
    grid_0_25 = 2.5f.dp,
    grid_0_5 = 5.dp,
    grid_1 = 10.dp,
    grid_1_5 = 15.dp,
    grid_2 = 20.dp,
    grid_2_5 = 25.dp,
    grid_3 = 30.dp,
    grid_3_5 = 35.dp,
    grid_4 = 40.dp,
    grid_4_5 = 45.dp,
    grid_5 = 50.dp,
    grid_5_5 = 55.dp,
    grid_6 = 60.dp
)

@Composable
fun AppTheme(
    windowSize: WindowSize,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val appDimens = when (windowSize) {
        WindowSize.COMPACT -> smallDimens
        WindowSize.MEDIUM -> sw600Dimens
        WindowSize.EXPANDED -> sw840Dimens
    }
    CompositionLocalProvider(
        LocalAppShapes provides appShapes,
        LocalAppColors provides if (darkTheme) appDarkColors else appLightColors,
        LocalResources provides ResourcesImpl(LocaleProvider.locale.collectAsState().value),
        LocalAppTypography provides appTypography(),
        LocalAppDimens provides appDimens,
        LocalWindowSize provides windowSize,
        LocalChartColors provides if (darkTheme) darkChartColors else lightChartColors,
    ) {
        MaterialTheme(
            typography = appTypography(),
            colors = MaterialTheme.colors.copy(
                primary = AppTheme.colors.secondaryText,
                onSurface = AppTheme.colors.secondaryText,
                onBackground = AppTheme.colors.secondaryText,
                onPrimary = AppTheme.colors.secondaryText,
                onSecondary = AppTheme.colors.secondaryText,
            ),
            content = content,
        )
    }
}

object AppTheme {
    val shapes: AppShapes
        @ReadOnlyComposable
        @Composable
        get() = LocalAppShapes.current

    val colors: AppColors
        @ReadOnlyComposable
        @Composable
        get() = LocalAppColors.current

    val typography: Typography
        @ReadOnlyComposable
        @Composable
        get() = LocalAppTypography.current

    val dimens: AppDimens
        @ReadOnlyComposable
        @Composable
        get() = LocalAppDimens.current

    val strings: StringResources
        @ReadOnlyComposable
        @Composable
        get() = getString()

    val windowSize: WindowSize
        @ReadOnlyComposable
        @Composable
        get() = LocalWindowSize.current

    val drawables: DrawableResources
        @ReadOnlyComposable
        @Composable
        get() = getDrawbles()
}

@Immutable
data class AppShapes(
    val cornersRounded: Shape,
)

@Immutable
data class AppColors(
    val primary: Color,
    val onPrimary: Color,
    val background: Color,
    val surface: Color,
    val primaryText: Color,
    val secondaryText: Color,
    val borders: Color,
    val success: Color,
    val danger: Color,
    val yellow: Color,
    val green: Color,
    val blue: Color,
)

class AppDimens(
    val grid_0_25: Dp,
    val grid_0_5: Dp,
    val grid_1: Dp,
    val grid_1_5: Dp,
    val grid_2: Dp,
    val grid_2_5: Dp,
    val grid_3: Dp,
    val grid_3_5: Dp,
    val grid_4: Dp,
    val grid_4_5: Dp,
    val grid_5: Dp,
    val grid_5_5: Dp,
    val grid_6: Dp,
    val borders_thickness: Dp = 0.5.dp,
    val collapsable_icon_size: Dp = 24.dp,
)
