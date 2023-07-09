package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.InventoryUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerCancelDrag implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        ItemStack cursorItem = event.getOldCursor();
        if (!plugin.isBackpack(cursorItem)) {
            return;
        }
        int lowestSlot = Integer.MAX_VALUE;
        for (Integer rawSlot : event.getRawSlots()) {
            if (rawSlot < lowestSlot) {
                lowestSlot = rawSlot;
            }
            break;
        }

        Inventory bottom = player.getOpenInventory().getBottomInventory();
        if (bottom.getType() != InventoryType.PLAYER) {
            return;
        }

        if (InventoryUtils.isBottomClick(lowestSlot, player)) {
            int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
            if (maxAmount <= -1) {
                return;
            }
            int backpackCount = plugin.countBackpacks(player);
            if (backpackCount >= maxAmount) {
                cancelDrag(event, cursorItem);
            }
            return;
        }
        if (plugin.hasOpenBackback(player)) {
            cancelDrag(event, cursorItem);
            return;
        }
        Inventory top = player.getOpenInventory().getTopInventory();
        if (top.getType() == InventoryType.CRAFTING) {
            cancelDrag(event, cursorItem);
        }
    }

    private void cancelDrag(InventoryDragEvent event, ItemStack cursor) {
        event.setResult(Event.Result.DENY);
        event.setCursor(cursor);
        event.setCancelled(true);
    }
}
