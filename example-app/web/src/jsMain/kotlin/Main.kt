import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        var text by remember { mutableStateOf("Hello, World!") }

        Button(attrs = {
            onClick { text = "Hello, Web!" }
        }) {
            Text(text)
        }
    }
}
