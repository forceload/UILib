package io.github.forceload.uilib.generator

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.Drawable
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.Selectable
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

/**
 * UIScreen is one of the most essential parts of using this library
 *
 * ## Example
 * ```kotlin
 * // This is a sample screen with a square button which says "Sample" in the center
 * class SampleScreen(title: String): Screen(title) {
 *     override fun init() {
 *         ui = buildUI { //Build Main UI
 *             button("Sample") {
 *                 position = Point2D(width / 2, height / 2)
 *                 centered = true
 *             }
 *         }
 *     }
 *
 *     override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
 *         this.renderBackground(context)
 *         context.let { ui.render(it, mouseX, mouseY, delta) } // Render UI Components
 *
 *         super.render(context, mouseX, mouseY, delta)
 *     }
 *
 *     override fun tick() { ui.update() } // Update UI (Useless in this example)
 * }
 * ```
 */
open class UIScreen(title: Text?) : Screen(title) {
    constructor(title: String?) : this(Text.translatable(title))

    companion object {
        @Suppress("MemberVisibilityCanBePrivate")
        fun buildUI(uiBlock: UIGenerator.() -> Unit): UIObject = buildUI(uiBlock)
    }

    fun buildUI(uiBlock: UIGenerator.() -> Unit): UIObject {
        val uiGenerator = UIGenerator()
        uiGenerator.uiBlock()

        return uiGenerator.mainWidget
    }

    fun <T> applyUI(drawable: T) where T : Element?, T : Drawable?, T : Selectable? {
        this.addDrawableChild(drawable)
    }

    fun textRenderer(): TextRenderer = this.textRenderer
}