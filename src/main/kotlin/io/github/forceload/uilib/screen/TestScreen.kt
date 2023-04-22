package io.github.forceload.uilib.screen

import io.github.forceload.uilib.UILib
import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.generator.UIObject
import io.github.forceload.uilib.wrapper.Point2D
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.awt.Dimension

class TestScreen(title: String) : UIScreen(title) {
    private lateinit var ui: UIObject
    override fun init() {
        ui = buildUI {
            button {
                text = Text.translatable("gui.uilib.button_default")

                size = Dimension(100, 100)
                position = Point2D(width / 2, height / 2)

                click {
                    UILib.logger.info("UI Clicker")
                    // client!!.setScreen(TitleScreen())
                }
            }
        }

        ui.apply(this)
    }

    override fun render(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        super.render(matrices, mouseX, mouseY, delta)
    }
}