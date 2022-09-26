package net.jacg.file_browser.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.jacg.file_browser.util.FileBrowserScreenBuilder;

@Environment(EnvType.CLIENT)
public class FileBrowserClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
    }
}
