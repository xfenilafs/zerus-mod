package ru.starfarm.zerus.mod.impl.regions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.starfarm.client.api.functional.Lazy;
import ru.starfarm.client.api.resource.SoundResource;
import ru.starfarm.zerus.mod.ZerusMod;

import java.time.temporal.ValueRange;
import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Region {
    OPEN_WORLD(Type.HOSTILE, "Открытый мир", null),
    GREENISH(Type.PEACEFUL, "Гриншир", ValueRange.of(1, 5)),
    SUBURB_CHIPPED_I(Type.PEACEFUL, "Пригород Скола", ValueRange.of(6, 10)),
    CHIPPED(Type.PEACEFUL, "Скол", ValueRange.of(10, 19)),
    SUBURB_CHIPPED_II(Type.PEACEFUL, "Пригород Скола", ValueRange.of(11, 13)),
    PIRATE_GROUNDS(Type.HOSTILE, "Пиратские угодья", ValueRange.of(14, 17)),
    PIRATE_STRONGHOLD(Type.HOSTILE, "Оплот пиратов", ValueRange.of(18, 19)),
    WINTER_KEYS(Type.HOSTILE, "Зимние ключи", ValueRange.of(20, 23)),
    SNOWY_TRANSITION(Type.HOSTILE, "Заснеженный переход", ValueRange.of(22, 25)),
    WINTER_VALLEY(Type.HOSTILE, "Зимняя долина", ValueRange.of(24, 29)),
    SNOW_HAVEN(Type.PEACEFUL, "Снежное пристанище", ValueRange.of(20, 35)),
    WESTERN_ICE_LIMIT(Type.HOSTILE, "Западный лед. предел", ValueRange.of(31, 33)),
    SOUTHERN_ICE_LIMIT(Type.HOSTILE, "Южный лед. предел", ValueRange.of(33, 35)),
    ICE_FRONT(Type.PEACEFUL, "Ледяной фронт", ValueRange.of(30, 35)),
    GIRAN(Type.PEACEFUL, "Гиран", ValueRange.of(35, 47)),
    GIRAN_FORESTS(Type.HOSTILE, "Леса Гирана", ValueRange.of(35, 38)),
    FENUGREEK(Type.ELITE, "Шамбала", ValueRange.of(39, 39)),
    OCCUPIED_MINE(Type.ELITE, "Захваченная шахта", ValueRange.of(35, 38)),
    THE_HOT_SPRINGS(Type.HOSTILE, "Горячие источники", ValueRange.of(42, 47)),
    MALMO(Type.HOSTILE, "Мальмо", ValueRange.of(40, 42)),
    HUNTING_VILLAGE(Type.PEACEFUL, "Охотничье Селение", ValueRange.of(47, 51)),
    NORTHERN_CAPITAL(Type.PEACEFUL, "Северная столица", ValueRange.of(51, 70)),
    FORGOTTEN_FOREST(Type.HOSTILE, "Позабытый лес", ValueRange.of(49, 51)),
    DENSE_FOREST(Type.HOSTILE, "Дремучий лес", ValueRange.of(47, 49)),
    CROSSING_UNDER_THE_MOUNTAIN(Type.HOSTILE, "Переход под горой", ValueRange.of(51, 51)),
    INCENSE(Type.PEACEFUL, "Ладан", ValueRange.of(51, 59)),
    FOOTHILLS_OF_INCENSE(Type.HOSTILE, "Предгорье Ладана", ValueRange.of(51, 52)),
    ROAD_TO_INCENSE(Type.HOSTILE, "Дорога в Ладан", ValueRange.of(51, 53)),
    ROTTEN_MARSH(Type.HOSTILE, "Гиблые топи", ValueRange.of(54, 57)),
    WITCH_LAND(Type.ELITE, "Ведьмин край", ValueRange.of(58, 59)),
    LOST_EDGE(Type.HOSTILE, "Утерянный край", ValueRange.of(60, 61)),
    PATH_BETWEEN_ROCKS(Type.HOSTILE, "Путь меж скал", ValueRange.of(52, 54)),
    PORT_ROYAL(Type.HOSTILE, "Порт-роял", ValueRange.of(62, 64)),
    FARMS_PORT_ROYAL(Type.HOSTILE, "Фермы близ Порт-рояла", ValueRange.of(64, 66)),
    SUNKEN_GARDENS(Type.HOSTILE, "Затонувшие сады", ValueRange.of(66, 67)),
    SOUTHERN_APPANAGE(Type.ELITE, "Южный удел", ValueRange.of(67, 69)),
    GUNNHILDUR(Type.ELITE, "Тол'гундур", ValueRange.of(68, 69)),
    PALACE_ON_THE_CLIFF(Type.ELITE, "Дворец на утесе", ValueRange.of(67, 69)),
    LANDS_OF_ETERNAL_WINTER(Type.HOSTILE, "Земли вечной зимы", ValueRange.of(69, 80)),
    BEAR_LAIR(Type.ELITE, "Медвежье логово", ValueRange.of(69, 72)),
    SETTLEMENT_IN_THE_ICE(Type.PEACEFUL, "Поселение во льдах", ValueRange.of(69, 72)),
    SUBURB_OF_MILDIAN(Type.PEACEFUL, "Пригород Милдиана", ValueRange.of(71, 80)),
    MILDIAN(Type.PEACEFUL, "Милдиан", ValueRange.of(71, 80)),
    PORTAL(Type.ABNORMAL, "Портал в неизведанный мир", ValueRange.of(72, 80)),
    CAPTURED_PORT(Type.HOSTILE, "Захваченный порт", ValueRange.of(72, 73)),
    DESTROYED_VILLAGE(Type.ELITE, "Разрушенная станица", ValueRange.of(74, 75)),

    ETERNAL_BLOOM(Type.RAID, "Вечное Цветение", ValueRange.of(68, 69))
    ;

    public final Type type;
    public final String name;
    public final ValueRange range;
    public final Lazy<SoundResource> sound = Lazy.by(() -> ZerusMod.getInstance().getApi().resourceHandler().sound(
            "https://client.starfarm.fun/resources/zerus/region-sound/" + name().toLowerCase() + ".ogg"
    ));


    public String formattedName() {
        return type.prefixBold.concat(name);
    }

    public static Region getRegionById(int id) {
        return Arrays.stream(values())
                .filter(region -> region.ordinal() == id)
                .findAny().orElse(Region.ETERNAL_BLOOM);
    }

    public boolean hasSound() {
        return sound.getValue().isLoaded();
    }

    @AllArgsConstructor
    public enum Type {
        PEACEFUL("§a", "§a§l"), HOSTILE("§e", "§e§l"),
        ELITE("§6", "§6§l"), RAID("§c", "§c§l"),
        ABNORMAL("§5", "§5§l")
        ;

        public final String prefix, prefixBold;
    }
}
