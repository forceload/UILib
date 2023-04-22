package io.github.forceload.uilib.generator

import io.github.forceload.uilib.widget.UIGroup
import io.github.forceload.uilib.widget.UIWidget
import io.github.forceload.uilib.wrapper.Point2D
import io.github.forceload.uilib.wrapper.UIRenderInfo
import net.minecraft.client.util.math.MatrixStack

class UIObject {

    private val group = UIGroup()
    fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        group.render(UIRenderInfo(matrixStack, Point2D(mouseX, mouseY), delta))
    }

    fun addChild(uiWidget: UIWidget) = group.addChild(uiWidget)
    fun apply(uiScreen: UIScreen) = group.apply(uiScreen)
}