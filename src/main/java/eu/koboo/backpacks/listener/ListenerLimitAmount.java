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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.BlockInventoryHolder;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@SuppressWarnings("unused")
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

    // Cancel pickup of backpacks if players exceeds limit
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
        // Check if user wants the limit restriction
        int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
        if (maxAmount <= -1) {
            return;
        }
        // Check the total backpacks in the players inventory exceeds the set limit
        int countedBackpacks = plugin.countBackpacks(player);
        if (countedBackpacks <= maxAmount) {
            return;
        }

        // Check if the items from the inventory gets added to the players inventory after closing
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        InventoryType inventoryType = topInventory.getType();
        if(!BackpackPlugin.INVENTORY_TYPES_ADDED_AFTER_CLOSE.contains(inventoryType)) {
            return;
        }

        // Check if the player has the backpack still on its cursor and if so drop it
        ItemStack onCursor = player.getItemOnCursor();
        if(plugin.isBackpack(onCursor)) {
            player.getWorld().dropItem(player.getLocation(), onCursor);
            player.setItemOnCursor(new ItemStack(Material.AIR));
            return;
        }

        // Check how many backpacks are too much and check every item from the top inventory
        // and drop them until we reached the max amount of the player inventory
        int overflow = countedBackpacks - maxAmount;
        for (ItemStack content : topInventory.getContents()) {
            if (content == null || !plugin.isBackpack(content)) {
                continue;
            }
            Location location = player.getLocation();
            if (topInventory.getHolder() instanceof BlockInventoryHolder holder) {
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
