package io.github.forceload.uilib.widget

import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.wrapper.UIRenderInfo

class UIGroup : UIWidget<UIGroup> {
    var deltaTime: Float = 0.0F
    var children = ArrayList<UIWidget<*>>()
    fun addChild(widget: UIWidget<*>) {
        children.add(widget)
    }

    override var eventCallback = mutableMapOf(
        "tick" to genCallback { },
        "frame" to genCallback { }
    )

    override var visible: Boolean = true
    override var _forcedInvisible: Boolean = false
        set(value) {
            field = value
            for (child in children) {
                child._forcedInvisible = value
            }
        }

    override fun render(renderInfo: UIRenderInfo) {
        deltaTime = renderInfo.deltaTime
        eventCallback["frame"]?.invoke(this)
        for (child in children) { child.render(renderInfo) }
    }

    override fun frame(callback: UIGroup.() -> Unit) { eventCallback["frame"] = callback }
    override fun tick(callback: UIGroup.() -> Unit) { eventCallback["tick"] = callback }
    override fun tickUpdate() {
        eventCallback["tick"]?.invoke(this)
        for (child in children) { child.tickUpdate() }
    }

    override fun generate() {
        for (child in children) { child.generate() }
    }

    override fun apply(uiScreen: UIScreen) {
        for (child in children) { child.apply(uiScreen) }
    }
}