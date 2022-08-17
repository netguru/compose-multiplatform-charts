import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.netguru.multiplatform.charts.application.Application
import com.netguru.multiplatform.charts.common.AppTheme.strings
import com.netguru.multiplatform.charts.common.WindowSize

fun main() = application {
    val windowState = rememberWindowState(size = DpSize(1050.dp, 950.dp))
    Window(
        onCloseRequest = ::exitApplication,
        state = windowState,
        title = strings.app_name
    ) {
        Application(WindowSize.basedOnWidth(windowState.size.width))
    }
}
