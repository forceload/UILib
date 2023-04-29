package io.github.forceload.uilib.widget.internal

import io.github.forceload.uilib.util.Point2D
import net.minecraft.client.gui.widget.SliderWidget
import net.minecraft.text.Text
import java.awt.Dimension

class UISliderWidget<T>(
    var pos: Point2D<Int, Int>, var size: Dimension,
    var text: Text, var value: Double, private val thisObject: T, private var callback: T.() -> Unit
) : SliderWidget(pos.x, pos.y, size.width, size.height, text, value) {
    override fun updateMessage() {
        this.message = this.text
        super.setMessage(this.message)
    }

    override fun applyValue() {
        this.value = super.value
        callback.invoke(thisObject)
    }

    fun update() { this.updateMessage() }
}