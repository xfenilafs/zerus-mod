package ru.starfarm.zerus.mod.util;

import lombok.experimental.UtilityClass;
import ru.starfarm.client.api.render.Char;

import java.text.DecimalFormat;
import java.util.StringJoiner;
import java.util.TreeMap;

@UtilityClass
public class Util {

    public int rgb(int r, int g, int b) {
        return rgba(r, g, b, 255);
    }

    public int rgba(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                ((b & 0xFF));
    }

    public double percent(double x, double max) {
        return x / max > 1 ? 1 : x / max < 0 ? 0 : x / max;
    }

    public String formatPrice(int price) {
        int emeralds = price / 262144;
        int diamonds = (price % 262144) / 4096;
        int gold = price - ((262144 * emeralds) + (4096 * diamonds));
        int gold_bars = Math.max(0, gold / 64);
        int gold_shards = Math.max(0, gold % 64);

        StringJoiner stringJoiner = new StringJoiner(" ");

        if (price == 0)
            stringJoiner.add("§f0§7⛀");
        if (emeralds > 0)
            stringJoiner.add(String.format("§f%s%s", Char.COIN_EMERALD, emeralds));
        if (diamonds > 0)
            stringJoiner.add(String.format("§f%s%s", Char.COIN_DIAMOND, diamonds));
        if (gold_bars > 0)
            stringJoiner.add(String.format("§f%s%s", Char.COIN_GOLD, gold_bars));
        if (gold_shards > 0)
            stringJoiner.add(String.format("§f%s%s", Char.COIN_SILVER, gold_shards));

        return stringJoiner.toString();
    }

    private final String[] FORMATTED = {
            "", "K", "M", "B", "T", "P", "E"
    };

    public String formatValue(double value) {
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s%s", decimalFormat.format(value), FORMATTED[index]);
    }

    private final TreeMap<Integer, String> numbers = new TreeMap<Integer, String>() {{
        put(1000, "M");
        put(900, "CM");
        put(500, "D");
        put(400, "CD");
        put(100, "C");
        put(90, "XC");
        put(50, "L");
        put(40, "XL");
        put(10, "X");
        put(9, "IX");
        put(5, "V");
        put(4, "IV");
        put(1, "I");
    }};

    public String toRoman(int number) {
        int l = numbers.floorKey(number);
        return number == l ? numbers.get(number) : numbers.get(l) + toRoman(number - l);
    }
}
