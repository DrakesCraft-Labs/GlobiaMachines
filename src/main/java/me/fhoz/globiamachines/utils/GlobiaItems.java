package me.fhoz.globiamachines.utils;

import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.core.attributes.MachineTier;
import com.github.drakescraft_labs.slimefun4.core.attributes.MachineType;
import com.github.drakescraft_labs.slimefun4.utils.LoreBuilder;
import me.fhoz.globiamachines.machines.BudgetDustFabricator;
import org.bukkit.Material;

/**
 * Specifies all plugin items
 */
public class GlobiaItems {

    public static final SlimefunItemStack BUDGET_DUST_FABRICATOR = new SlimefunItemStack("GLOBIA_BUDGET_DUST_FABRICATOR",
            Material.SMOKER,
            "&cBomba de polvo económica",
            "",
            "&7Una máquina que integra molienda, lavado de oro y lavado de oro.",
            LoreBuilder.machine(MachineTier.GOOD, MachineType.MACHINE),
            LoreBuilder.speed(1),
            LoreBuilderDynamic.powerBuffer(BudgetDustFabricator.CAPACITY),
            LoreBuilderDynamic.powerPerTick(BudgetDustFabricator.ENERGY_CONSUMPTION)
    );

    private GlobiaItems() {
    }
}
