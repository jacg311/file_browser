package net.jacg.file_browser.client.screen;

import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class FileBrowserScreen extends Screen {
    private final boolean shouldPause;
    private final boolean showFiles;
    private final Path rootPath;
    private final List<String> extensionWhiteList;
    private final int backGroundGradient;

    private Path currentPath;
    private List<ButtonWidget> currentElements;

    public FileBrowserScreen(Text title, boolean shouldPause, boolean showFiles, Path rootPath, List<String> extensionWhiteList, int backGroundGradient) {
        super(title);
        this.shouldPause = shouldPause;
        this.showFiles = showFiles;
        this.rootPath = rootPath;
        this.extensionWhiteList = extensionWhiteList;
        this.backGroundGradient = backGroundGradient;

        this.currentPath = rootPath;
    }

    @Override
    public boolean shouldPause() {
        return shouldPause;
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        super.renderBackground(matrices);
        DrawableHelper.fill(matrices, 35, 35, 100, 100, 0xdcdcdc);
    }

    @Override
    protected void init() {
        updateCurrentElements();
        for (ButtonWidget buttonWidget : this.currentElements) {
            this.addDrawableChild(buttonWidget);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.client.world == null) {
            this.renderBackground(matrices);
        } else {
            this.fillGradient(matrices, 0, 0, width, height, backGroundGradient, backGroundGradient);
        }
        DrawableHelper.fill(matrices, 0, 0, width / 2, height / 2, 0xE0E0E0);

        this.textRenderer.draw(matrices, currentPath.toString(), 40, 40, 0xE0E0E0);
        super.render(matrices, mouseX, mouseY, delta);
    }

    private void updateCurrentElements() {
        try (Stream<Path> stream = Files.list(currentPath)) {
            AtomicInteger i = new AtomicInteger(1);
            List<ButtonWidget> list = new ArrayList<>(stream
                    .filter(path -> (Files.isDirectory(path)) || (Files.isRegularFile(path) && showFiles && this.extensionWhiteList.contains(path.getFileName().toString())))
                    .map(path -> {
                        String text = path.getFileName().toString();
                        var btn = new ButtonWidget(50, i.get() * 14 + 50, this.textRenderer.getWidth('/' + text) + 5, this.textRenderer.fontHeight + 3, Text.literal('/' + text), button -> {
                            currentPath = currentPath.resolve(text);
                            updateCurrentElements();
                            clearAndInit();
                        });
                        i.getAndIncrement();
                        return btn;
                    }).toList());

            if (currentPath != currentPath.getRoot()) {

                list.add(0, new ButtonWidget(50, 50, this.textRenderer.getWidth("/..") + 5, this.textRenderer.fontHeight + 3, Text.literal("/,,"), button -> {
                    currentPath = currentPath.getParent();
                    clearAndInit();
                }));
            }

            this.currentElements = list;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
