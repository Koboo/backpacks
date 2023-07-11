package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.InventoryUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
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
public class ListenerShulkerBoxInBackpack implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onShulkerBoxInBackpackClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (plugin.getBackpackConfig().getRestrictions().isAllowShulkerBoxIn()) {
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
        if(!Tag.SHULKER_BOXES.isTagged(affectedItem.getType())) {
            return;
        }
        event.setCancelled(true);
        if (handleShift) {
            plugin.handleShift(player, slot, currentItem, slotType);
        }
    }

    @EventHandler
    public void onShulkerBoxInBackpackDrag(InventoryDragEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (plugin.getBackpackConfig().getRestrictions().isAllowShulkerBoxIn()) {
            return;
        }
        ItemStack cursorItem = event.getOldCursor();
        if(!Tag.SHULKER_BOXES.isTagged(cursorItem.getType())) {
            return;
        }
        if (!plugin.hasOpenBackback(player)) {
            return;
        }
        if(InventoryUtils.isBottomDrag(event.getRawSlots(), player)) {
           return;
        }
        event.setResult(Event.Result.DENY);
        event.setCursor(cursorItem);
        event.setCancelled(true);
    }
}
