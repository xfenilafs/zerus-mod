package ru.starfarm.zerus.mod.impl.modules.player;

import ru.starfarm.zerus.mod.ZerusMod;
import ru.starfarm.zerus.mod.impl.IModule;

public interface IPlayerInfoModule extends IModule {

    static IPlayerInfoModule get() {
        return ZerusMod.getInstance().getModuleManager().getModule(IPlayerInfoModule.class);
    }
}
