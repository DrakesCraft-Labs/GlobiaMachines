package me.fhoz.globiamachines;

import com.github.drakescraft_labs.slimefun4.api.SlimefunAddon;
import com.github.drakescraft_labs.slimefun4.api.player.PlayerProfile;
import com.github.drakescraft_labs.slimefun4.libraries.dough.collections.Pair;
import com.github.drakescraft_labs.slimefun4.libraries.dough.config.Config;
import lombok.SneakyThrows;
import me.fhoz.globiamachines.utils.Utils;
import com.github.drakescraft_labs.slimefun4.legacy.api.BlockStorage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class GlobiaMachines extends JavaPlugin implements SlimefunAddon {

    public static final HashMap<ItemStack, List<Pair<ItemStack, List<RecipeChoice>>>> shapedVanillaRecipes = new HashMap<>();
    public static final HashMap<ItemStack, List<Pair<ItemStack, List<RecipeChoice>>>> shapelessVanillaRecipes =
            new HashMap<>();
    private static GlobiaMachines instance;

    public static GlobiaMachines getInstance() {
        return instance;
    }

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        // Read something from your config.yml
        Config cfg = new Config(this);

        // Aqui iba el autoactualizador, que se traia el jar del repositorio de upstream.
        //
        // Se quita entero en vez de apagarlo por configuracion: este jar esta recompilado contra
        // el Slimefun repaquetado del servidor, asi que bajarse el de upstream encima dejaria el
        // addon sin cargar. Hasta ahora lo unico que lo frenaba era que su condicion exige una
        // version que empiece por "DEV", y las nuestras no -- una coincidencia que se rompe el dia
        // que alguien toque la cadena de version. Se despliega por SFTP, como el resto.

        // Register ACT Recipes
        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe r = recipeIterator.next();

            if (r instanceof ShapedRecipe) {
                ShapedRecipe sr = (ShapedRecipe) r;
                List<RecipeChoice> rc = new ArrayList<>();
                ItemStack key = new ItemStack(sr.getResult().getType(), 1);

                // Convert the recipe to a list
                for (Map.Entry<Character, RecipeChoice> choice : sr.getChoiceMap().entrySet()) {
                    if (choice.getValue() != null) {
                        rc.add(choice.getValue());
                    }
                }

                if (!shapedVanillaRecipes.containsKey(key)) {
                    shapedVanillaRecipes.put(key,
                            new ArrayList<>(Collections.singletonList(new Pair<>(sr.getResult(), rc))));
                } else {
                    shapedVanillaRecipes.get(key).add(new Pair<>(sr.getResult(), rc));
                }

            } else if (r instanceof ShapelessRecipe) {
                ShapelessRecipe slr = (ShapelessRecipe) r;
                ItemStack key = new ItemStack(slr.getResult().getType(), 1);

                // Key has a list of recipe options
                if (!shapelessVanillaRecipes.containsKey(key)) {
                    shapelessVanillaRecipes.put(key,
                            new ArrayList<>(Collections.singletonList(new Pair<>(slr.getResult(), slr.getChoiceList()))));
                } else {
                    shapelessVanillaRecipes.get(key).add(new Pair<>(slr.getResult(), slr.getChoiceList()));
                }
            }
        }

        // Registering Items
        GlobiaItemSetup.setup(this);
    }

    @Override
    public void onDisable() {
        // Logic for disabling the plugin...
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command cmd, @Nonnull String label, String[] args) {

        if (args.length == 0) {
            Utils.send(sender, "&cComando no válido");
            return true;
        }

        if (!(sender instanceof Player)) {
            Utils.send(sender, "&cNo hay comandos disponibles desde la consola");
            return true;
        }

        Player p = (Player) sender;

        switch (args[0].toUpperCase()) {
            case "META":
                Utils.send(p, String.valueOf(p.getInventory().getItemInMainHand().getItemMeta()));
                return true;
            case "RAWMETA":
                p.sendMessage(String.valueOf(p.getInventory().getItemInMainHand().getItemMeta()).replace("§", "&"));
                return true;
            case "VERSION":
            case "V":
                Utils.send(p, "&eVersión actual:" + this.getPluginVersion());
                return true;
        }

        if (p.hasPermission("globiaslimefun.admin")) {
            switch (args[0].toUpperCase()) {
                case "ADDINFO":

                    if (args.length != 3) {
                        Utils.send(p, "&cIndica la clave y el dato");

                    } else {
                        RayTraceResult rayResult = p.rayTraceBlocks(5d);
                        if (rayResult != null && rayResult.getHitBlock() != null
                                && BlockStorage.hasBlockInfo(rayResult.getHitBlock())) {

                            BlockStorage.addBlockInfo(rayResult.getHitBlock(), args[1], args[2]);
                            Utils.send(p, "&aInformación añadida");

                        } else {
                            Utils.send(p, "&cTienes que estar mirando a un bloque de Slimefun");
                        }
                    }
                    return true;
                case "SAVEPLAYERS":
                    saveAllPlayers();
                    return true;
            }
        }

        Utils.send(p, "&cComando no encontrado");

        return false;
    }

    private void saveAllPlayers() {
        Iterator<PlayerProfile> iterator = PlayerProfile.iterator();
        int players = 0;

        while (iterator.hasNext()) {
            PlayerProfile profile = iterator.next();

            profile.save();
            players++;
        }

        if (players > 0) {
            Bukkit.getLogger().log(Level.INFO, "Auto-saved all player data for {0} player(s)!", players);
        }
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/DrakesCraft-Labs/GlobiaMachines/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

}
