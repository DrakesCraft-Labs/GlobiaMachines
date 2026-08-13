package me.fhoz.globiamachines;

import com.github.drakescraft_labs.slimefun4.api.items.groups.NestedItemGroup;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.implementation.SlimefunItems;
import com.github.drakescraft_labs.slimefun4.libraries.dough.items.CustomItemStack;
import me.fhoz.globiamachines.machines.BudgetDustFabricator;
import me.fhoz.globiamachines.utils.GlobiaItems;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public final class GlobiaItemSetup {

    // ItemGroup
    private static final NestedItemGroup globiamachines = new NestedItemGroup(
            new NamespacedKey(GlobiaMachines.getInstance(), "globiaslimefun"),
            new CustomItemStack(Material.SMOKER, "&6maquina global")
    );


    private GlobiaItemSetup() {
    }

    public static void setup(@Nonnull GlobiaMachines plugin) {
        new BudgetDustFabricator(globiamachines, GlobiaItems.BUDGET_DUST_FABRICATOR,
                RecipeType.ENHANCED_CRAFTING_TABLE, new ItemStack[]{
                new ItemStack(Material.GRINDSTONE), new ItemStack(Material.GRINDSTONE),
                new ItemStack(Material.GRINDSTONE),
                SlimefunItems.GOLD_PAN, new ItemStack(Material.DIAMOND), SlimefunItems.GOLD_PAN,
                SlimefunItems.MAGNET, SlimefunItems.PROGRAMMABLE_ANDROID_MINER, SlimefunItems.MAGNET
        }).register(plugin);
    }

}
