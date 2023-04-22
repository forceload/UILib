package io.github.forceload.uilib.generator

import io.github.forceload.uilib.widget.UIButton

class UIGenerator {

    val mainWidget = UIObject()
    fun button(init: UIButton.() -> Unit) {
        val button = UIButton()

        button.init()
        button.generate()

        mainWidget.addChild(button)
    }
}