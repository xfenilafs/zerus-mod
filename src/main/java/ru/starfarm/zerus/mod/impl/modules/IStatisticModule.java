package ru.starfarm.zerus.mod.impl.modules;

import ru.starfarm.zerus.mod.ZerusMod;
import ru.starfarm.zerus.mod.impl.IModule;
import ru.starfarm.zerus.mod.impl.buffs.Buff;
import ru.starfarm.zerus.mod.impl.buffs.BuffInfo;
import ru.starfarm.zerus.mod.impl.quests.QuestInfo;
import ru.starfarm.zerus.mod.impl.regions.Region;
import ru.starfarm.zerus.mod.impl.settings.Settings;

import java.util.Map;

public interface IStatisticModule extends IModule {

    static IStatisticModule get() {
        return ZerusMod.getInstance().getModuleManager().getModule(IStatisticModule.class);
    }


    <T> T getStatistic(StatisticModule.Statistic statistic);

    <T> T getStatistic(StatisticModule.Statistic statistic, T defaultValue);

    <T> T getSetting(Settings setting);

    <T> T getSetting(Settings setting, T defaultValue);

    default Region getRegion() {
        return Region.getRegionById(getStatistic(StatisticModule.Statistic.REGION));
    }

    boolean isMaxLevel();

    Map<Buff, BuffInfo> getBuffs();

    Map<Integer, QuestInfo> getQuests();

    void setSetting(Settings setting, Object value);


}
