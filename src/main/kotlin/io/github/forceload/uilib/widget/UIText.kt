package io.github.forceload.uilib.widget

import io.github.forceload.uilib.UILib
import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.wrapper.Point2D
import io.github.forceload.uilib.wrapper.UIRenderInfo
import net.minecraft.client.gui.DrawableHelper.drawCenteredTextWithShadow
import net.minecraft.client.gui.DrawableHelper.drawTextWithShadow
import net.minecraft.text.Text

class UIText(var text: Text = defaultText): UIWidget<UIText> {
    var screens = ArrayList<UIScreen>()
    companion object {
        val defaultText = Text.translatable("gui.${UILib.MOD_ID}.text_default")
    }

    var centered = true
    var shadowed = true
    var position = Point2D(0, 0)
    var color = 0xFFFFFF
    constructor(text: String): this(Text.translatable(text))

    override var eventCallback = mutableMapOf(
        "tick" to genCallback { }
    )

    override fun render(renderInfo: UIRenderInfo) {
        eventCallback["frame"]?.invoke(this)

        for (screen in screens) {
            if (centered) {
                if (shadowed) {
                    drawCenteredTextWithShadow(
                        renderInfo.matrixStack, screen.textRenderer(),
                        text, position.x, position.y, color
                    )
                } else {
                    drawCenteredTextWithShadow(
                        renderInfo.matrixStack, screen.textRenderer(),
                        text, position.x, position.y, color)
                }
            } else {
                if (shadowed) {
                    drawTextWithShadow(
                        renderInfo.matrixStack, screen.textRenderer(),
                        text, position.x, position.y, color
                    )
                } else {
                    drawTextWithShadow(
                        renderInfo.matrixStack, screen.textRenderer(),
                        text, position.x, position.y, color)
                }
            }
        }
    }

    fun frame(callback: UIText.() -> Unit) { eventCallback["frame"] = callback }
    override fun update(callback: UIText.() -> Unit) { eventCallback["tick"] = callback }
    override fun tick() { eventCallback["tick"]?.invoke(this) }

    override fun apply(uiScreen: UIScreen) {
        screens.add(uiScreen)
    }
}