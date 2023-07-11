package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.InventoryUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class ListenerLimitAmount implements Listener {

    BackpackPlugin plugin;

    @EventHandler
    public void onLimitAmountBackpackClick(InventoryClickEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        if(player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }
        int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
        if (maxAmount <= -1) {
            return;
        }

        InventoryType topType = player.getOpenInventory().getTopInventory().getType();
        if(topType == InventoryType.WORKBENCH) {
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
        boolean isBottomClick = InventoryUtils.isBottomClick(event.getRawSlot(), player);

        ItemStack affectedItem = null;
        if (!isBottomClick && (event.isShiftClick() || click == ClickType.NUMBER_KEY)) {
            affectedItem = currentItem;
        }
        if (affectedItem == null && isBottomClick && (click == ClickType.LEFT || click == ClickType.RIGHT)) {
            affectedItem = cursorItem;
        }
        if(!plugin.isBackpack(affectedItem)) {
            return;
        }

        int totalBackpacks = plugin.countBackpacks(player.getOpenInventory().getBottomInventory());
        if (totalBackpacks <= (maxAmount - 1)) {
            return;
        }
        event.setCancelled(true);
        player.sendMessage(
                plugin.getBackpackConfig().getMessages().getExceedsLimitAmount()
                        .replaceAll("%limit_amount%", String.valueOf(maxAmount))
        );
    }

    // Cancel pickup of backpacks if players exceeds limit
    @EventHandler
    public void onLimitAmountBackpackPickup(EntityPickupItemEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if(player.getGameMode() == GameMode.SPECTATOR) {
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
    public void onLimitAmountBackpackClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }
        // Check if user wants the limit restriction
        int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
        if (maxAmount <= -1) {
            return;
        }
        // Check the total backpacks in the players inventory exceeds the set limit
        int onCloseCount = plugin.countBackpacks(player);
        if (onCloseCount <= maxAmount) {
            return;
        }

        // Check if the player has the backpack still on its cursor and if so drop it
        ItemStack onCursor = player.getItemOnCursor();
        if (plugin.isBackpack(onCursor)) {
            player.getWorld().dropItem(player.getLocation(), onCursor);
            player.setItemOnCursor(new ItemStack(Material.AIR));
            player.sendMessage(
                    plugin.getBackpackConfig().getMessages().getExceedsLimitAmount()
                            .replaceAll("%limit_amount%", String.valueOf(maxAmount))
            );
        }
        int countAfterDroppedCursor = plugin.countBackpacks(player);
        if (countAfterDroppedCursor <= maxAmount) {
            return;
        }

        // Check if the items from the inventory gets added to the players inventory after closing
        Inventory topInventory = player.getOpenInventory().getTopInventory();

        // Check how many backpacks are too much and check every item from the top inventory
        // and drop them until we reached the max amount of the player inventory
        int overflow = countAfterDroppedCursor - maxAmount;
        int dropped = 0;
        for (ItemStack content : player.getInventory().getContents()) {
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
            dropped += 1;
            if (overflow == 0) {
                break;
            }
            overflow -= 1;
        }
        if(dropped > 0) {
            player.sendMessage(
                    plugin.getBackpackConfig().getMessages().getExceedsLimitAmount()
                            .replaceAll("%limit_amount%", String.valueOf(maxAmount))
            );
        }
    }


    @EventHandler
    public void onLimitAmountBackpackDrag(InventoryDragEvent event) {
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
        if (!InventoryUtils.isBottomDrag(event.getRawSlots(), player)) {
            return;
        }
        Inventory bottom = player.getOpenInventory().getBottomInventory();
        if (bottom.getType() != InventoryType.PLAYER) {
            return;
        }
        int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
        if (maxAmount <= -1) {
            return;
        }
        int backpackCount = plugin.countBackpacks(player);
        if (backpackCount < maxAmount) {
            return;
        }
        event.setResult(Event.Result.DENY);
        event.setCursor(cursorItem);
        event.setCancelled(true);
    }


    // Make backpacks craft-able and unstackable
    @EventHandler
    public void onLimitAmountBackpackCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack resultItem = inventory.getResult();
        if (resultItem == null) {
            return;
        }
        if (!(event.getView().getPlayer() instanceof Player player)) {
            return;
        }
        if (!plugin.isBackpack(resultItem)) {
            return;
        }
        Recipe recipe = event.getRecipe();
        if (!(recipe instanceof Keyed keyed)) {
            return;
        }
        ItemMeta resultMeta = resultItem.getItemMeta();
        if (resultMeta == null) {
            return;
        }

        // Check if limiting amount is enabled
        int maxAmount = plugin.getBackpackConfig().getRestrictions().getMaxPlayerInventoryAmount();
        if (maxAmount <= -1) {
            return;
        }

        int backpackCount = plugin.countBackpacks(player);
        // The result is counted as backpack, so we need to subtract one from the total count
        backpackCount -= 1;

        // Check if the player wants to craft a colored backpack, in that case the result- and matrixItem is count as one
        // and we need to subtract one more backpack from the count
        boolean coloredCrafting = keyed.getKey().getKey().startsWith(BackpackPlugin.RECIPE_KEY_PREFIX + "_");
        if(coloredCrafting) {
            backpackCount -= 1;
        }
        if (backpackCount < maxAmount) {
            return;
        }
        player.sendMessage(
                plugin.getBackpackConfig().getMessages().getExceedsLimitAmount()
                        .replaceAll("%limit_amount%", String.valueOf(maxAmount))
        );
        event.getInventory().setResult(new ItemStack(Material.AIR));
    }
}
