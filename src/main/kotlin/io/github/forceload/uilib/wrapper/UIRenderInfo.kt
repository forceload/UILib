package io.github.forceload.uilib.wrapper

import io.github.forceload.uilib.util.Point2D
import net.minecraft.client.gui.DrawContext
data class UIRenderInfo(val drawContext: DrawContext, val mousePos: Point2D<Int, Int>, val deltaTime: Float)