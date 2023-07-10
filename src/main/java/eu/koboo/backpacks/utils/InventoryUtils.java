package eu.koboo.backpacks.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

@UtilityClass
public class InventoryUtils {

    public boolean isBottomClick(int rawSlot, Player player) {
        Inventory top = player.getOpenInventory().getTopInventory();
        return top.getSize() <= rawSlot;
    }

    public boolean isBottomDrag(Set<Integer> rawSlotSet, Player player) {
        InventoryView view = player.getOpenInventory();
        Inventory top = view.getTopInventory();
        for (Integer rawSlot : rawSlotSet) {
            if(rawSlot < top.getSize()) {
                continue;
            }
            return true;
        }
        return false;
    }

    public boolean isTopDrag(Set<Integer> rawSlotSet, Player player) {
        InventoryView view = player.getOpenInventory();
        Inventory top = view.getTopInventory();
        int lastTopSlot = top.getSize() - 1;
        for (Integer rawSlot : rawSlotSet) {
            if(rawSlot >= lastTopSlot) {
                continue;
            }
            return true;
        }
        return false;
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
