package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.InventoryUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.ItemStack;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerLimitAmount implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (player.getGameMode() != GameMode.SURVIVAL) {
            return;
        }
        int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
        if (maxAmount <= -1) {
            return;
        }

        ItemStack currentItem = event.getCurrentItem();
        if (currentItem == null) {
            currentItem = new ItemStack(Material.AIR);
        }
        ItemStack cursorItem = event.getCursor();
        if (cursorItem == null) {
            cursorItem = new ItemStack(Material.AIR);
        }
        ClickType click = event.getClick();
        boolean clicksTop = !InventoryUtils.isBottomClick(event.getRawSlot(), player);

        ItemStack affectedItem = null;
        if (clicksTop && (event.isShiftClick() || click == ClickType.NUMBER_KEY)) {
            affectedItem = currentItem;
        }
        if (affectedItem == null && !clicksTop && (click == ClickType.LEFT || click == ClickType.RIGHT)) {
            affectedItem = cursorItem;
        }

        if (!plugin.isBackpack(affectedItem)) {
            return;
        }
        int totalBackpacks = plugin.countBackpacks(player);
        // MaxAmount - 1
        if (totalBackpacks <= (maxAmount - 1)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
        if (maxAmount <= -1) {
            return;
        }
        int countedBackpacks = plugin.countBackpacks(player);
        if (countedBackpacks < maxAmount) {
            return;
        }
        ItemStack backpackItem = event.getItem().getItemStack();
        if (!plugin.isBackpack(backpackItem)) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }
        int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
        if (maxAmount <= -1) {
            return;
        }
        int countedBackpacks = plugin.countBackpacks(player);
        if (countedBackpacks <= maxAmount) {
            return;
        }
        int overflow = countedBackpacks - maxAmount;
        for (ItemStack content : player.getInventory().getContents()) {
            if (content == null) {
                continue;
            }
            if (!plugin.isBackpack(content)) {
                continue;
            }
            Location location = player.getLocation();
            if (player.getOpenInventory().getTopInventory().getHolder() instanceof BlockInventoryHolder holder) {
                location = holder.getBlock().getLocation();
            }
            player.getWorld().dropItem(location, content);
            content.setAmount(0);
            content.setType(Material.AIR);
            if (overflow == 0) {
                break;
            }
            overflow -= 1;
        }
    }
}
