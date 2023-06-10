package io.github.forceload.uilib.widget

import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.wrapper.UIRenderInfo

interface UIWidget<T> {
    var visible: Boolean
    var _forcedInvisible: Boolean
    var eventCallback: MutableMap<String, T.() -> Unit>
    fun render(renderInfo: UIRenderInfo)
    fun tick(callback: T.() -> Unit)
    fun frame(callback: T.() -> Unit)
    fun tickUpdate()
    fun generate()
    fun apply(uiScreen: UIScreen)
    fun genCallback(callback: T.() -> Unit) = callback
}