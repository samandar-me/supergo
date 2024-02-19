import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.sdk.supergo.ui.root.RootComponent
import com.sdk.supergo.ui.root.RootContent
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val rootComponent = RootComponent(
        componentContext = DefaultComponentContext(
            lifecycle = LifecycleRegistry()
        ),
        storeFactory = DefaultStoreFactory()
    )
    return ComposeUIViewController {
        RootContent(rootComponent)
    }
}
fun debugBuild() {
    Napier.base(DebugAntilog())
}

