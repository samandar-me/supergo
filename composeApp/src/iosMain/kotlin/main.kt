import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.sdk.supergo.ui.root.RootComponent
import com.sdk.supergo.ui.root.RootContent
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
