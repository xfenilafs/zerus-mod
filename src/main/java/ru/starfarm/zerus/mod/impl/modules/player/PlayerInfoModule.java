package ru.starfarm.zerus.mod.impl.modules.player;

import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import ru.starfarm.client.api.ClientApi;
import ru.starfarm.client.api.render.Resolution;
import ru.starfarm.client.api.resource.SoundResource;
import ru.starfarm.zerus.mod.impl.Module;
import ru.starfarm.zerus.mod.impl.buffs.Buff;
import ru.starfarm.zerus.mod.impl.buffs.BuffInfo;
import ru.starfarm.zerus.mod.impl.modules.IStatisticModule;
import ru.starfarm.zerus.mod.impl.modules.StatisticModule;
import ru.starfarm.zerus.mod.impl.regions.Region;
import ru.starfarm.zerus.mod.impl.settings.Settings;
import ru.starfarm.zerus.mod.util.Util;

import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.starfarm.zerus.mod.util.Util.*;

public class PlayerInfoModule extends Module implements IPlayerInfoModule {

    protected SoundResource regionSound;
    protected ClientApi api;
    protected IStatisticModule statistic;

    @Override
    public void onEnable(ClientApi api) {
        this.api = api;
        statistic = IStatisticModule.get();

        api.eventBus().register(GuiScreenEvent.BackgroundDrawnEvent.class, event -> {
            if (event.getGui() instanceof GuiInventory)
                drawStatistics();
        });

        api.eventBus().register(RenderGameOverlayEvent.Post.class, event -> {
            val resolution = (Resolution) event.getResolution();

            api.overlayRenderer().isolateMatrix(() -> {
                if (!Minecraft.getMinecraft().gameSettings.showDebugInfo) {
                    drawQuests();
                    drawHead();
                    drawBars();
                    drawBuffs(resolution);
                }
                drawRegionInfo(resolution);
                drawBalance();
            });
        });

        api.scheduler().everyAsync(1, 1, task -> {
            val region = statistic.getRegion();
            if (region.sound.getValue() != regionSound) {
                if (regionSound != null) regionSound.stop();
                regionSound = region.sound.getValue();
                if (region.hasSound()) regionSound.play();
            } else if (regionSound != null && region.hasSound() && !regionSound.isPlaying()) regionSound.play();
        });
    }

    public void drawBalance() {
        api.overlayRenderer().scaled(0.95f, res -> api.fontRenderer().drawStringWithShadow(
                res.getWidth() / 2f - 94, res.getHeight() - 39.5f,
                Util.formatPrice(statistic.getStatistic(StatisticModule.Statistic.BALANCE))
        ));
    }

    private void drawBars() {
        int level = statistic.getStatistic(StatisticModule.Statistic.LEVEL);
        double exp = statistic.getStatistic(StatisticModule.Statistic.EXP),
                needExp = statistic.getStatistic(StatisticModule.Statistic.NEED);
        int health = statistic.getStatistic(StatisticModule.Statistic.HEALTH),
                maxHealth = statistic.getStatistic(StatisticModule.Statistic.MAX_HEALTH);
        int mana = statistic.getStatistic(StatisticModule.Statistic.MANA),
                maxMana = statistic.getStatistic(StatisticModule.Statistic.MAX_MANA);

        drawBar(
                40, 9, 100, 10,
                rgb(119, 115, 112), rgb(4, 5, 7),
                -1, -1, 1,
                "§e" + Minecraft.getMinecraft().player.getName(), ""
        );
        drawBar(
                40, 20, 100, 10,
                rgb(160, 36, 34), rgb(87, 11, 15),
                rgb(72, 40, 35), rgb(13, 8, 11),
                percent(health, maxHealth),
                "HP", String.format("%s/%s", health, maxHealth));
        drawBar(
                40, 31, 100, 10,
                rgb(80, 52, 164), rgb(40, 34, 138),
                rgb(50, 37, 78), rgb(28, 21, 67),
                percent(mana, maxMana),
                "MP", String.format("%s/%s", mana, maxMana)
        );
        drawBar(
                7, 44, 133, 10,
                rgb(97, 96, 156), rgb(48, 43, 83),
                rgb(59, 57, 78), rgb(11, 9, 14),
                statistic.isMaxLevel() ? 1 : percent(exp, needExp),
                "XP", statistic.isMaxLevel() ? "Макс. уровень" : String.format("%s/%s", Util.formatValue(exp), Util.formatValue(needExp))
        );
        api.overlayRenderer().scaled(1.5f, res ->
                api.fontRenderer().drawStringWithShadow(5.7f, 22.3f, "§e" + level)
        );
    }

    private void drawRegionInfo(Resolution resolution) {
        Region region = statistic.getRegion();
        if (!Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
            api.fontRenderer().drawCenteredStringWithShadow(resolution.getWidth() / 2f, 9, region.formattedName());
            if (region != Region.OPEN_WORLD)
                api.fontRenderer().drawCenteredStringWithShadow(resolution.getWidth() / 2f, 20,
                        String.format("%sРекомендуемые уровни: %s %s- %s",
                                region.type.prefix,
                                region.getRange().getMinimum(),
                                region.type.prefix,
                                region.getRange().getMaximum())
                );
        }
    }

    private final ModelPlayer biped = new ModelPlayer(0, true);

    public void drawHead() {
        Minecraft minecraft = Minecraft.getMinecraft();
        int xPosition = 7, yPosition = 11;

        api.overlayRenderer().isolateMatrix(() -> {
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.color(1, 1, 1, 1);

            GlStateManager.pushMatrix();
            GlStateManager.translate(xPosition + 14, (float) yPosition + 22, 0f);
            GlStateManager.scale(45, 45, 45);
            GlStateManager.bindTexture(minecraft.getTextureManager().getTexture(minecraft.player.getLocationSkin()).getGlTextureId());
            GlStateManager.enableColorMaterial();
            GlStateManager.rotate(135.0F, -1.0F, 1.0F, -1.0F);
            GlStateManager.rotate(-135.0F, -1.0F, 1.0F, -1.0F);
            biped.bipedHeadwear.rotateAngleX = biped.bipedHead.rotateAngleX = 0.7f;
            biped.bipedHeadwear.rotateAngleY = biped.bipedHead.rotateAngleY = (float) (-Math.PI / 4);
            biped.bipedHeadwear.rotateAngleZ = biped.bipedHead.rotateAngleZ = -0.5f;
            biped.bipedHead.render(0.064f);
            biped.bipedHeadwear.render(0.0625F);

            GlStateManager.color(1, 1, 1, 1);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        });
    }

    private void drawBar(float x, float y, float width, float height, int startColor, int endColor, int emptyStartColor, int emptyEndColor, double percent, String prefix, String text) {
        api.overlayRenderer().clear();

        api.overlayRenderer().drawGradientRect((int) x, (int) y, (int) (x + width), (int) (y + height), emptyStartColor, emptyEndColor);
        api.overlayRenderer().drawGradientRect((int) x, (int) y, (int) (x + width * percent), (int) (y + height), startColor, endColor);

        api.fontRenderer().drawCenteredStringWithShadow(x + width / 2, y + 0.5f, text);

        api.fontRenderer().drawStringWithShadow(x + 1, y + 0.5f, prefix);
    }

    public void drawBuffs(Resolution resolution) {
        int mouseX = Mouse.getX() * resolution.getWidth() / Minecraft.getMinecraft().displayWidth,
                mouseY = resolution.getHeight() - Mouse.getY() * resolution.getHeight() / Minecraft.getMinecraft().displayHeight;
        int i = 0, j = 0;
        Map.Entry<Buff, BuffInfo> hovered = null;
        for (Map.Entry<Buff, BuffInfo> entry : statistic.getBuffs().entrySet()) {
            val buff = entry.getKey();
            val info = entry.getValue();

            int x = 7 + i * 27, y = 57 + j * 27;

            if (Minecraft.getMinecraft().currentScreen != null
                    && hovered == null
                    && mouseX >= x && mouseX <= x + 25 && mouseY >= y && mouseY <= y + 25
            ) hovered = entry;


            api.overlayRenderer().clear();
            val texture = buff.getTexture().getValue();
            texture.bind();
            api.overlayRenderer().drawScaledCustomSizeModalRect(
                    x, y, 0, 0, texture.width(), texture.height(), 25, 25, texture.width(), texture.height()
            );
            api.overlayRenderer().drawRect(
                    x, (int) (y + 25 * info.getRightPercent()),
                    x + 25, y + 25,
                    rgba(0, 0, 0, 100)
            );
            api.overlayRenderer().clear();

            if (++i >= 5) {
                i = 0;
                j++;
            }
        }

        if (hovered != null) {
            Map.Entry<Buff, BuffInfo> entry = hovered;
            api.overlayRenderer().isolateMatrix(() -> {
                val buff = entry.getKey();
                val info = entry.getValue();

                val list = new ArrayList<String>();
                list.add(info.getFormatName(buff));
                list.add(" ");
                list.addAll(Arrays.asList(buff.formatDescription(info)));
                list.add(" ");
                list.add(info.getFormatTime());
                api.overlayRenderer().drawHoveringText(mouseX, mouseY, list);
                api.overlayRenderer().clear();
            });
        }
    }

    public void drawStatistics() {
        int x = api.overlayRenderer().resolution().getWidth() / 2 - 135;
        AtomicInteger y = new AtomicInteger(api.overlayRenderer().resolution().getHeight() / 2 - 50);

        Arrays.stream(StatisticModule.Statistic.values(), 5, 15)
                .forEach(statistic -> {
                    String text = statistic.getName().concat(":");
                    val value = IStatisticModule.get().getStatistic(statistic);

                    api.fontRenderer().drawStringWithShadow(
                            x - api.fontRenderer().width(text), y.getAndAdd(12),
                            String.format(
                                    "%s " + (value instanceof Double ? "%.2f" : "%s") + "%s",
                                    text, value,
                                    (ValueRange.of(9, 12).isValidValue(statistic.ordinal()) ? "%" : "")
                            ), statistic.color
                    );
                });
    }

    public void drawQuests() {
        boolean has = statistic.getSetting(Settings.VIEW_ACTIVE_QUESTS);
        int quests = statistic.getSetting(Settings.MAX_ACTIVE_QUESTS);
        if (statistic.getQuests().size() == 0 || !has) {
            return;
        }
        api.overlayRenderer().scaled(1.7f, res -> api.fontRenderer().drawStringWithShadow(
                res.getWidth() / 1.38f, 5,
                "Активные задания",
                        Util.rgb(30, 144, 255)
        ));
        AtomicInteger y = new AtomicInteger(19);
        statistic.getQuests().keySet().stream().unordered()
                .sorted(Comparator.comparingInt(id -> (int) id).reversed())
                .map(statistic.getQuests()::get)
                .limit(quests + 1)
                .forEachOrdered(questInfo -> api.overlayRenderer().scaled(0.9f, res -> {
                    api.fontRenderer().drawStringWithShadow(
                            res.getWidth() / 1.38f, y.addAndGet(10),
                            questInfo.name,
                            Util.rgb(0, 250, 154)
                    );

                    questInfo.requirements.forEach(lore -> api.fontRenderer().drawStringWithShadow(
                            res.getWidth() / 1.35f, y.addAndGet(10),
                            lore
                    ));
                }));

    }

}
