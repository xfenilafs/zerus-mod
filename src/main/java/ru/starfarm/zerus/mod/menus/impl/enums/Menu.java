package ru.starfarm.zerus.mod.menus.impl.enums;

import ru.starfarm.zerus.mod.menus.SettingsMenu;

public enum Menu {
    SETTINGS {
        @Override
        public void display() {
            new SettingsMenu().display();
        }
    }
    ;

    public abstract void display();
}
