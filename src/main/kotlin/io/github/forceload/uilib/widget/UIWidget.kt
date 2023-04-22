package io.github.forceload.uilib.widget

import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.wrapper.UIRenderInfo

interface UIWidget {
    fun render(renderInfo: UIRenderInfo)
    fun apply(uiScreen: UIScreen)
}