package io.github.forceload.uilib.generator

import io.github.forceload.uilib.widget.UIGroup
import io.github.forceload.uilib.widget.UIWidget
import io.github.forceload.uilib.util.Point2D
import io.github.forceload.uilib.wrapper.UIRenderInfo
import net.minecraft.client.gui.DrawContext

class UIObject {

    private val group = UIGroup()
    fun render(drawContext: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        group.render(UIRenderInfo(drawContext, Point2D(mouseX, mouseY), delta))
    }

    fun update() = group.tickUpdate()

    fun addChild(uiWidget: UIWidget<*>) = group.addChild(uiWidget)
    fun apply(uiScreen: UIScreen) = group.apply(uiScreen)
}