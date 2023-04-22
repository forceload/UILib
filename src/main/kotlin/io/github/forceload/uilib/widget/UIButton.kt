package io.github.forceload.uilib.widget

import io.github.forceload.uilib.UILib
import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.wrapper.Point2D
import io.github.forceload.uilib.wrapper.UIRenderInfo
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text
import java.awt.Dimension

class UIButton: UIWidget {

    var text: Text = Text.translatable("gui.${UILib.MOD_ID}.button_default")

    var centered = true
    var position = Point2D(0, 0)
    var size: Dimension = Dimension(100, 100)
    private var eventCallback = mutableMapOf(
        "click" to genCallback { }
    )

    private lateinit var drawable: ButtonWidget
    fun generate() {
        val builder = ButtonWidget.builder(text) { _: ButtonWidget? ->
            eventCallback["click"]?.let { it() }
        }

        drawable = builder.dimensions(
            position.x - (if (centered) { size.width / 2 } else 0),
            position.y - (if (centered) { size.height / 2 } else 0),
            size.width, size.height
        ).build()
    }

    fun click(callback: UIButton.() -> Unit) { eventCallback["click"] = callback }

    private fun genCallback(callback: UIButton.() -> Unit) = callback
    override fun render(renderInfo: UIRenderInfo) {
        drawable.render(renderInfo.matrixStack, renderInfo.mousePos.x, renderInfo.mousePos.y, renderInfo.deltaTime)
    }

    override fun apply(uiScreen: UIScreen) {
        uiScreen.applyUI(drawable)
    }
}