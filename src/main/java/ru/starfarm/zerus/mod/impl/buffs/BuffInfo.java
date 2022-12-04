package ru.starfarm.zerus.mod.impl.buffs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.val;
import ru.starfarm.zerus.mod.util.Util;

@Data
@AllArgsConstructor
public class BuffInfo {

    public int level;
    protected final boolean leveled;
    protected final boolean boost;
    protected int time, giveTime;

    public double getRightPercent() {
        return 1 - Util.percent(time, giveTime);
    }

    public String getFormatName(Buff buff) {
        return (buff.isNegative() ? buff == Buff.AGONY ? "§4" : "§c" : "§a") + buff.getName() + getFormatLevel();
    }

    public String getFormatTime() {
        if (time == -1)
            return "**:**";

        int minutes = time / 60;
        int seconds = time - minutes * 60;
        return (minutes == 0 ? "00" : minutes < 10 ? "0" + minutes : String.valueOf(minutes))
                + ":" + (seconds == 0 ? "00" : seconds < 10 ? "0" + seconds : String.valueOf(seconds));
    }

    public String getFormatLevel() {
        if (leveled) return " ".concat(Util.toRoman(level));
        else if (boost) return " x" + level;
        else return "";
    }
}

