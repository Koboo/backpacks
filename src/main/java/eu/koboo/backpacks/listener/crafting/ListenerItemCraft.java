package eu.koboo.backpacks.listener.crafting;

import com.jeff_media.morepersistentdatatypes.DataType;
import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.config.Messages;
import eu.koboo.backpacks.config.Permissions;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.GameMode;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerItemCraft implements Listener {

    BackpackPlugin plugin;

    // Make backpacks craft-able and unstackable
    @EventHandler(priority = EventPriority.HIGH)
    public void onCraftBackpack(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack resultItem = inventory.getResult();
        if (resultItem == null) {
            return;
        }
        if (!(event.getView().getPlayer() instanceof Player player)) {
            return;
        }
        if (player.getGameMode() == GameMode.SPECTATOR) {
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
        PersistentDataContainer resultPDC = resultMeta.getPersistentDataContainer();

        Messages messages = plugin.getMessages();
        Permissions permissions = plugin.getPermissions();

        NamespacedKey unstackableKey = plugin.getItemUnstackableKey();
        NamespacedKey contentKey = plugin.getItemContentKey();
        NamespacedKey ownerKey = plugin.getItemOwnerKey();

        // Check if the player wants to craft a colored or a completely new backpack
        boolean useOwnerUniqueIds = plugin.getBackpackConfig().getHandling().isUseUniqueIds();
        if (!keyed.getKey().getKey().startsWith(BackpackPlugin.RECIPE_KEY_PREFIX + "_")) {
            // Crafting a new backpack
            if (!player.hasPermission(permissions.getCraftDefaultBackpack())) {
                player.sendMessage(messages.getNotAllowedToCraftDefault());
                event.getInventory().setResult(new ItemStack(Material.AIR));
                return;
            }

            // Assign a new backpackId if the result doesn't have an id
            UUID resultBackpackId = UUID.randomUUID();
            resultPDC.set(unstackableKey, DataType.UUID, resultBackpackId);

            // Setting the owners id on the backpack
            if (useOwnerUniqueIds) {
                resultPDC.set(ownerKey, DataType.UUID, player.getUniqueId());
            } else {
                resultPDC.set(ownerKey, DataType.STRING, player.getName());
            }

            resultItem.setItemMeta(resultMeta);
            return;
        }
        // Crafting a colored backpack
        if (!player.hasPermission(permissions.getCraftColoredBackpack())) {
            player.sendMessage(messages.getNotAllowedToCraftColored());
            event.getInventory().setResult(new ItemStack(Material.AIR));
            return;
        }

        // Check if the crafting table has a valid backpack player head
        ItemStack matrixItem = null;
        for (ItemStack matrixValue : event.getInventory().getMatrix()) {
            if (matrixValue == null || matrixValue.getType() != Material.PLAYER_HEAD) {
                continue;
            }
            if (!plugin.isBackpack(matrixValue)) {
                continue;
            }
            matrixItem = matrixValue;
            break;
        }
        // if no valid backpack is found, clear the result
        if (matrixItem == null) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
            return;
        }

        // Copy the properties of the colored backpack to the new result
        ItemMeta matrixMeta = matrixItem.getItemMeta();
        PersistentDataContainer matrixPDC = matrixMeta.getPersistentDataContainer();

        // Don't color backpacks that are not your property!
        if (useOwnerUniqueIds
                && !player.hasPermission(plugin.getPermissions().getOpenEveryBackpack())
                && matrixPDC.has(ownerKey, DataType.UUID)
                && !player.getUniqueId().equals(matrixPDC.get(ownerKey, DataType.UUID))) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
            return;
        }
        if (!useOwnerUniqueIds
                && !player.hasPermission(plugin.getPermissions().getOpenEveryBackpack())
                && matrixPDC.has(ownerKey, DataType.STRING)
                && !player.getName().equals(matrixPDC.get(ownerKey, DataType.STRING))) {
            event.getInventory().setResult(new ItemStack(Material.AIR));
            return;
        }

        // Setting backpack content
        copyValue(matrixPDC, resultPDC, contentKey, DataType.STRING);

        // Setting backpack id
        copyValue(matrixPDC, resultPDC, unstackableKey, DataType.UUID);

        // Setting owner id
        if (useOwnerUniqueIds) {
            if (matrixPDC.has(ownerKey, DataType.UUID)) {
                copyValue(matrixPDC, resultPDC, ownerKey, DataType.UUID);
            } else {
                matrixPDC.remove(ownerKey);
                resultPDC.set(ownerKey, DataType.UUID, player.getUniqueId());
            }
        } else {
            if (matrixPDC.has(ownerKey, DataType.STRING)) {
                copyValue(matrixPDC, resultPDC, ownerKey, DataType.STRING);
            } else {
                matrixPDC.remove(ownerKey);
                resultPDC.set(ownerKey, DataType.STRING, player.getName());
            }
        }

        // Setting backpack name
        if (matrixMeta.hasDisplayName()) {
            resultMeta.setDisplayName(matrixMeta.getDisplayName());
        }

        // Resetting the item meta
        resultItem.setItemMeta(resultMeta);
    }

    private <T, Z> void copyValue(PersistentDataContainer from, PersistentDataContainer to,
                                  NamespacedKey key,
                                  PersistentDataType<Z, T> dataType) {
        T valueType = from.get(key, dataType);
        if (valueType != null) {
            to.set(key, dataType, valueType);
        } else {
            to.remove(key);
        }
    }
}
