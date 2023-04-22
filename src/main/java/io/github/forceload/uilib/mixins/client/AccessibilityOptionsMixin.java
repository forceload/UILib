package io.github.forceload.uilib.mixins.client;

import io.github.forceload.uilib.UILib;
import io.github.forceload.uilib.screen.TestScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ConfirmLinkScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.SimpleOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AccessibilityOptionsScreen.class)
@Environment(EnvType.CLIENT)
public abstract class AccessibilityOptionsMixin extends SimpleOptionsScreen {

    private static SimpleOption<?>[] getOptions(GameOptions gameOptions) {
        return new SimpleOption[]{
            gameOptions.getNarrator(), gameOptions.getShowSubtitles(), gameOptions.getHighContrast(),
            gameOptions.getAutoJump(), gameOptions.getTextBackgroundOpacity(),
            gameOptions.getBackgroundForChatOnly(), gameOptions.getChatOpacity(),
            gameOptions.getChatLineSpacing(), gameOptions.getChatDelay(),
            gameOptions.getNotificationDisplayTime(), gameOptions.getSneakToggled(),
            gameOptions.getSprintToggled(), gameOptions.getDistortionEffectScale(),
            gameOptions.getFovEffectScale(), gameOptions.getDarknessEffectScale(),
            gameOptions.getDamageTiltStrength(), gameOptions.getGlintSpeed(),
            gameOptions.getGlintStrength(), gameOptions.getHideLightningFlashes(),
            gameOptions.getMonochromeLogo(), gameOptions.getPanoramaSpeed()
        };
    }

    public AccessibilityOptionsMixin(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions, Text.translatable("options.accessibility.title"), getOptions(gameOptions));
    }

    @Inject(method = "initFooter()V", at = @At("TAIL"), cancellable = true)
    protected void initFooter(CallbackInfo ci) {
        ci.cancel();

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("options.accessibility.link"), (button) -> {
            this.client.setScreen(new ConfirmLinkScreen((openInBrowser) -> {
                if (openInBrowser) {
                    Util.getOperatingSystem().open("https://aka.ms/MinecraftJavaAccessibility");
                }

                this.client.setScreen(this);
            }, "https://aka.ms/MinecraftJavaAccessibility", true));
        }).dimensions(this.width / 2 - 155, this.height - 27, 150, 20).build());
        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, (button) -> {
            this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 + 5, this.height - 27, 150, 20).build());

        this.addDrawableChild(ButtonWidget.builder(Text.translatable("gui.uilib.uitest"), (button) -> {
            UILib.INSTANCE.getClient().setScreen(new TestScreen("UI Test"));
        }).dimensions(this.width / 2 + 155 - 60, 6, 60, 20).build());
    }
}
