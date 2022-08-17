package com.netguru.multiplatform.charts.application.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.application.AppAction
import com.netguru.multiplatform.charts.application.LocalActionDispatcher
import com.netguru.multiplatform.charts.application.navigation.NavigationAction
import com.netguru.multiplatform.charts.application.navigation.NavigationState
import com.netguru.multiplatform.charts.application.screen.BarChartScreen
import com.netguru.multiplatform.charts.application.screen.BubbleChartScreen
import com.netguru.multiplatform.charts.application.screen.DialChartScreen
import com.netguru.multiplatform.charts.application.screen.GasBottleChartScreen
import com.netguru.multiplatform.charts.application.screen.LineChartScreen
import com.netguru.multiplatform.charts.application.screen.PieChartScreen
import com.netguru.multiplatform.charts.common.AppTheme
import com.netguru.multiplatform.charts.common.AppTheme.drawables
import com.netguru.multiplatform.charts.common.AppTheme.strings
import com.netguru.multiplatform.charts.common.LocalWindowSize
import com.netguru.multiplatform.charts.common.WindowSize
import com.netguru.multiplatform.charts.common.imageResources
import com.netguru.multiplatform.charts.common.noElevation
import kotlin.math.min

private const val DrawerMaxWidth = 250f

@Composable
fun HomeScreen() {
    val actionDispatcher = LocalActionDispatcher.current
    val state by actionDispatcher.navigationState.collectAsState()
    HomeContent(state, actionDispatcher::dispatch)
}

@Composable
private fun HomeContent(state: NavigationState, dispatch: (AppAction) -> Unit) {
    val windowSize = LocalWindowSize.current
    val isScreenExpanded = remember(windowSize) { windowSize == WindowSize.EXPANDED }
    val scaffoldState = rememberSizeAwareScaffoldState()
    val drawerState = scaffoldState.drawerState

    LaunchedEffect(state.isDrawerOpened) {
        if (state.isDrawerOpened) {
            drawerState.open()
        } else {
            drawerState.close()
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        drawerContent = {
            HomeDrawer(state, dispatch)
        },
        drawerGesturesEnabled = !isScreenExpanded,
        drawerShape = HomeDrawerShape(),
        topBar = {
            if (!isScreenExpanded) {
                TopBar(
                    title = {
                        Text(
                            text = state.currentTab.toLabel(),
                            color = AppTheme.colors.primaryText
                        )
                    },
                    onNavButtonClicked = { dispatch(NavigationAction.ToggleDrawer) }
                )
            }
        }
    ) {
        Row(
            modifier = Modifier
                .background(AppTheme.colors.background)
                .fillMaxHeight()
        ) {
            if (isScreenExpanded) {
                HomeDrawer(state, dispatch)
            }
            Crossfade(state.currentTab) { tab ->
                when (tab) {
                    NavigationState.Tab.BAR -> BarChartScreen()
                    NavigationState.Tab.BUBBLE -> BubbleChartScreen()
                    NavigationState.Tab.DIAL -> DialChartScreen()
                    NavigationState.Tab.GAS_BOTTLE -> GasBottleChartScreen()
                    NavigationState.Tab.LINE -> LineChartScreen()
                    NavigationState.Tab.PIE -> PieChartScreen()
                }
            }
        }
    }
}

@Composable
private fun rememberSizeAwareScaffoldState(): ScaffoldState {
    val commonSnackbarHostState = remember { SnackbarHostState() }
    val compactScaffoldState = rememberScaffoldState(
        drawerState = rememberDrawerState(DrawerValue.Closed),
        snackbarHostState = commonSnackbarHostState
    )
    val expandedScaffoldState = rememberScaffoldState(
        drawerState = DrawerState(DrawerValue.Closed),
        snackbarHostState = commonSnackbarHostState
    )
    val isScreenExpanded = LocalWindowSize.current == WindowSize.EXPANDED
    return if (isScreenExpanded) {
        expandedScaffoldState
    } else {
        compactScaffoldState
    }
}

private class HomeDrawerShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Rectangle(
            Rect(
                offset = Offset.Zero,
                size = Size(
                    width = min(size.width, DrawerMaxWidth * density.density),
                    height = size.height
                )
            )
        )
    }
}

@Composable
private fun TopBar(
    title: @Composable () -> Unit = { Text(strings.app_name) },
    onNavButtonClicked: () -> Unit,
) {
    TopAppBar(
        backgroundColor = AppTheme.colors.background,
        navigationIcon = {
            IconButton(
                onClick = onNavButtonClicked
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = strings.navigation_menu
                )
            }
        },
        title = title,
    )
}

private val drawerHorizontalPadding = 20.dp

@Composable
fun HomeDrawer(
    state: NavigationState,
    dispatch: (AppAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(DrawerMaxWidth.dp)
            .background(AppTheme.colors.surface)
            .verticalScroll(rememberScrollState())
            .padding(
                top = AppTheme.dimens.grid_2,
                bottom = AppTheme.dimens.grid_3,
                start = AppTheme.dimens.grid_1_5,
                end = AppTheme.dimens.grid_1_5
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        TopHomeDrawer(state, dispatch)
        Spacer(modifier = Modifier.height(AppTheme.dimens.grid_1_5))
    }
}

@Composable
private fun TopHomeDrawer(state: NavigationState, dispatch: (AppAction) -> Unit) {
    Column {
        Image(
            modifier = Modifier
                .size(width = 160.dp, height = 60.dp)
                .padding(
                    start = drawerHorizontalPadding - 5.dp,
                    bottom = AppTheme.dimens.grid_1
                ),
            painter = if (isSystemInDarkTheme()) {
                imageResources(drawables.netguru_logo_dark)
            } else {
                imageResources(drawables.netguru_logo_light)
            },
            contentDescription = strings.navigation_panel_logo,
        )
        Spacer(modifier = Modifier.height(AppTheme.dimens.grid_1_5))
        arrayOf(
            NavigationState.Tab.BAR,
            NavigationState.Tab.BUBBLE,
            NavigationState.Tab.DIAL,
            NavigationState.Tab.GAS_BOTTLE,
            NavigationState.Tab.LINE,
            NavigationState.Tab.PIE,
        ).forEach { tab ->
            HomeDrawerButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = { dispatch(NavigationAction.OpenTab(tab)) },
                iconPainter = null,
                iconDescription = tab.toLabel(),
                label = tab.toLabel(),
                isCurrent = tab == state.currentTab,
            )
        }
    }
}

@Composable
fun HomeDrawerButton(
    modifier: Modifier = Modifier,
    isCurrent: Boolean = false,
    onClick: () -> Unit,
    iconPainter: Painter?,
    label: String,
    iconDescription: String = label,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isCurrent) AppTheme.colors.background else Color.Transparent,
            contentColor = if (isCurrent) AppTheme.colors.primary else AppTheme.colors.secondaryText
        ),
        shape = AppTheme.shapes.cornersRounded,
        contentPadding = PaddingValues(
            horizontal = drawerHorizontalPadding,
            vertical = 15.dp,
        ),
        elevation = ButtonDefaults.noElevation()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            if (iconPainter != null) {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    painter = iconPainter,
                    contentDescription = iconDescription
                )
            } else {
                Spacer(Modifier.size(ButtonDefaults.IconSize))
            }
            Spacer(Modifier.size(ButtonDefaults.IconSize))
            Text(label)
        }
    }
}
