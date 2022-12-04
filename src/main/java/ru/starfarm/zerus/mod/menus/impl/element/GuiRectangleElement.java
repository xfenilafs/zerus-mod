package ru.starfarm.zerus.mod.menus.impl.element;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.starfarm.zerus.mod.ZerusMod;
import ru.starfarm.zerus.mod.menus.impl.GuiElement;

@Data @Accessors(chain = true)
public class GuiRectangleElement implements GuiElement {

    protected int x, y, width, height, color;
    protected boolean visible = true, hovered, enabled = true;

    public GuiRectangleElement(int color, int x, int y, int width, int height) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public GuiElement setPos(int x, int y) {
        return setX(x).setY(y);
    }

    @Override
    public GuiElement setSize(int width, int height) {
        return setWidth(width).setHeight(height);
    }

    @Override
    public void keyTyped(int i) {

    }

    @Override
    public void draw(int mx, int my) {
        if (!visible)
            hovered = false;
        else ZerusMod.getInstance().getApi().overlayRenderer().drawRect(x, y, width + x, height + y, color);
    }

    @Override
    public void mouseReleased(int button, int mx, int my) {

    }

    @Override
    public void mousePressed(int button, int mx, int my) {

    }

}
