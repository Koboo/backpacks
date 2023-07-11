package eu.koboo.backpacks.listener;

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
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerCancelEquip implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onEquipBackpackClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        Inventory inventory = event.getInventory();
        InventoryHolder holder = inventory.getHolder();
        if (!(holder instanceof Player)) {
            return;
        }
        if (inventory.getType() != InventoryType.CRAFTING) {
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
        InventoryType.SlotType slotType = event.getSlotType();
        int slot = event.getSlot();
        boolean isBottomClick = InventoryUtils.isBottomClick(event.getRawSlot(), player);

        // Clicked in player inventory
        PlayerInventory playerInventory = player.getInventory();
        // Shift equipping
        if (event.isShiftClick()
                && (slotType == InventoryType.SlotType.CONTAINER || slotType == InventoryType.SlotType.QUICKBAR)
                && isBottomClick
                && plugin.isBackpack(currentItem)) {
            // Custom shift handling for quality of life
            InventoryUtils.handleCancelledArmorShiftClick(player, slot, currentItem, slotType);
            event.setCancelled(true);
            return;
        }
        // Drag and Drop equipping
        if ((event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT)
                && slotType == InventoryType.SlotType.ARMOR
                && plugin.isBackpack(cursorItem)) {
            event.setCancelled(true);
            return;
        }
        // Number key equipping
        if (event.getClick() == ClickType.NUMBER_KEY
                && slotType == InventoryType.SlotType.ARMOR
                && plugin.isBackpack(playerInventory.getItem(event.getHotbarButton()))) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEquipBackpackDrag(InventoryDragEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if (player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        Inventory inventory = event.getInventory();
        if (inventory.getType() != InventoryType.CRAFTING) {
            return;
        }
        InventoryHolder holder = inventory.getHolder();
        if (!(holder instanceof Player)) {
            return;
        }
        if (!event.getRawSlots().contains(BackpackPlugin.HELMET_RAW_SLOT)) {
            return;
        }
        ItemStack cursorItem = event.getOldCursor();
        if (!plugin.isBackpack(cursorItem)) {
            return;
        }
        event.setResult(Event.Result.DENY);
        event.setCursor(cursorItem);
        event.setCancelled(true);
    }
}
