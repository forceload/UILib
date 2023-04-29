package io.github.forceload.uilib.wrapper

import io.github.forceload.uilib.util.Point2D
import net.minecraft.client.util.math.MatrixStack
data class UIRenderInfo(val matrixStack: MatrixStack, val mousePos: Point2D<Int, Int>, val deltaTime: Float)