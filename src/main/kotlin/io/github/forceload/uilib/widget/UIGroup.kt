package io.github.forceload.uilib.widget

import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.wrapper.UIRenderInfo

class UIGroup: UIWidget {
    var children = ArrayList<UIWidget>()
    fun addChild(widget: UIWidget) {
        children.add(widget)
    }

    override fun render(renderInfo: UIRenderInfo) {
        for (child in children) { child.render(renderInfo) }
    }

    override fun apply(uiScreen: UIScreen) {
        for (child in children) { child.apply(uiScreen) }
    }
}