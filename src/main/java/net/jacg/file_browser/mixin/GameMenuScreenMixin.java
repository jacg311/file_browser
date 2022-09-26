package net.jacg.file_browser.mixin;

import net.jacg.file_browser.util.FileBrowserScreenBuilder;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Paths;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void addButton(CallbackInfo ci) {
        this.addDrawableChild(
                new ButtonWidget(
                        this.width / 2 - 200,
                        this.height / 4 + 48 + 72 + 12,
                        98,
                        20,
                        Text.literal("click me"),
                        button -> this.client.setScreen(
                                FileBrowserScreenBuilder.create()
                                        .setRootPath(Paths.get("/home/jacg/Documents/Minecraft/file_browser/run"))
                                        .build()
                        )
                )
        );
    }
}
