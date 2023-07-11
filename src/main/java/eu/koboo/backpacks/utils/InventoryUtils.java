package eu.koboo.backpacks.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@UtilityClass
public class InventoryUtils {

    public static final List<Integer> PLAYER_INVENTORY_TOP_SLOTS = Arrays.asList(
            0, 1, 2, 3, 4, 5, 6, 7, 8
    );

    public int getTopSize(Player player) {
        Inventory top = player.getOpenInventory().getTopInventory();
        if (top.getType() == InventoryType.CRAFTING) {
            return 9;
        }
        return top.getSize();
    }

    public boolean isBottomClick(int rawSlot, Player player) {
        return getTopSize(player) <= rawSlot;
    }

    public boolean isBottomDrag(Set<Integer> rawSlotSet, Player player) {
        int topSize = getTopSize(player);
        for (Integer rawSlot : rawSlotSet) {
            if (rawSlot < topSize) {
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
            if (rawSlot >= lastTopSlot) {
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
