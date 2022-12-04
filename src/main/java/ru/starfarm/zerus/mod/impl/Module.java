package ru.starfarm.zerus.mod.impl;

import lombok.Getter;
import ru.starfarm.zerus.mod.ZerusMod;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Module implements IModule {

    @Getter
    protected boolean enabled;
    protected Logger logger = Logger.getLogger(getClass().getSimpleName());

    @Override
    public final void enable() {
        try {
            if (enabled) throw new IllegalStateException("Module already enabled");

            enabled = true;

            onEnable(mod().getApi());

            logger.info("Module enabled.");
        } catch (Throwable throwable) {
            logger.log(Level.SEVERE, "Error while enabling module", throwable);
        }
    }

    @Override
    public final void disable() {
        try {
            if (!enabled) throw new IllegalStateException("Module already disabled");

            enabled = false;

            onDisable(mod().getApi());

            logger.info("Module disabled.");
        } catch (Throwable throwable) {
            logger.log(Level.SEVERE, "Error while disabling module", throwable);
        }
    }

    public ZerusMod mod() {
        return ZerusMod.getInstance();
    }

}
