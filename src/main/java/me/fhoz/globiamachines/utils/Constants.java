package me.fhoz.globiamachines.utils;

import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;
import com.github.drakescraft_labs.slimefun4.implementation.SlimefunItems;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public final class Constants {
    public static final int SERVER_TICK_RATE = 20;
    public static final int CUSTOM_TICKER_DELAY = Slimefun.getCfg().getInt("URID.custom-ticker-delay");


    public static final int SERVER_VERSION = Integer.parseInt(Bukkit.getVersion().replaceFirst(".*MC: ", "").replace(
            ")", "").replace(".", ""));

    public static final String SLIMEFUN_VERSION = Slimefun.getVersion();
    public static final Pattern VERSION_PATTERN = Pattern.compile("(DEV - )([0-9]+)");
    public static final boolean isNCPInstalled = Bukkit.getPluginManager().isPluginEnabled("NoCheatPlus");
    public static final List<SlimefunItemStack> dusts = new ArrayList<>(Arrays.asList(
            SlimefunItems.COPPER_DUST, SlimefunItems.GOLD_DUST, SlimefunItems.IRON_DUST,
            SlimefunItems.LEAD_DUST, SlimefunItems.ALUMINUM_DUST, SlimefunItems.ZINC_DUST,
            SlimefunItems.TIN_DUST, SlimefunItems.MAGNESIUM_DUST, SlimefunItems.SILVER_DUST
    ));
    public static final int MAX_STACK_SIZE = 64;
    public static boolean SLIMEFUN_UPDATED = false;

    private Constants() {
    }

}
