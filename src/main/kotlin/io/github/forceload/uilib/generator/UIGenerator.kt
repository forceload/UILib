package io.github.forceload.uilib.generator

import io.github.forceload.uilib.widget.UIButton
import io.github.forceload.uilib.widget.UIImage
import io.github.forceload.uilib.widget.UISlider
import io.github.forceload.uilib.widget.UIText
import net.minecraft.text.Text
import net.minecraft.util.Identifier

/**
 * UI Generator
 *
 * You should extend [UIScreen] to use this class
 * @see [io.github.forceload.uilib.generator.UIScreen] See UIScreen if you want to see how to use this class correctly
 */
class UIGenerator {

    val mainWidget = UIObject()

    /**
     * Create Button UI on Screen
     *
     * ## Example
     * ```kotlin
     * button("Example") {
     *     // Create button with label "Example"
     * }
     * ```
     */
    fun button(text: String, vararg args: Any, init: UIButton.() -> Unit) =
        button(Text.translatable(text, *args), init)

    fun button(text: Text = UIButton.defaultText, init: UIButton.() -> Unit) {
        val button = UIButton(text)

        button.init()
        button.generate()

        mainWidget.addChild(button)
    }

    /**
     * Create Text on Screen
     *
     * ## Example
     * ```kotlin
     * text("Example") {
     *     // Create text "Example"
     * }
     * ```
     */
    fun text(text: String, vararg args: Any, init: UIText.() -> Unit) =
        text(Text.translatable(text, *args), init)
    fun text(text: Text = UIText.defaultText, init: UIText.() -> Unit) {
        val uiText = UIText(text)

        uiText.init()
        uiText.generate()
        mainWidget.addChild(uiText)
    }

    /**
     * Create Slider on Screen
     *
     * ## Example
     * ```kotlin
     * slider("Example", value = 5, max = 10) {
     *     // Create a slider with text "Example", the current value 5, and the maximum value 10
     * }
     * ```
     */
    fun slider(text: String, vararg args: Any, value: Double = 0.0, max: Double = 1.0, init: UISlider.() -> Unit) =
        slider(Text.translatable(text, *args), value, max, init)
    fun slider(text: Text = UISlider.defaultText, value: Double = 0.0, max: Double = 1.0, init: UISlider.() -> Unit) {
        val uiSlider = UISlider(text, value, max)

        uiSlider.init()
        uiSlider.generate()
        mainWidget.addChild(uiSlider)
    }

    /**
     * Still Working on
     */
    fun image(texture: Identifier, color: Int, init: UIImage.() -> Unit) {
        val uiImage = UIImage(texture, color)

        uiImage.init()
        uiImage.generate()
        mainWidget.addChild(uiImage)
    }
}