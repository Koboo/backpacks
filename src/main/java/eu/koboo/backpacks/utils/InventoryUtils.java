package eu.koboo.backpacks.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class InventoryUtils {

    public boolean isBottomClick(int slot, Player player) {
        Inventory top = player.getOpenInventory().getTopInventory();
        return top.getSize() + 1 <= slot;
    }

    public int findFreeSlot(Inventory inventory, boolean hotbar) {
        if (hotbar) {
            return findFreeHotbarSlot(inventory);
        } else {
            return findFreeContainerSlot(inventory);
        }
    }

    public int findFreeHotbarSlot(Inventory inventory) {
        for (int i = 0; i < 9; i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null) {
                item = new ItemStack(Material.AIR);
            }
            if (item.getType() != Material.AIR) {
                continue;
            }
            return i;
        }
        return -1;
    }

    public int findFreeContainerSlot(Inventory inventory) {
        int containerStart = 9;
        int containerEnd = 35;
        for (int i = containerStart; i < containerEnd; i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null && item.getType() != Material.AIR) {
                continue;
            }
            return i;
        }
        return -1;
    }
}
