package io.github.forceload.uilib.generator

import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.Drawable
import net.minecraft.client.gui.Element
import net.minecraft.client.gui.Selectable
import net.minecraft.client.gui.screen.Screen
import net.minecraft.text.Text

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