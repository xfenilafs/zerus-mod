package ru.starfarm.zerus.mod.menus.impl;

import lombok.Getter;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.input.Keyboard;

import java.util.LinkedList;
import java.util.List;

@Getter
public abstract class GuiScreen extends net.minecraft.client.gui.GuiScreen {

    protected final List<GuiElement> elements = new LinkedList<>();

    @Override
    public void initGui() {
        elements.clear();
        handleInit(width, height);
    }

    public void addElement(GuiElement element) {
        elements.add(element);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        try {
            for (GuiElement element : elements)
                element.draw(mouseX, mouseY);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        try {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                mc.displayGuiScreen(null);
                if (mc.currentScreen == null) mc.setIngameFocus();
            } else for (GuiElement element : elements) element.keyTyped(keyCode);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        try {
            for (GuiElement element : elements)
                element.mousePressed(mouseButton, mouseX, mouseY);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        try {
            for (GuiElement element : elements)
                element.mouseReleased(state, mouseX, mouseY);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onGuiClosed() {
        handleClose();
    }

    abstract public void handleClose();

    abstract public void handleInit(int width, int height);

    public void display() {
        FMLClientHandler.instance().showGuiScreen(this);
    }

}