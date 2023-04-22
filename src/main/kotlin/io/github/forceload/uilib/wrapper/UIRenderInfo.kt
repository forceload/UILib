package io.github.forceload.uilib.wrapper

import net.minecraft.client.util.math.MatrixStack
data class UIRenderInfo(val matrixStack: MatrixStack, val mousePos: Point2D<Int, Int>, val deltaTime: Float)