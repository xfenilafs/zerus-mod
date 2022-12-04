package ru.starfarm.zerus.mod;

import lombok.Getter;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import ru.starfarm.client.api.ClientApi;
import ru.starfarm.client.api.ClientMod;
import ru.starfarm.zerus.mod.impl.ModuleManager;

@Getter
public class ZerusMod implements ClientMod {

    @Getter
    public static ZerusMod instance;

    public ClientApi api;
    public ModuleManager moduleManager;

    @Override
    public void enable(ClientApi clientApi) {
        instance = this;
        api = clientApi;
        moduleManager = new ModuleManager(clientApi);

        api.eventBus().register(RenderGameOverlayEvent.Pre.class, event -> {
            switch (event.getType()) {
                case ARMOR:
                case HEALTH:
                case AIR:
                case EXPERIENCE:
                    event.setCanceled(true);
            }
        });
    }

    @Override
    public void disable(ClientApi api) {

    }
}