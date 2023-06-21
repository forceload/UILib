package io.github.forceload.uilib.widget

import io.github.forceload.uilib.UILib
import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.util.Point2D
import io.github.forceload.uilib.wrapper.UIRenderInfo
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text
import java.awt.Dimension

/**
 *
 *
 * ## Example
 * ```kotlin
 * var buttonPressCount = 0
 * button("Sample 0") {
 *     // This code centers the button on the screen
 *     position = Point2D(width / 2, height / 2)
 *     centered = true
 *
 *     click {
 *         // This code increases the <number> part of the text "Sample <number>" on the button by 1 when the button is clicked.
 *         buttonPressCount++
 *         text = Text.translatable("Sample ${buttonPressCount}")
 *     }
 * }
 * ```
 */
class UIButton(text: Text = defaultText): UIWidget<UIButton> {
    companion object {
        val defaultText = Text.translatable("gui.${UILib.MOD_ID}.button_default")
    }

    var text: Text = text
        set(value) {
            field = value
            drawable.message = value
        }

    var centered = true
    var deltaTime: Float = 0.0F
    var position = Point2D(0, 0)
    var size: Dimension = Dimension(100, 100)
    constructor(text: String): this(Text.translatable(text))

    override var eventCallback = mutableMapOf(
        "click" to genCallback { },
        "tick" to genCallback { },
        "frame" to genCallback { }
    )

    override var visible: Boolean = true
        set(value) { field = value; drawable.visible = value && !_forcedInvisible }

    override var _forcedInvisible: Boolean = false
        set(value) { field = value; drawable.visible = visible && !value }

    private lateinit var drawable: ButtonWidget
    override fun generate() {
        val builder = ButtonWidget.builder(text) { _: ButtonWidget? ->
            eventCallback["click"]?.invoke(this)
        }

        drawable = builder.dimensions(
            position.x - (if (centered) { size.width / 2 } else 0),
            position.y - (if (centered) { size.height / 2 } else 0),
            size.width, size.height
        ).build()
    }

    fun click(callback: UIButton.() -> Unit) { eventCallback["click"] = callback }
    override fun render(renderInfo: UIRenderInfo) {
        deltaTime = renderInfo.deltaTime
        eventCallback["frame"]?.invoke(this)
        // drawable.render(renderInfo.matrixStack, renderInfo.mousePos.x, renderInfo.mousePos.y, renderInfo.deltaTime)
    }

    override fun frame(callback: UIButton.() -> Unit) { eventCallback["frame"] = callback }
    override fun tick(callback: UIButton.() -> Unit) { eventCallback["tick"] = callback }
    override fun tickUpdate() { eventCallback["tick"]?.invoke(this) }

    override fun apply(uiScreen: UIScreen) {
        uiScreen.applyUI(drawable)
    }
}