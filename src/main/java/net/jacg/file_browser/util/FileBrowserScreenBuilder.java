package net.jacg.file_browser.util;

import net.jacg.file_browser.client.screen.FileBrowserScreen;
import net.minecraft.text.Text;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public final class FileBrowserScreenBuilder {
    private boolean shouldPause;
    private boolean showFiles;
    private boolean shouldSelectFolders;
    private Path rootPath;
    private final List<String> extensionWhiteList = new ArrayList<>();
    private final Text title;
    private int backGroundTint;

    public static FileBrowserScreenBuilder create() {
        return new FileBrowserScreenBuilder();
    }

    private FileBrowserScreenBuilder() {
        this.shouldPause = false;
        this.showFiles = true;
        this.shouldSelectFolders = false;
        this.title = Text.literal("ble");
        this.backGroundTint = 0x4F232323;
    }

    /**
     * If the game should pause in singleplayer while in screen <br>
     * Default: {@code false}
     */
    public FileBrowserScreenBuilder shouldPause(boolean shouldPause) {
        this.shouldPause = shouldPause;
        return this;
    }

    /**
     * If the dialog should also show files <br>
     * Default: {@code true}
     */
    public FileBrowserScreenBuilder shouldShowFiles(boolean showFiles) {
        this.showFiles = showFiles;
        return this;
    }

    /**
     * If the user should select folders instead of files <br>
     * Default: {@code false}
     */
    public FileBrowserScreenBuilder shouldSelectFolders(boolean shouldSelectFolders) {
        this.shouldSelectFolders = shouldSelectFolders;
        return this;
    }

    /**
     * Sets the path where the browser opens. <br>
     * May not be {@code null}
     */
    public FileBrowserScreenBuilder setRootPath(Path rootPath) {
        this.rootPath = rootPath;
        return this;
    }

    public FileBrowserScreenBuilder setBackGroundTint(int backGroundTint) {
        this.backGroundTint = backGroundTint;
        return this;
    }

    public FileBrowserScreenBuilder addToExtensionWhiteList(String... extensions) {
        addToExtensionWhiteList(Arrays.asList(extensions));
        return this;
    }

    /**
     * @param extensions
     * @return
     */
    public FileBrowserScreenBuilder addToExtensionWhiteList(List<String> extensions) {
        this.extensionWhiteList.addAll(extensions);
        return this;
    }

    public FileBrowserScreen build() {
        if (rootPath == null) throw new RuntimeException("You must set a root path in the file explorer!");
        return new FileBrowserScreen(this.title, shouldPause, showFiles, rootPath, extensionWhiteList, backGroundTint);
    }
}
