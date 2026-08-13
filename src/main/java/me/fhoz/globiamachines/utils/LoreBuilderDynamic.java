package me.fhoz.globiamachines.utils;

public final class LoreBuilderDynamic {
    public LoreBuilderDynamic() {
    }

    public static String powerBuffer(double power) {
        return power(power, " Buffer");
    }

    public static String powerPerSecond(double power) {
        return power(Utils.perTickToPerSecond(power), "/s");
    }

    public static String powerPerTick(double power) {
        return power(power, "/t");
    }

    public static String power(double power, String suffix) {
        return "&8⇨ &e⚡ &7" + Utils.powerFormatAndFadeDecimals(power) + " J" + suffix;
    }
}
