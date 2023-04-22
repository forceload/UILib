package io.github.forceload.uilib.screen

import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.generator.UIObject
import io.github.forceload.uilib.wrapper.Point2D
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.Text
import java.awt.Dimension

class TestScreen(title: String) : UIScreen(title) {

    var clickerNumber = 0
    private lateinit var ui: UIObject
    override fun init() {
        ui = buildUI {
            button("gui.uilib.button_default") {
                size = Dimension(100, 100)
                position = Point2D(width / 2, height / 2)

                click {
                    clickerNumber++
                    // client!!.setScreen(TitleScreen())
                }

                frame {
                    clickerNumber++
                }
            }

            text("UI Clicker %s", 0) {
                position = Point2D(width / 2, height / 2 + 60)
                update {
                    this.text = Text.translatable("UI Clicker %s", clickerNumber)
                }
            }
        }

        ui.apply(this)
    }

    override fun render(matrices: MatrixStack?, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(matrices)
        matrices?.let { ui.render(it, mouseX, mouseY, delta) }

        super.render(matrices, mouseX, mouseY, delta)
    }

    override fun tick() { ui.tick() }
}