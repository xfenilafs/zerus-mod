package ru.starfarm.zerus.mod.impl;

import ru.starfarm.client.api.ClientApi;
import ru.starfarm.zerus.mod.ZerusMod;

public interface IModule {

    static <M extends IModule> M getModule(Class<M> moduleClass) {
        return ZerusMod.getInstance().getModuleManager().getModule(moduleClass);
    }

    default void onEnable(ClientApi api) throws Throwable {}

    default void onDisable(ClientApi api) throws Throwable {}

    void enable();

    void disable();

    boolean isEnabled();

}
