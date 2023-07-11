package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerAutoClose implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onAutoCloseDamage(EntityDamageEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if(player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        if (!plugin.getBackpackConfig().getHandling().isCloseOnDamage()) {
            return;
        }
        if (!plugin.hasOpenBackback(player)) {
            return;
        }
        player.closeInventory();
    }

    @EventHandler
    public void onAutoCloseExit(VehicleExitEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getExited() instanceof Player player)) {
            return;
        }
        if(player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        if (!plugin.getBackpackConfig().getHandling().isCloseOnEject()) {
            return;
        }
        if (!plugin.hasOpenBackback(player)) {
            return;
        }
        player.closeInventory();
    }
}
