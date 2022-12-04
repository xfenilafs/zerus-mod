package ru.starfarm.zerus.mod.menus.impl.element;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.starfarm.zerus.mod.ZerusMod;
import ru.starfarm.zerus.mod.menus.impl.GuiElement;

@Data @Accessors(chain = true)
public class GuiLabelElement implements GuiElement {

    protected String text;
    protected float scale = 1f;
    protected int x, y;
    protected boolean visible = true, hovered, enabled = true;

    public GuiLabelElement(String text, int x, int y, float scale) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public GuiLabelElement(String text, int x, int y) {
        this(text, x, y, 1f);
    }

    @Override
    public GuiElement setPos(int x, int y) {
        return setX(x).setY(y);
    }

    @Override
    public GuiElement setSize(int width, int height) {
        return this;
    }

    @Override
    public void draw(int mx, int my) {
        if (!visible)
            hovered = false;
        else {
            hovered = isInside(mx, my);
            ZerusMod.getInstance().getApi().overlayRenderer().scaled(scale, resolution ->
                    ZerusMod.getInstance().getApi().fontRenderer().drawStringWithShadow(x / scale, y / scale, text, -1)
        );
        }
    }

    @Override
    public void keyTyped(int i) {

    }

    @Override
    public void mouseReleased(int button, int mx, int my) {

    }

    @Override
    public void mousePressed(int button, int mx, int my) {

    }

    @Override
    public GuiElement setWidth(int width) {
        return this;
    }

    @Override
    public GuiElement setHeight(int height) {
        return this;
    }

    @Override
    public int getWidth() {
        return ZerusMod.getInstance().getApi().fontRenderer().width(text == null ? "" : text);
    }

    @Override
    public int getHeight() {
        return ZerusMod.getInstance().getApi().fontRenderer().height();
    }

}
