package io.github.forceload.uilib.widget

import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.wrapper.UIRenderInfo

class UIGroup: UIWidget<UIGroup> {
    var children = ArrayList<UIWidget<*>>()
    fun addChild(widget: UIWidget<*>) {
        children.add(widget)
    }

    override var eventCallback = mutableMapOf(
        "tick" to genCallback { }
    )

    override fun render(renderInfo: UIRenderInfo) {
        for (child in children) { child.render(renderInfo) }
    }

    override fun update(callback: UIGroup.() -> Unit) { eventCallback["tick"] = callback }
    override fun tick() {
        eventCallback["tick"]?.invoke(this)
        for (child in children) { child.tick() }
    }

    override fun apply(uiScreen: UIScreen) {
        for (child in children) { child.apply(uiScreen) }
    }
}