package ru.starfarm.zerus.mod.impl.modules;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.lwjgl.BufferUtils;
import org.lwjgl.Sys;
import ru.starfarm.client.api.ClientApi;
import ru.starfarm.client.api.util.BufUtil;
import ru.starfarm.zerus.mod.ZerusMod;
import ru.starfarm.zerus.mod.impl.Module;
import ru.starfarm.zerus.mod.impl.buffs.Buff;
import ru.starfarm.zerus.mod.impl.buffs.BuffInfo;
import ru.starfarm.zerus.mod.impl.quests.QuestInfo;
import ru.starfarm.zerus.mod.impl.regions.Region;
import ru.starfarm.zerus.mod.impl.settings.Settings;
import ru.starfarm.zerus.mod.menus.impl.enums.Menu;
import ru.starfarm.zerus.mod.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

@Getter
public class StatisticModule extends Module implements IStatisticModule {
    public Map<Statistic, Object> statistics = new ConcurrentHashMap<>();
    public Map<Settings, Object> settings = new ConcurrentHashMap<>();
    public Map<Buff, BuffInfo> buffs = new ConcurrentHashMap<>();
    public Map<Integer, QuestInfo> quests = new ConcurrentHashMap<>();

    @Override
    public void onEnable(ClientApi api) {
        api.payload().sendEmpty("zerus:init");
        Arrays.stream(Statistic.values()).forEach(statistic -> ZerusMod.getInstance().getApi().payload().register(
                "zerus:".concat(statistic.name().toLowerCase()),
                buffer -> statistics.put(statistic, statistic.read(buffer))
        ));

        ZerusMod.getInstance().getApi().payload().register("zerus:buff", buffer -> {
            int buff = BufUtil.readInt(buffer), level = BufUtil.readInt(buffer), time = BufUtil.readInt(buffer), giveTime = BufUtil.readInt(buffer);
            boolean leveled = BufUtil.readInt(buffer) == 1, boosted = BufUtil.readInt(buffer) == 1;

            buffs.put(Buff.values()[buff], new BuffInfo(level, leveled, boosted, time, giveTime));
        });
        ZerusMod.getInstance().getApi().payload().register("zerus:setting", buffer -> {
            val setting = Settings.values()[BufUtil.readInt(buffer)];

            settings.put(setting, setting.read(buffer));
        });
        ZerusMod.getInstance().getApi().payload().register("zerus:dbuff", buffer -> buffs.remove(Buff.values()[BufUtil.readInt(buffer)]));
        ZerusMod.getInstance().getApi().payload().register("zerus:quest", buffer -> {
            int id = BufUtil.readInt(buffer);
            String name = BufUtil.readStringUTF8(buffer);
            List<String> requirements = Arrays.asList(BufUtil.readStringUTF8(buffer).split("\n"));

            quests.put(id, new QuestInfo(name, requirements));
            System.out.println("test " + id);
        });
        ZerusMod.getInstance().getApi().payload().register("zerus:dquest", buffer -> quests.remove(BufUtil.readInt(buffer)));
        api.scheduler().everyAsync(20, 20, task -> buffs.forEach((buff, info) -> {
            Region region = Region.getRegionById(getStatistic(Statistic.REGION));

            if (info.getTime() <= -1 || buff == Buff.BLOOM_COOLDOWN && region == Region.ETERNAL_BLOOM) {
                return;
            }
            info.setTime(info.getTime() - 1);
            if (info.getTime() <= 0)
                buffs.remove(buff);
        }));

        ZerusMod.getInstance().getApi().payload().register("zerus:gui", buffer -> Menu.values()[BufUtil.readInt(buffer)].display());
    }

    public <T> T getStatistic(Statistic statistic, T defaultValue) {
        return (T) statistics.getOrDefault(statistic, defaultValue);
    }

    public <T> T getStatistic(Statistic statistic) {
        return getStatistic(statistic, (T) statistic.defaultValue);
    }

    @Override
    public <T> T getSetting(Settings setting) {
        return (T) getSetting(setting, setting.defaultValue);
    }

    @Override
    public <T> T getSetting(Settings setting, T defaultValue) {
        return (T) settings.getOrDefault(setting, defaultValue);
    }

    public boolean isMaxLevel() {
        return this.<Double>getStatistic(Statistic.NEED) == -1;
    }

    @Override
    public void setSetting(Settings setting, Object value) {
        settings.put(setting, value);
    }

    @AllArgsConstructor
    @RequiredArgsConstructor
    public enum Statistic {
        LEVEL("Уровень", 1, BufUtil::readInt),
        HEALTH("Здоровье", 0, BufUtil::readInt),
        MAX_HEALTH("Макс. здоровье", 0, BufUtil::readInt),
        MANA("Мана", 0, BufUtil::readInt),
        MAX_MANA("Макс. мана", 0, BufUtil::readInt),
        ARMOR("Физическая броня", 0, BufUtil::readInt, Util.rgb(205, 92, 92)),
        MAGIC_ARMOR("Магическая броня", 0, BufUtil::readInt, Util.rgb(30, 144, 255)),
        RESISTANCE("Сопротивление", 0, BufUtil::readInt, Util.rgb(244, 164, 96)),
        REGENERATION("Регенерация", 0, BufUtil::readInt, Util.rgb(0, 250, 154)),
        SPEED("Скорость", 0, BufUtil::readInt, Util.rgb(210, 180, 140)),
        EVASION("Шанс уклонения", 0, BufUtil::readInt, Util.rgb(0, 206, 209)),
        MANA_REGEN("Пассив. реген маны", 0, BufUtil::readInt, Util.rgb(30, 144, 255)),
        HEALTH_REGEN("Пассив. реген здоровья", 0, BufUtil::readInt, Util.rgb(154, 205, 50)),
        ITEMS_LEVEL("Ср. ур. предметов", 0, BufUtil::readDouble, Util.rgb(138, 43, 226)),
        ENCHANT_LEVEL("Ср. ур. заточки", 0, BufUtil::readDouble, Util.rgb(138, 43, 226)),
        EXP("Опыт", .0, BufUtil::readDouble),
        NEED("Необ. опыта", .0, BufUtil::readDouble),
        BALANCE("Баланс", 0, BufUtil::readInt),
        REGION("Локация", 0, BufUtil::readInt);

        @Getter
        private final String name;
        @Getter
        private final Object defaultValue;
        private final Function<ByteBuf, Object> reader;

        public int color;

        public Object read(ByteBuf buffer) {
            return reader.apply(buffer);
        }

    }

}
