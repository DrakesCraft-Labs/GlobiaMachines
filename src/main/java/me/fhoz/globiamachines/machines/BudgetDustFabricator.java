package me.fhoz.globiamachines.machines;

import com.github.drakescraft_labs.slimefun4.api.items.ItemGroup;
import com.github.drakescraft_labs.slimefun4.api.items.SlimefunItemStack;
import com.github.drakescraft_labs.slimefun4.api.recipes.RecipeType;
import com.github.drakescraft_labs.slimefun4.core.attributes.RecipeDisplayItem;
import com.github.drakescraft_labs.slimefun4.implementation.SlimefunItems;
import com.github.drakescraft_labs.slimefun4.implementation.items.electric.machines.ElectricDustWasher;
import com.github.drakescraft_labs.slimefun4.implementation.items.multiblocks.OreWasher;
import com.github.drakescraft_labs.slimefun4.libraries.dough.items.CustomItemStack;
import com.github.drakescraft_labs.slimefun4.utils.SlimefunUtils;
import me.fhoz.globiamachines.utils.Constants;
import com.github.drakescraft_labs.slimefun4.legacy.Objects.SlimefunItem.abstractItems.AContainer;
import com.github.drakescraft_labs.slimefun4.legacy.Objects.SlimefunItem.abstractItems.MachineRecipe;
import com.github.drakescraft_labs.slimefun4.legacy.api.inventory.BlockMenu;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The electric dust washer but accepts cobblestone
 * Low iq low effort machine
 *
 * @see ElectricDustWasher
 */

public class BudgetDustFabricator extends AContainer implements RecipeDisplayItem {

    public static final int ENERGY_CONSUMPTION = 26;
    public static final int CAPACITY = ENERGY_CONSUMPTION * 3;
    private final List<ItemStack> acceptableInputs = new ArrayList<>(Arrays.asList(
            new ItemStack(Material.COBBLESTONE), new ItemStack(Material.ANDESITE),
            new ItemStack(Material.DIORITE), new ItemStack(Material.GRANITE)
    ));
    private OreWasher oreWasher;

    public BudgetDustFabricator(ItemGroup category, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(category, item, recipeType, recipe);
    }

    @Override
    public void preRegister() {
        super.preRegister();

        oreWasher = (OreWasher) SlimefunItems.ORE_WASHER.getItem();
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> displayRecipes = new ArrayList<>();

        for (SlimefunItemStack dust : Constants.dusts) {
            displayRecipes.add(new CustomItemStack(Material.COBBLESTONE,
                    "&f任意圆石变种", "&7圆石", "&7安山岩", "&7闪长岩", "&7花岗岩"
            ));
            displayRecipes.add(dust);
        }

        return displayRecipes;
    }

    @Override
    protected MachineRecipe findNextRecipe(BlockMenu menu) {
        for (int slot : getInputSlots()) {
            for (ItemStack acceptableInput : acceptableInputs) {
                if (SlimefunUtils.isItemSimilar(menu.getItemInSlot(slot), acceptableInput, true, false)) {
                    if (!hasFreeSlot(menu)) {
                        return null;
                    }

                    ItemStack dust = oreWasher.getRandomDust();
                    MachineRecipe recipe = new MachineRecipe(4 / getSpeed(), new ItemStack[]{acceptableInput},
                            new ItemStack[]{dust});

                    if (menu.fits(recipe.getOutput()[0], getOutputSlots())) {
                        menu.consumeItem(slot);
                        return recipe;
                    }
                }
            }
        }

        return null;
    }

    private boolean hasFreeSlot(BlockMenu menu) {
        for (int slot : getOutputSlots()) {
            ItemStack item = menu.getItemInSlot(slot);

            if (item == null || item.getType() == Material.AIR) {
                return true;
            }
        }

        return false;
    }

    @Override
    public ItemStack getProgressBar() {
        return new ItemStack(Material.CAULDRON);
    }

    @Nonnull
    @Override
    public String getMachineIdentifier() {
        return "GLOBIA_BUDGET_DUST_FABRICATOR";
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public int getEnergyConsumption() {
        return ENERGY_CONSUMPTION;
    }

    @Override
    public int getSpeed() {
        return 1;
    }

}
