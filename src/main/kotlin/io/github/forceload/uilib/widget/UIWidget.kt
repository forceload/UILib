package io.github.forceload.uilib.widget

import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.wrapper.UIRenderInfo

interface UIWidget<T> {
    var eventCallback: MutableMap<String, T.() -> Unit>
    fun render(renderInfo: UIRenderInfo)
    fun update(callback: T.() -> Unit)
    fun tick()
    fun apply(uiScreen: UIScreen)
    fun genCallback(callback: T.() -> Unit) = callback
}