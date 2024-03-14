package com.sdk.supergo.ui.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.sdk.supergo.ui.human.HumanComponent
import com.sdk.supergo.ui.intro.IntroComponent
import com.sdk.supergo.ui.profile.ProfileComponent

class RootComponent internal constructor(
    componentContext: ComponentContext,
    private val intro: (ComponentContext, (IntroComponent.Output) -> Unit) -> IntroComponent,
    private val human: (ComponentContext, (HumanComponent.Output) -> Unit) -> HumanComponent,
    private val profile: (ComponentContext, (ProfileComponent.Output) -> Unit) -> ProfileComponent,
) : ComponentContext by componentContext {
    constructor(
        componentContext: ComponentContext,
        storeFactory: StoreFactory
    ) : this(
        componentContext = componentContext,
        intro = { childContext, output ->
            IntroComponent(
                componentContext = childContext,
           //     storeFactory = storeFactory,
                output = output
            )
        },
        human = { childContext, output ->
            HumanComponent(
                componentContext = childContext,
                storeFactory = storeFactory,
                output = output
            )
        },
        profile = { childContext, output ->
            ProfileComponent(
                componentContext = childContext,
              //  storeFactory = storeFactory,
                output = output
            )
        }
    )

    private val navigation = StackNavigation<Configuration>()

    private val stack = childStack(
        source = navigation,
        initialConfiguration = Configuration.Intro,
        handleBackButton = true,
        childFactory = ::createChild
    )
    val childStack: Value<ChildStack<*, Child>> = stack

    private fun createChild(configuration: Configuration, componentContext: ComponentContext): Child =
        when(configuration) {
            is Configuration.Intro -> Child.Intro(intro(componentContext, ::onIntroOutput))
            is Configuration.Human -> Child.Human(human(componentContext, ::onHumanOutput))
            is Configuration.Profile -> Child.Profile(profile(componentContext, ::onProfileOutput))
        }

    private fun onIntroOutput(output: IntroComponent.Output) = when(output) {
        is IntroComponent.Output.OnHumanClicked -> navigation.push(Configuration.Human)
        is IntroComponent.Output.OnDeliverClicked -> Unit //navigation.push(Configuration.D)
        is IntroComponent.Output.OnProfileClicked -> navigation.push(Configuration.Profile)
    }

    private fun onHumanOutput(output: HumanComponent.Output) = when(output) {
        is HumanComponent.Output.OnBack -> navigation.pop()
        is HumanComponent.Output.OnSuccess -> {
            navigation.pop()
            navigation.push(Configuration.Intro)
        }
    }
    private fun onProfileOutput(output: ProfileComponent.Output) = when(output) {
        is ProfileComponent.Output.OnBack -> navigation.pop()
    }

    private sealed interface Configuration: Parcelable {
        @Parcelize
        data object Intro: Configuration

        @Parcelize
        data object Human: Configuration
        @Parcelize
        data object Profile: Configuration
    }
    sealed class Child {
        data class Intro(val component: IntroComponent): Child()
        data class Human(val component: HumanComponent): Child()
        data class Profile(val component: ProfileComponent): Child()
    }
}