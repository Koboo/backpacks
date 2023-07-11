package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerDiscoverRecipes implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onJoinBackpack(PlayerJoinEvent event) {
        if (!plugin.getBackpackConfig().getCrafting().isDiscoverAllRecipes()) {
            return;
        }
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        for (Keyed recipe : plugin.getRecipeKeyList()) {
            if (player.hasDiscoveredRecipe(recipe.getKey())) {
                continue;
            }
            player.discoverRecipe(recipe.getKey());
        }
    }
}
