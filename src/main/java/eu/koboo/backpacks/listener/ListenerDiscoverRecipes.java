package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.BackpackColor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerDiscoverRecipes implements Listener {

    BackpackPlugin plugin;
    List<NamespacedKey> backpackRecipeList;

    public ListenerDiscoverRecipes(BackpackPlugin plugin) {
        this.plugin = plugin;
        this.backpackRecipeList = new ArrayList<>();

        List<String> backpackColorRecipeNameList = Arrays.stream(BackpackColor.values())
                .map(BackpackColor::getRecipeKey)
                .map(NamespacedKey::toString)
                .toList();

        Iterator<Recipe> recipeIterator = Bukkit.recipeIterator();
        while (recipeIterator.hasNext()) {
            Recipe recipe = recipeIterator.next();
            if (!(recipe instanceof Keyed keyed)) {
                continue;
            }
            if (keyed.toString().equalsIgnoreCase(plugin.getRootBackpackRecipeKey().toString())) {
                backpackRecipeList.add(keyed.getKey());
            }
            if (backpackColorRecipeNameList.contains(keyed.toString())) {
                backpackRecipeList.add(keyed.getKey());
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!plugin.getBackpackConfig().getCrafting().isDiscoverAllRecipes()) {
            return;
        }
        if (!plugin.getBackpackConfig().getCrafting().isAllowCrafting()) {
            return;
        }
        Player player = event.getPlayer();
        for (NamespacedKey recipeKey : backpackRecipeList) {
            if (player.hasDiscoveredRecipe(recipeKey)) {
                continue;
            }
            player.discoverRecipe(recipeKey);
        }
    }
}
