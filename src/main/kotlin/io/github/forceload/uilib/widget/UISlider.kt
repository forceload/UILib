package io.github.forceload.uilib.widget

import io.github.forceload.uilib.UILib
import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.widget.internal.UISliderWidget
import io.github.forceload.uilib.util.Point2D
import io.github.forceload.uilib.wrapper.UIRenderInfo
import net.minecraft.text.Text
import java.awt.Dimension

class UISlider(var text: Text = UIButton.defaultText, value: Double = 0.0, var maxValue: Double = defaultMaxValue): UIWidget<UISlider> {
    companion object {
        val defaultText = Text.translatable("gui.${UILib.MOD_ID}.slider_default")!!
        const val defaultMaxValue = 1.0
    }

    var value: Double = 0.0
        set(value) { field = value / maxValue }
        get() { return field * maxValue }

    init {
        this.value = value
    }

    constructor(text: String): this(Text.translatable(text))
    override var eventCallback = mutableMapOf(
        "tick" to genCallback { },
        "frame" to genCallback { },
        "valueUpdate" to genCallback { }
    )

    var centered = true
    var deltaTime: Float = 0.0F
    var position = Point2D(0, 0)
    var size: Dimension = Dimension(200, 20)
    private lateinit var drawable: UISliderWidget<UISlider>

    override fun render(renderInfo: UIRenderInfo) {
        deltaTime = renderInfo.deltaTime
        eventCallback["frame"]?.invoke(this)
        syncDrawable()
    }
    override fun frame(callback: UISlider.() -> Unit) { eventCallback["frame"] = callback }
    override fun tickUpdate() {
        eventCallback["tick"]?.invoke(this)
        syncDrawable()
    }
    fun update(callback: UISlider.() -> Unit) {
        eventCallback["valueUpdate"] = callback
    }

    override var visible: Boolean = true
        set(value) { field = value; drawable.visible = value && !_forcedInvisible }

    override var _forcedInvisible: Boolean = false
        set(value) { field = value; drawable.visible = visible && !value }

    override fun generate() {
        drawable = UISliderWidget(
            position - Point2D(size.width / 2, size.height / 2) * centered.toInt(),
            size, text, 0.0, this
        ) {
            this.value = drawable.value * maxValue
            eventCallback["valueUpdate"]?.invoke(this)
            syncDrawable()
        }
    }
    override fun apply(uiScreen: UIScreen) { uiScreen.applyUI(drawable) }
    override fun tick(callback: UISlider.() -> Unit) { eventCallback["tick"] = callback }
    private fun syncDrawable() {
        drawable.value = this.value / maxValue

        if (drawable.text.string != text.string) {
            drawable.text = text
        }

        drawable.update()
    }
}

private fun Boolean.toInt() = if (this) { 1 } else { 0 }
