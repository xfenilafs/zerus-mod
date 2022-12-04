package ru.starfarm.zerus.mod.menus.impl.element;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.starfarm.zerus.mod.ZerusMod;
import ru.starfarm.zerus.mod.menus.impl.GuiElement;
import ru.starfarm.zerus.mod.util.Util;

import java.util.List;
import java.util.function.Consumer;

@Data @Accessors(chain = true)
public class GuiRangeElement<T> implements GuiElement {

    public static int
            BOX_COLOR = Util.rgb(23, 24, 31),
            HOVERED_CURSOR_COLOR = Util.rgb(56, 57, 65),
            CURSOR_COLOR = Util.rgb(38, 72, 140);

    protected List<T> elements;
    protected Consumer<GuiRangeElement<T>> actionHandler;
    protected int x, y, width, height;
    protected boolean visible = true, hovered, enabled = true;

    private double step = 0;
    private double[] cursorPositions = {};
    private int currentPosition = 0;
    private boolean captured;

    public GuiRangeElement(List<T> elements, int x, int y, int width, int height, Consumer<GuiRangeElement<T>> actionHandler, int selectedIndex) {
        this.elements = elements;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.actionHandler = actionHandler;
        currentPosition = selectedIndex;
        update();
    }

    public GuiRangeElement(List<T> elements, int x, int y, int width, int height, Consumer<GuiRangeElement<T>> actionHandler) {
        this(elements, x, y, width, height, actionHandler, 0);
    }

    public GuiRangeElement(List<T> elements, int x, int y, int width, int height, int selectedIndex) {
        this(elements, x, y, width, height, null, selectedIndex);
    }

    public GuiRangeElement(List<T> elements, int x, int y, int width, int height) {
        this(elements, x, y, width, height, null);
    }

    public GuiElement setCursorPositions(int x, int y) {
        return setX(x).setY(y);
    }

    @Override
    public GuiElement setPos(int x, int y) {
        return setX(x).setY(y);
    }

    @Override
    public GuiElement setSize(int width, int height) {
        return setWidth(width).setHeight(height);
    }

    public GuiRangeElement<T> update() {
        step = width / ((double) elements.size());
        cursorPositions = new double[elements.size()];
        for (int i = 0; i < elements.size(); i++)
            cursorPositions[i] = step * (i + 1) + (i == elements.size() - 1 ? width - elements.size() * step : 0);
        return this;
    }

    public T getCurrentElement() {
        return elements.get(currentPosition);
    }

    public GuiRangeElement<T> setElements(List<T> elements) {
        this.elements = elements;
        return update();
    }

    public GuiRangeElement<T> setWidth(int width) {
        this.width = width;
        return update();
    }

    @Override
    public void draw(int mx, int my) {
        if (elements.isEmpty() || !visible) {
            hovered = false;
        } else {
            if (captured) {
                if (mx < getX() || mx > x + width)
                    currentPosition = mx < getX() ? 0 : cursorPositions.length - 1;
                else {
                    double last = x - 1;
                    for (int i = 0; i < cursorPositions.length; i++) {
                        double cx = cursorPositions[i] + x;
                        if (mx >= last && mx <= cx) {
                            currentPosition = i;
                            if (actionHandler != null) actionHandler.accept(this);
                            break;
                        } else last = cx + 1;
                    }
                }
            }

            ZerusMod.getInstance().getApi().overlayRenderer().drawRect(x, y, width + x, height + y, BOX_COLOR);
            ZerusMod.getInstance().getApi().overlayRenderer().drawRect(
                    (int) (x + cursorPositions[currentPosition] - step / 2 - 2),
                    y - 1, (int) (4 + (x + cursorPositions[currentPosition] - step / 2 - 2)), 1 + height + y, hovered ? HOVERED_CURSOR_COLOR : CURSOR_COLOR
            );

            ZerusMod.getInstance().getApi().fontRenderer().drawCenteredStringWithShadow(
                    x + width / 2f,
                    y + height / 2f - ZerusMod.getInstance().getApi().fontRenderer().height() / 2f,
                    getCurrentElement().toString(), -1
            );
        }
    }

    @Override
    public void keyTyped(int i) {

    }

    @Override
    public void mouseReleased(int button, int mx, int my) {
        captured = false;
    }

    @Override
    public void mousePressed(int button, int mx, int my) {
        captured = isInside(mx, my);
    }

}
