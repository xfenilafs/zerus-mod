package ru.starfarm.zerus.mod.impl.buffs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.starfarm.client.api.functional.Lazy;
import ru.starfarm.client.api.resource.TextureResource;
import ru.starfarm.zerus.mod.ZerusMod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public enum Buff {

    POWERFUL_REGENERATION(
            "Могучая регенерация",
            Arrays.asList(
                    "Регенерация здоровья (и в режиме",
                    "боя) увеличена на величину, равную",
                    "5% от максимального запаса здоровья."
            )
    ),
    LESS_MANA_COST(
            "Меньший расход маны",
            Arrays.asList(
                    "Расход маны на любые действия,",
                    "требующие более одной единицы",
                    "маны, уменьшен на одну единицу."
            )
    ),
    TELEPORTATION(
            "§bТелепортация§3",
            Collections.singletonList(
                    "Происходит процесс телепортации."
            )
    ),
    SOUL_WEAKNESS(
            "Слабость души",
            Collections.singletonList(
                    "Ваша природная сила восстанавливается."
            ), true
    ),
    PIERCED_ARMOR(
            "Пробитый доспех",
            Arrays.asList(
                    "Получаемый вами урон увеличен",
                    "на %s%%."
            ), true
    ),
    SIEVE(
            "Решето",
            Arrays.asList(
                    "Получаемый вами урон от стрел",
                    "увеличен на %s%%."
            ), true
    ),
    AGONY(
            "Агония",
            Arrays.asList(
                    "Получаемый вами опыт с монстров",
                    "уменьшен на 25%."
            ), true
    ),
    VAMPIRIC(
            "Доп. вампиризм",
            Collections.singletonList(
                    "Увеличивает Ваш вампиризм на 5%."
            )
    ),
    BLEEDING(
            "Кровотечение",
            Arrays.asList(
                    "Каждую секунду вы теряете",
                    "%s%% от максимального здоровья."
            ), true
    ),
    SILENCE(
            "Безмолвие",
            Collections.singletonList(
                    "Использование способностей заблокировано."
            ), true
    ),
    ECSTASY_SONG(
            "Песня упоения",
            Arrays.asList(
                    "Расход маны на любые действия,",
                    "уменьшен на %s%%."
            )
    ),
    JOY_SONG(
            "Песня радости",
            Arrays.asList(
                    "Регенерация здоровья (и в режиме",
                    "боя) увеличена на величину, равную",
                    "%s%% от максимального запаса здоровья."
            )
    ),
    COURAGE_SONG(
            "Песня мужества",
            Arrays.asList(
                    "Получаемый вами урон уменьшен",
                    "на %s%%."
            )
    ),
    BLOODLUST(
            "Жажда крови",
            Arrays.asList(
                    "Повышает наносимый вами",
                    "урон на %s%%."
            )
    ),
    BATTLE_CRY(
            "Боевой крик",
            Arrays.asList(
                    "Ваши атаки генерируют максимальную",
                    "угрозу и получаемый вами урон",
                    "снижен на 20%."
            )
    ),
    BLESSING_OF_PROTECTION(
            "Щит",
            Arrays.asList(
                    "Ваше макс. здоровье увеличено на 20%,",
                    "а так же сопротивление на 10%."
            )
    ),
    ANGER(
            "Гнев",
            Arrays.asList(
                    "Увеличивает ближний урон",
                    "по суностям в два раза."
            )
    ),
    FURIOUS_GROWL(
            "Рык",
            Collections.singletonList(
                    "Гарантирует нанесение крит. удара."
            )
    ),
    BLESSING_OF_LIGHT(
            "Свет",
            Arrays.asList(
                    "Гарантирует 70%% сопротивления к урону",
                    "что не привышает ваше макс. здоровье."
            )
    ),
    JAGGEDNESS(
            "Зазубренность",
            Arrays.asList(
                    "Ваши атаки дальнего боя",
                    "уменьшают защиту цели на 10%",
                    "в течении следующих 20 секунд."
            )
    ),
    PENETRATION(
            "Пробитие",
            Collections.singletonList(
                    "Ваша защита уменьшена на 10%."
            ), true
    ),
    WEAK_ARROWS(
            "Ослабляющий",
            Arrays.asList(
                    "Ваши атаки дальнего боя",
                    "уменьшают урон цели на 10%",
                    "в течении следующих 20 секунд."
            )
    ),
    AURA_OF_DARKNESS(
            "Аура тьмы",
            Arrays.asList(
                    "В радиусе 5 блоков ваши противники",
                    "получают 10%% урон от вашего оружия."
            )
    ),
    HEALING_HAND(
            "Спасение",
            Arrays.asList(
                    "Увеличивает восстановление здоровья",
                    "с таланта \"Помощь Ближним\" на 20%."
            )
    ),
    BLACK_BLOOD_SKIN(
            "Черная кровь",
            Collections.singletonList(
                    "Ограничивает вампиризм противников."
            )
    ),
    BLESSING_OF_SILVIA(
            "Благославление",
            Collections.singletonList(
                    "Повышает ваш крит. урон на 20%."
            )
    ),
    WINDY(
            "Ветренный",
            Arrays.asList(
                    "Увеличивает вероятность вашего",
                    "уклонения от атаки на 50%",
                    "и скорость передвижения на 75%."
            )
    ),
    DEFENSIVE_REACTION(
            "Защитная реакция",
            Arrays.asList(
                    "Получаемый вами урон снижен",
                    "на 50%."
            )
    ),
    VULNERABILITY(
            "Уязвимость",
            Collections.singletonList(
                    "Ваша броня сейчас уязвима."
            ), true
    ),
    SUDDEN_WEAKNESS(
            "Внезапная слабость",
            Arrays.asList(
                    "Наносимый вами урон снижен",
                    "на 10%."
            ), true
    ),
    BEAST_CURSE(
            "Проклятие зверя",
            Collections.singletonList(
                    "Ваша скорость передвижения снижена."
            ), true
    ),
    ICE_CURSE(
            "Проклятие льдов",
            Arrays.asList(
                    "Наносимый вами урон снижен",
                    "на 5%."
            ), true
    ),
    ICE_BITE(
            "Ледяной укус",
            Arrays.asList(
                    "Каждую секунду вы теряете",
                    "5%% от максимального здоровья."
            ), true
    ),
    PALOMNIKS_WILL(
            "Воля паломников",
            Arrays.asList(
                    "Получаемый вами опыт и наносимый урон",
                    "увеличен на 10%.",
                    "Получаемый урон снижен на 10%."
            )
    ),
    MADNESS_CURSE(
            "Проклятие безумия",
            Collections.singletonList(
                    "Получаемый вами урон увеличен в 5 раз."
            ), true
    ),
    BLOOD_CURSE(
            "Проклятие крови",
            Collections.singletonList(
                    "Вы получаете урон за каждую нанесенную атаку."
            ), true
    ),
    BLOOM_COOLDOWN(
            "§3КД на ВЦ",
            Arrays.asList(
                    "В течении данного времени Вы",
                    "не сможете начать рейд."
            ), true
    ),
    HOSTELESS_POWER(
            "Мощь хозяек",
            Arrays.asList(
                    "Наносимый вами урон увеличен",
                    "на 10%% во владениях хозяек."
            )
    ),

    BREATH(
            "Дыхание",
            Collections.singletonList(
                    "Позволяет Вам дышать под водой."
            )
    ),
    MAGIC_POTION(
            "Волшебство",
            Arrays.asList(
                    "Увеличивает максимальный запас",
                    "маны на %s единиц."
            )
    ),
    POWER_POTION(
            "Могущество",
            Arrays.asList(
                    "Увеличивает максимальный запас",
                    "здоровья на %s единиц."
            )
    ),
    PROTECTION_POTION(
            "Защита",
            Arrays.asList(
                    "Увеличивает параметры обычной",
                    "и магической брони на %s единиц."
            )
    ),
    SPEED_POTION(
            "Скорость",
            Arrays.asList(
                    "Увеличивает скорость вашего",
                    "передвижения на %s%%."
            )
    ),
    ABSORPTION_POTION(
            "Поглощение",
            Arrays.asList(
                    "Уменьшает любой получаемый",
                    "урон на %s единиц."
            )
    ),
    HUNTER_POTION(
            "Охотник",
            Arrays.asList(
                    "Наносимый вами урон по монстрам",
                    "увеличен на %s%%."
            )
    ),
    HARDENING_POTION(
            "Закалка",
            Arrays.asList(
                    "Вероятность поломки ваших",
                    "предметов сокращена на %s%%."
            )
    ),
    PEACE_OF_MIND_POTION(
            "Спокойствие духа",
            Collections.singletonList(
                    "Вы теряете на %s%% меньше опыта."
            )
    ),

    GIFT_OF_SHAMBHALA(
            "Дар шамбалы",
            Arrays.asList(
                    "Защищает от полного поглощения",
                    "тенями в Шамбале во время",
                    "битвы с Костями Страдальца."
            )
    ),

    SCNDPRF_CURSE(
            "scndprf_curse",
            Arrays.asList(
                    "Ваша скорость передвижения снижена.",
                    "Использование свитков телепортации",
                    "и прыжка апрещена.",
                    "Любая смерть приведет Вас к Волку Шаману."
            )
    ),
    EXP_POTION(
            "§dДоп. опыт",
            Arrays.asList(
                    "Увеличивает любой получаемый опыт",
                    "на %s%%."
            )
    ),
    SUSTAINABILITY_POTION(
            "§dУстойчивость",
            Collections.singletonList(
                    "Вы не теряете опыт в случае смерти."
            )
    ),
    STRENGTHENING_POTION(
            "§dУкрепление",
            Arrays.asList(
                    "Вы не будете получать ауру Агонии",
                    "в случае смерти."
            )
    ),
    GRACE_POTION(
            "§dБлагодать",
            Arrays.asList(
                    "Увеличивает любой получаемый опыт",
                    "на 5%."
            )
    )
    ;

    private final String name;
    private final List<String> description;
    private boolean negative = false;
    private final Lazy<TextureResource> texture = Lazy.by(() -> ZerusMod.getInstance().getApi().resourceHandler().texture(
            "https://client.starfarm.fun/resources/zerus/buff/" + name().toLowerCase() + ".png"
    ));

    public String[] formatDescription(BuffInfo buffInfo) {
        switch (this) {
            case PEACE_OF_MIND_POTION: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        25 * buffInfo.level
                ).split("\n");
            }
            case HARDENING_POTION: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        buffInfo.level == 1 ? 25 : 40
                ).split("\n");
            }
            case HUNTER_POTION: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        2.5d * buffInfo.level
                ).split("\n");
            }
            case ABSORPTION_POTION: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        buffInfo.level == 1 ? 10 : buffInfo.level == 2 ? 30 : buffInfo.level == 3 ? 60 : buffInfo.level == 4 ? 75 : buffInfo.level == 5 ? 95 : 105
                ).split("\n");
            }
            case PROTECTION_POTION: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        buffInfo.level == 1 ? 20 : buffInfo.level == 2 ? 60 : buffInfo.level == 3 ? 115 : buffInfo.level == 4 ? 175 : 0
                ).split("\n");
            }
            case POWER_POTION: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        buffInfo.level == 1 ? 25 : buffInfo.level == 2 ? 75 : buffInfo.level == 3 ? 250 : buffInfo.level == 4 ? 500 : buffInfo.level == 5 ? 1750 : buffInfo.level == 6 ? 2750 : buffInfo.level == 7 ? 2860 : 0
                ).split("\n");
            }
            case MAGIC_POTION: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        10 * buffInfo.level
                ).split("\n");
            }
            case EXP_POTION:
            case SPEED_POTION: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        5 * buffInfo.level
                ).split("\n");
            }
            case BLOODLUST:
            case ECSTASY_SONG: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        4 * buffInfo.level
                ).split("\n");
            }
            case COURAGE_SONG:
            case BLEEDING:
            case PIERCED_ARMOR: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        2 * buffInfo.level
                ).split("\n");
            }
            case JOY_SONG: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        buffInfo.level
                ).split("\n");
            }
            case SIEVE: {
                return String.format(
                        description.stream().map(it -> "§7" + it).collect(Collectors.joining("\n")),
                        buffInfo.boost
                ).split("\n");
            }
            default: {
                return description.stream().map(it -> "§7" + it).toArray(String[]::new);
            }
        }
    }
}