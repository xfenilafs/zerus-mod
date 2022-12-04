package ru.starfarm.zerus.mod.impl.settings;

import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.starfarm.client.api.util.BufUtil;

import java.util.function.BiConsumer;
import java.util.function.Function;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum Settings {

    BLOOD("Отображение эффектов крови", BufUtil::readBoolean, (buffer, value) -> BufUtil.writeBoolean(buffer, (boolean) value), true),
    PLAYER_BALANCE("Торговать с баланса", BufUtil::readBoolean, (buffer, value) -> BufUtil.writeBoolean(buffer, (boolean) value), true),
    MAX_ACTIVE_QUESTS("Максимальное кол-во отображаемых заданий", BufUtil::readInt, (buffer, value) -> BufUtil.writeInt(buffer, (int) value), 4, 11),
    VIEW_ACTIVE_QUESTS("Отображать активные задания", BufUtil::readBoolean, (buffer, value) -> BufUtil.writeBoolean(buffer, (boolean) value), true)
    ;

    public final String name;
    public final Function<ByteBuf, Object> reader;
    public final BiConsumer<ByteBuf, Object> consumer;
    public final Object defaultValue;

    public int maxValue;


    public Object read(ByteBuf buffer) {
        return reader.apply(buffer);
    }
}
