package io.github.forceload.uilib.screen

import io.github.forceload.uilib.UILib
import io.github.forceload.uilib.generator.UIScreen
import io.github.forceload.uilib.generator.UIObject
import io.github.forceload.uilib.util.Point2D
import io.github.forceload.uilib.util.format
import net.minecraft.client.gui.DrawContext
import net.minecraft.text.Text
import net.minecraft.util.Util
import java.awt.Dimension

class TestScreen(title: String) : UIScreen(title) {
    private var clickerTime = 0
    private var clickerNumber = 0

    private var intervalClicker = 0.0
    private var realTime = Util.getMeasuringTimeMs()
    private var time = 0.0

    private lateinit var ui: UIObject
    override fun init() {
        ui = buildUI {
            button("gui.uilib.button_default") {
                size = Dimension(100, 100)
                position = Point2D(width / 2, height / 2)

                click { clickerNumber++ }
                frame {
                    if (clickerTime != 0) {
                        time += this.deltaTime / 20
                        if (time > 1.0 / clickerTime) {
                            val temp = (time / (1.0 / clickerTime)).toInt()
                            clickerNumber += temp

                            time -= (1.0 / clickerTime) * temp
                        }
                    } else {
                        time = 0.0
                        realTime = Util.getMeasuringTimeMs()
                    }
                }
            }

            text("UI Clicker %s", 0) {
                position = Point2D(width / 2, height / 2 + 55)
                tick {
                    this.text = Text.translatable("gui.${UILib.MOD_ID}.test.clicker")
                        .append(Text.translatable(" %s", clickerNumber))
                }
            }

            text("0.0") {
                position = Point2D(width / 2, height / 2 + 65)
                frame {
                    this.text = Text.of("Real Time: ${
                        ((Util.getMeasuringTimeMs() - realTime) / 1000.0).format(2)}")
                }
            }

            text("0.0") {
                position = Point2D(width / 2, height / 2 + 75)
                frame {
                    this.text = Text.of("Difference: ${
                        ((Util.getMeasuringTimeMs() - realTime) / 1000.0 - clickerNumber).format(2)}")
                }
            }

            var sliderText = Text.translatable("gui.${UILib.MOD_ID}.test.auto_clicker")
                    .append(Text.translatable(" %s", intervalClicker.toInt()))

            slider(sliderText, value = intervalClicker, max = 200.0) {
                position = Point2D(width / 2, height / 2 - 70)

                update {
                    intervalClicker = this.value
                    clickerTime = this.value.toInt()

                    sliderText = Text.translatable("gui.${UILib.MOD_ID}.test.auto_clicker")
                        .append(Text.translatable(" %s", intervalClicker.toInt()))

                    this.text = sliderText
                }
            }
        }

        ui.apply(this)
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        this.renderBackground(context)
        context.let { ui.render(it, mouseX, mouseY, delta) }

        super.render(context, mouseX, mouseY, delta)
    }

    override fun tick() { ui.update() }
}