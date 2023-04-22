package io.github.forceload.uilib

import com.mojang.logging.LogUtils
import io.github.forceload.uilib.screen.TestScreen
import net.fabricmc.api.EnvType
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.MinecraftClient

@Suppress("unused")
object UILib: ModInitializer {
    const val MOD_ID = "uilib"
    private lateinit var loaderInstance: FabricLoader
    private lateinit var client: MinecraftClient

    val logger = LogUtils.getLogger()
    var gameTick = 0L

    override fun onInitialize() {
        client = MinecraftClient.getInstance()
        loaderInstance = FabricLoader.getInstance()

        if (loaderInstance.environmentType == EnvType.CLIENT) {
            ClientTickEvents.END_CLIENT_TICK.register(ClientTickEvents.EndTick {
                gameTick++
                if (gameTick == 1L) {
                    client.setScreen(TestScreen("HI"))
                }
            })
        }
    }
}