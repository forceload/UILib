package io.github.forceload.uilib.generator

import io.github.forceload.uilib.widget.UIButton
import io.github.forceload.uilib.widget.UIText
import net.minecraft.text.Text

class UIGenerator {

    val mainWidget = UIObject()

    fun button(text: String, vararg args: Any, init: UIButton.() -> Unit) =
        button(Text.translatable(text, *args), init)

    fun button(text: Text = UIButton.defaultText, init: UIButton.() -> Unit) {
        val button = UIButton(text)

        button.init()
        button.generate()

        mainWidget.addChild(button)
    }

    fun text(text: String, vararg args: Any, init: UIText.() -> Unit) =
        text(Text.translatable(text, *args), init)
    fun text(text: Text = UIText.defaultText, init: UIText.() -> Unit) {
        val uiText = UIText(text)

        uiText.init()
        mainWidget.addChild(uiText)
    }
}