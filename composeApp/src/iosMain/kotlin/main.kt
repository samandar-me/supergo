import androidx.compose.ui.window.ComposeUIViewController
import com.sdk.supergo.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
