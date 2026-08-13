package me.fhoz.globiamachines.utils;

import com.github.drakescraft_labs.slimefun4.implementation.Slimefun;
import com.github.drakescraft_labs.slimefun4.libraries.dough.common.ChatColors;
import com.github.drakescraft_labs.slimefun4.libraries.dough.protection.Interaction;
import me.fhoz.globiamachines.GlobiaMachines;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import com.github.drakescraft_labs.slimefun4.legacy.api.inventory.BlockMenuPreset;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitTask;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public final class Utils {

    private static final DecimalFormat powerFormat;
    private static final NamespacedKey globiakey = new NamespacedKey(GlobiaMachines.getInstance(), "globiakey");
    private static final NamespacedKey nonClickable = new NamespacedKey(GlobiaMachines.getInstance(), "nonclickable");
    private final static TreeMap<Integer, String> map = new TreeMap<>();

    static {
        powerFormat = new DecimalFormat("###,###.##", DecimalFormatSymbols.getInstance(Locale.ROOT));
    }

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }

    private Utils() {
    }

    public static String powerFormatAndFadeDecimals(double power) {
        String formattedString = powerFormat.format(power);
        return formattedString.indexOf(46) != -1 ? formattedString.substring(0, formattedString.indexOf(46)) + ChatColor.DARK_GRAY + formattedString.substring(formattedString.indexOf(46)) + ChatColor.GRAY : formattedString;
    }

    public static void putOutputSlot(BlockMenuPreset preset, int slot) {
        preset.addItem(slot, (ItemStack) null, new ChestMenu.AdvancedMenuClickHandler() {
            public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                return false;
            }

            public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                return cursor == null || cursor.getType() == Material.AIR;
            }
        });
    }

    public static double ticksToSeconds(double ticks) {
        return Constants.CUSTOM_TICKER_DELAY <= 0 ? (double) 20.0F * ticks : (double) Constants.CUSTOM_TICKER_DELAY / (double) 20.0F * ticks;
    }

    public static double perTickToPerSecond(double power) {
        return Constants.CUSTOM_TICKER_DELAY <= 0 ? (double) 20.0F * power : (double) 1.0F / ((double) Constants.CUSTOM_TICKER_DELAY / (double) 20.0F) * power;
    }

    public static String color(String str) {
        if (str == null) {
            return null;
        }

        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void send(CommandSender p, String message) {
        p.sendMessage(color("&7[&6全球机器&7] &r" + message));
    }

    public static String multiBlockWarning() {
        return "&c这是一个多块机器!";
    }

    // TODO: Deprecate custom model data method of detecting non interactables
    public static ItemStack buildNonInteractable(Material material, @Nullable String name, @Nullable String... lore) {
        ItemStack nonClickableItem = new ItemStack(material);
        ItemMeta NCMeta = nonClickableItem.getItemMeta();
        if (name != null) {
            NCMeta.setDisplayName(ChatColors.color(name));
        } else {
            NCMeta.setDisplayName(" ");
        }

        if (lore.length > 0) {
            List<String> lines = new ArrayList<>();

            for (String line : lore) {
                lines.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            NCMeta.setLore(lines);
        }

        NCMeta.getPersistentDataContainer().set(nonClickable, PersistentDataType.BYTE, (byte) 1);
        nonClickableItem.setItemMeta(NCMeta);
        return nonClickableItem;
    }

    // TODO: Deprecate custom model data method of detecting non interactables
    public static boolean checkNonInteractable(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().getOrDefault(nonClickable, PersistentDataType.BYTE, (byte) 0) == 1;
    }

    public static boolean checkAdjacent(Block b, Material material) {
        return b.getRelative(BlockFace.NORTH).getType() == material
                || b.getRelative(BlockFace.EAST).getType() == material
                || b.getRelative(BlockFace.SOUTH).getType() == material
                || b.getRelative(BlockFace.WEST).getType() == material;
    }

    public static void giveOrDropItem(Player p, ItemStack toGive) {
        for (ItemStack leftover : p.getInventory().addItem(toGive).values()) {
            p.getWorld().dropItemNaturally(p.getLocation(), leftover);
        }
    }

    public static String getViewableName(ItemStack item) {
        if (item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        } else {
            return WordUtils.capitalizeFully(item.getType().name().replace("_", " "));
        }
    }

    public static String toRoman(int number) {
        int l = map.floorKey(number);
        if (number == l) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number - l);
    }

    public static ItemStack keyItem(ItemStack item) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        meta.getPersistentDataContainer().set(globiakey, PersistentDataType.INTEGER, 1);
        clone.setItemMeta(meta);
        return clone;
    }

    public static ItemStack unKeyItem(ItemStack item) {
        ItemStack clone = item.clone();
        ItemMeta meta = clone.getItemMeta();
        meta.getPersistentDataContainer().remove(globiakey);
        clone.setItemMeta(meta);
        return clone;
    }

    public static boolean canOpen(@Nonnull Block b, @Nonnull Player p) {
        return (p.hasPermission("slimefun.inventory.bypass")
                || Slimefun.getProtectionManager().hasPermission(
                p, b.getLocation(), Interaction.INTERACT_BLOCK));
    }

    // Don't use Slimefun's runsync
    public static BukkitTask runSync(Runnable r) {
        return GlobiaMachines.getInstance() != null && GlobiaMachines.getInstance().isEnabled() ?
                Bukkit.getScheduler().runTask(GlobiaMachines.getInstance(), r) : null;
    }

    public static BukkitTask runSync(Runnable r, long delay) {
        return GlobiaMachines.getInstance() != null && GlobiaMachines.getInstance().isEnabled() ?
                Bukkit.getScheduler().runTaskLater(GlobiaMachines.getInstance(), r, delay) : null;
    }
}

