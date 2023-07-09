package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.InventoryUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerCancelRecursion implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onRecursion(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (plugin.getBackpackConfig().getRestrictions().isAllowRecursion()) {
            return;
        }
        Inventory inventory = event.getInventory();
        InventoryType type = inventory.getType();

        if (type != InventoryType.CHEST) {
            return;
        }
        InventoryHolder holder = inventory.getHolder();
        if (!(holder instanceof Player)) {
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
        InventoryType.SlotType slotType = event.getSlotType();
        int slot = event.getSlot();
        if (!plugin.hasOpenBackback(player)) {
            return;
        }

        boolean clicksTop = !InventoryUtils.isBottomClick(event.getRawSlot(), player);

        ItemStack affectedItem = null;
        boolean handleShift = false;
        if (!clicksTop && event.isShiftClick()) {
            affectedItem = currentItem;
            handleShift = true;
        }
        if (affectedItem == null && clicksTop && (click == ClickType.LEFT || click == ClickType.RIGHT)) {
            affectedItem = cursorItem;
        }
        if (affectedItem == null && clicksTop && click == ClickType.NUMBER_KEY) {
            int hotbarButton = event.getHotbarButton();
            affectedItem = player.getInventory().getItem(hotbarButton);
        }
        if (!plugin.isBackpack(affectedItem)) {
            return;
        }
        event.setCancelled(true);
        if (handleShift) {
            plugin.handleShift(player, slot, currentItem, slotType);
        }
    }
}
