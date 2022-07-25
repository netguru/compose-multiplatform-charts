package com.netguru.common

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
import com.netguru.charts.theme.ChartDefaults
import com.netguru.charts.theme.LocalChartColors
import com.netguru.common.locale.LocaleProvider

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
        onChart = Color.Unspecified,
        chart1 = Color.Unspecified,
        chart2 = Color.Unspecified,
        chart3 = Color.Unspecified,
        chart4 = Color.Unspecified,
        chart5 = Color.Unspecified,
        fullGasBottle = Color.Unspecified,
        emptyGasBottle = Color.Unspecified,
    )
}

val LocalAppDimens = staticCompositionLocalOf {
    smallDimens
}

private val appShapes = AppShapes(
    cornersRounded = RoundedCornerShape(8.dp)
)

private val appDarkColors = AppColors(
    primary = Color(0xFFAEC6FF),
    onPrimary = Color(0xFF002C70),
    primaryText = Color(0xFFb6bbe8),
    secondaryText = Color(0xFF938F99),
    borders = Color(0xFF938F99),
    surface = Color(0xFF1D1D20),
    background = Color(0xFF1B1B1E),
    success = Color(0xFF4FE086),
    danger = Color(0xFFFFB3AD),
    onChart = Color(0xFFFFFFFF),
    chart1 = Color(0xFF3D7FF9),
    chart2 = Color(0xFF3DC0F9),
    chart3 = Color(0xFF464CD3),
    chart4 = Color(0xFF2C4074),
    chart5 = Color(0xFF25DC89),
    emptyGasBottle = Color(0xFFFCA9A9),
    fullGasBottle = Color(0xFF5fa777)
)

private val appLightColors = AppColors(
    primary = Color(0xFF3D7FF9),
    onPrimary = Color(0xFFFFFFFF),
    primaryText = Color(0xFF1A1D3E),
    secondaryText = Color(0xFF505159),
    borders = Color(0xFFD4D5D9),
    surface = Color(0xFFF2F4F7),
    background = Color(0xFFFFFFFF),
    success = Color(0xFF20BE69),
    danger = Color(0xFFEB4F4F),
    onChart = Color(0xFFFFFFFF),
    chart1 = Color(0xFF3D7FF9),
    chart2 = Color(0xFF3DC0F9),
    chart3 = Color(0xFF464CD3),
    chart4 = Color(0xFF2C4074),
    chart5 = Color(0xFF25DC89),
    emptyGasBottle = Color(0xFFFCA9A9),
    fullGasBottle = Color(0xFF5fa777)
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
        LocalChartColors provides ChartDefaults.chartColors(),
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
    val onChart: Color,
    val chart1: Color,
    val chart2: Color,
    val chart3: Color,
    val chart4: Color,
    val chart5: Color,
    val emptyGasBottle: Color,
    val fullGasBottle: Color,
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
