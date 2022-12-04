package ru.starfarm.zerus.mod.impl;

import lombok.val;
import ru.starfarm.client.api.ClientApi;
import ru.starfarm.zerus.mod.ZerusMod;
import ru.starfarm.zerus.mod.impl.modules.IStatisticModule;
import ru.starfarm.zerus.mod.impl.modules.StatisticModule;
import ru.starfarm.zerus.mod.impl.modules.player.IPlayerInfoModule;
import ru.starfarm.zerus.mod.impl.modules.player.PlayerInfoModule;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ModuleManager {

    private final ClientApi api;

    private final Map<Class<? extends IModule>, IModule> modules = new HashMap<>();

    public ModuleManager(ClientApi api) {
        this.api = api;

        registerModule(IStatisticModule.class, new StatisticModule());

        api.scheduler().after(40, task -> {
            registerModule(IPlayerInfoModule.class, new PlayerInfoModule());
        });
    }

    public <M extends IModule> M registerModule(Class<M> moduleClass, M module) {
        modules.merge(moduleClass, module, (__, ___) -> {
            throw new IllegalStateException("Module already registered: " + moduleClass.getSimpleName());
        });

        if (!module.isEnabled()) module.enable();

        return module;
    }

    public <M extends IModule> M getModule(Class<M> moduleClass) {
        return (M) modules.get(moduleClass);
    }

    public <M extends IModule> M unregister(Class<M> moduleClass) {
        val module = modules.remove(moduleClass);

        if (module == null) throw new IllegalStateException("Module not registered: " + moduleClass.getSimpleName());

        if (module.isEnabled()) module.disable();

        return (M) module;
    }
}
