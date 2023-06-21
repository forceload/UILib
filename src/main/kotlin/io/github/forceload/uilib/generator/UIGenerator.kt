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
 * ## Example
 * ```
 * class TestScreen(title: String) : UIScreen(title) {
 *     private var clickerTime = 0
 *     private var clickerNumber = 0
 *
 *     private var intervalClicker = 0.0
 *     private var realTime = Util.getMeasuringTimeMs()
 *     private var time = 0.0
 *
 *     private lateinit var ui: UIObject
 *     override fun init() {
 *         ui = buildUI {
 *             button("gui.uilib.button_default") {
 *                 size = Dimension(100, 100)
 *                 position = Point2D(width / 2, height / 2)
 *
 *                 click { clickerNumber++ }
 *                 frame {
 *                     if (clickerTime != 0) {
 *                         time += this.deltaTime / 20
 *                         if (time >= 1.0 / clickerTime) {
 *                             val temp = (time / (1.0 / clickerTime)).toInt()
 *                             clickerNumber += temp
 *
 *                             time -= (1.0 / clickerTime) * temp
 *                         }
 *                     } else {
 *                         time = 0.0
 *                         realTime = Util.getMeasuringTimeMs()
 *                     }
 *                 }
 *             }
 *
 *             text("UI Clicker %s", 0) {
 *                 position = Point2D(width / 2, height / 2 + 55)
 *                 tick {
 *                     this.text = Text.translatable("gui.${UILib.MOD_ID}.test.clicker")
 *                         .append(Text.translatable(" %s", clickerNumber))
 *                 }
 *             }
 *
 *             text("0.0") {
 *                 position = Point2D(width / 2, height / 2 + 65)
 *                 frame {
 *                     this.text = Text.of("Real Time: ${
 *                         ((Util.getMeasuringTimeMs() - realTime) / 1000.0).format(2)}")
 *                 }
 *             }
 *
 *             text("0.0") {
 *                 position = Point2D(width / 2, height / 2 + 75)
 *                 frame {
 *                     this.text = Text.of("Difference: ${
 *                         ((Util.getMeasuringTimeMs() - realTime) / 1000.0 - clickerNumber).format(2)}")
 *                 }
 *             }
 *
 *             var sliderText = Text.translatable("gui.${UILib.MOD_ID}.test.auto_clicker")
 *                     .append(Text.translatable(" %s", intervalClicker.toInt()))
 *
 *             slider(sliderText, value = intervalClicker, max = 200.0) {
 *                 position = Point2D(width / 2, height / 2 - 70)
 *
 *                 update {
 *                     intervalClicker = this.value
 *                     clickerTime = this.value.toInt()
 *
 *                     sliderText = Text.translatable("gui.${UILib.MOD_ID}.test.auto_clicker")
 *                         .append(Text.translatable(" %s", intervalClicker.toInt()))
 *
 *                     this.text = sliderText
 *                 }
 *             }
 *         }
 *
 *         ui.apply(this)
 *     }
 *
 *     override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
 *         this.renderBackground(context)
 *         context.let { ui.render(it, mouseX, mouseY, delta) }
 *
 *         super.render(context, mouseX, mouseY, delta)
 *     }
 *
 *     override fun tick() { ui.update() }
 * }
 * ```
 * You can see the results of this example in the `Accessibility Settings > UI Test` button
 *
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
     *
     * @See [UIButton] See UIButton for button function usage details.
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