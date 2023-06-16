package io.github.forceload.uilib.widget

import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.util.Point2D
import io.github.forceload.uilib.wrapper.UIRenderInfo
import net.minecraft.util.Identifier

class UIImage(texture: Identifier, color: Int): UIWidget<UIImage> {
    override var visible: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}
    override var _forcedInvisible: Boolean
        get() = TODO("Not yet implemented")
        set(value) {}
    override var eventCallback: MutableMap<String, UIImage.() -> Unit>
        get() = TODO("Not yet implemented")
        set(value) {}

    var position = Point2D(0, 0)
    override fun render(renderInfo: UIRenderInfo) {
        TODO("Not yet implemented")
    }

    override fun tickUpdate() {
        TODO("Not yet implemented")
    }

    override fun generate() {
        TODO("Not yet implemented")
    }

    override fun apply(uiScreen: UIScreen) {
        TODO("Not yet implemented")
    }

    override fun frame(callback: UIImage.() -> Unit) {
        TODO("Not yet implemented")
    }

    override fun tick(callback: UIImage.() -> Unit) {
        TODO("Not yet implemented")
    }
}