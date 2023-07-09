package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
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
import org.bukkit.inventory.PlayerInventory;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerCancelEquip implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onEquipBackpack(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        Inventory inventory = event.getInventory();
        InventoryType type = inventory.getType();
        if (type != InventoryType.CRAFTING) {
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
        InventoryType.SlotType slotType = event.getSlotType();
        int slot = event.getSlot();

        if (!event.getView().getOriginalTitle().equalsIgnoreCase("Crafting")) {
            return;
        }

        // Clicked in player inventory
        PlayerInventory playerInventory = player.getInventory();
        if (event.isShiftClick()) {
            if (!plugin.isBackpack(currentItem)) {
                return;
            }
            if (slotType != InventoryType.SlotType.CONTAINER && slotType != InventoryType.SlotType.QUICKBAR) {
                return;
            }
            plugin.handleShift(player, slot, currentItem, slotType);
            event.setCancelled(true);
            return;
        }
        if (event.getClick() == ClickType.LEFT || event.getClick() == ClickType.RIGHT) {
            if (slotType != InventoryType.SlotType.ARMOR) {
                return;
            }
            if (!plugin.isBackpack(cursorItem)) {
                return;
            }
            event.setCancelled(true);
            return;
        }
        if (event.getClick() == ClickType.NUMBER_KEY) {
            int hotbarButton = event.getHotbarButton();
            ItemStack hotbarItem = playerInventory.getItem(hotbarButton);
            if (hotbarItem == null) {
                hotbarItem = new ItemStack(Material.AIR);
            }
            if (!plugin.isBackpack(hotbarItem)) {
                return;
            }
            event.setCancelled(true);
        }
    }
}
