package eu.koboo.backpacks.listener.insertion;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.InventoryUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerBackpackInBackpack implements Listener {

    BackpackPlugin plugin;

    @EventHandler(priority = EventPriority.HIGH)
    public void onRecursionBackpackClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (player.getGameMode() == GameMode.SPECTATOR) {
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
        if (!plugin.hasOpenBackback(player)) {
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

        boolean isBottomClick = InventoryUtils.isBottomClick(event.getRawSlot(), player);

        ItemStack affectedItem = null;
        boolean handleShift = false;

        if (isBottomClick && event.isShiftClick()) {
            affectedItem = currentItem;
            handleShift = true;
        }
        if (affectedItem == null && !isBottomClick && (click == ClickType.LEFT || click == ClickType.RIGHT)) {
            affectedItem = cursorItem;
        }
        if (affectedItem == null && !isBottomClick && click == ClickType.NUMBER_KEY) {
            int hotBarSlot = event.getHotbarButton();
            affectedItem = player.getInventory().getItem(hotBarSlot);
        }
        if (affectedItem == null) {
            return;
        }
        if (!plugin.isBackpack(affectedItem)) {
            return;
        }
        event.setCancelled(true);
        if (handleShift) {
            InventoryUtils.handleCancelledArmorShiftClick(player, slot, currentItem, slotType);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onRecursionBackpackDrag(InventoryDragEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        if (plugin.getBackpackConfig().getRestrictions().isAllowRecursion()) {
            return;
        }
        ItemStack cursorItem = event.getOldCursor();
        if (!plugin.isBackpack(cursorItem)) {
            return;
        }
        if (!plugin.hasOpenBackback(player)) {
            return;
        }
        if (InventoryUtils.isBottomDrag(event.getRawSlots(), player)) {
            return;
        }
        event.setResult(Event.Result.DENY);
        event.setCursor(cursorItem);
        event.setCancelled(true);
    }
}
