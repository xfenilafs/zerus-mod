package ru.starfarm.zerus.mod.menus;

import lombok.val;
import org.lwjgl.Sys;
import ru.starfarm.client.api.util.BufUtil;
import ru.starfarm.zerus.mod.ZerusMod;
import ru.starfarm.zerus.mod.impl.modules.IStatisticModule;
import ru.starfarm.zerus.mod.impl.settings.Settings;
import ru.starfarm.zerus.mod.menus.impl.GuiElement;
import ru.starfarm.zerus.mod.menus.impl.GuiScreen;
import ru.starfarm.zerus.mod.menus.impl.element.GuiButtonElement;
import ru.starfarm.zerus.mod.menus.impl.element.GuiLabelElement;
import ru.starfarm.zerus.mod.menus.impl.element.GuiRangeElement;
import ru.starfarm.zerus.mod.menus.impl.element.GuiRectangleElement;
import ru.starfarm.zerus.mod.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SettingsMenu extends GuiScreen {

    Map<Settings, Object> settings = new ConcurrentHashMap<>();


    @Override
    public void handleInit(int width, int height) {
        val settings = Arrays.asList(Settings.values());
        int startY = getSettingsStartY(settings);

        addElement(new GuiRectangleElement(Util.rgba(0, 0, 0, 100), width / 2 - 215, startY, 430, settings.size() * 30 + 10));
        settings.forEach(setting -> {
            val element = getElement(setting);

            addElement(new GuiLabelElement(
                    setting.name, width / 2 - 205,
                    startY + 20 + setting.ordinal() * 30 - ZerusMod.getInstance().getApi().fontRenderer().height() / 2)
            );
            addElement(element.setPos(width / 2 + 205 - element.getWidth(), startY + 10 + 30 * setting.ordinal()));
        });
    }

    @Override
    public void handleClose() {
        settings.forEach((setting, value) -> {
            ZerusMod.getInstance().getApi().payload().send("zerus:usetting", buffer -> {
                BufUtil.writeInt(buffer, setting.ordinal());
                setting.consumer.accept(buffer, value);
            });

            IStatisticModule.get().setSetting(setting, value);
        });
    }

    private int getSettingsStartY(List<Settings> features) {
        return 30 + (features.size() / 4 + (features.size() % 4 != 0 ? 1 : 0)) * 30;
    }

    public GuiElement getElement(Settings setting) {
        val value = IStatisticModule.get().getSetting(setting);

        if (value instanceof Boolean) {
            return new GuiButtonElement((boolean) value ? "§aВкл" : "§cВыкл", 0, 0, 100, 20, element -> {
                boolean settingValue = !(boolean) IStatisticModule.get().getSetting(setting);
                element.setText(settingValue ? "§aВкл" : "§cВыкл");

                IStatisticModule.get().setSetting(setting, settingValue);
                settings.put(setting, settingValue);
            });
        }

        return new GuiRangeElement<>(IntStream.range(1, setting.maxValue).boxed().collect(Collectors.toList()), 0, 0, 100, 20, element -> {
            IStatisticModule.get().setSetting(setting, element.getCurrentPosition());
            settings.put(setting, element.getCurrentPosition());
        }, (int) value);
    }

}