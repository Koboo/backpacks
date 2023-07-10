package eu.koboo.backpacks.listener;

import com.jeff_media.morepersistentdatatypes.DataType;
import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.config.Config;
import eu.koboo.backpacks.utils.BackpackSize;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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
    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
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
        PersistentDataContainer resultPDC = resultMeta.getPersistentDataContainer();

        Config backpackConfig = plugin.getBackpackConfig();
        NamespacedKey unstackableKey = plugin.getItemUnstackableKey();
        NamespacedKey sizeKey = plugin.getItemSizeKey();
        NamespacedKey contentKey = plugin.getItemContentKey();
        NamespacedKey ownerKey = plugin.getItemOwnerKey();

        // Check if the player wants to craft a colored or a completely new backpack
        if (!keyed.getKey().toString().startsWith(BackpackPlugin.RECIPE_KEY_PREFIX + "_")) {
            // Crafting a new backpack

            // Assign a new backpackId if the result doesn't have an id
            UUID resultBackpackId = UUID.randomUUID();
            resultPDC.set(unstackableKey, DataType.UUID, resultBackpackId);

            // Get the default size and set it in the results pdc
            BackpackSize defaultSize = backpackConfig.getCrafting().getSize();
            resultPDC.set(sizeKey, DataType.STRING, defaultSize.name());

            // Setting the owners id on the backpack
            resultPDC.set(ownerKey, DataType.UUID, player.getUniqueId());

            resultItem.setItemMeta(resultMeta);
            return;
        }
        // Crafting a colored backpack

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
            resultItem.setType(Material.AIR);
            resultItem.setAmount(0);
            return;
        }

        // Copy the properties of the colored backpack to the new result
        ItemMeta matrixMeta = matrixItem.getItemMeta();
        PersistentDataContainer matrixPDC = matrixMeta.getPersistentDataContainer();

        // Setting backpack content
        copyValue(matrixPDC, resultPDC, contentKey, DataType.STRING);
        /*
        String matrixContent = matrixPDC.get(contentKey, PersistentDataType.STRING);
        if (matrixContent != null) {
            resultPDC.set(contentKey, PersistentDataType.STRING, matrixContent);
        }
        */

        // Setting backpack id
        copyValue(matrixPDC, resultPDC, unstackableKey, DataType.UUID);
        /*
        UUID matrixBackpackId = matrixPDC.get(unstackableKey, DataType.UUID);
        if (matrixBackpackId != null) {
            resultPDC.set(unstackableKey, DataType.UUID, matrixBackpackId);
        }
         */

        // Setting the backpack size
        copyValue(matrixPDC, resultPDC, sizeKey, DataType.STRING);
        /*
        String sizeString = matrixPDC.get(sizeKey, DataType.STRING);
        if(sizeString != null) {
            resultPDC.set(sizeKey, DataType.STRING, sizeString);
        }
         */

        copyValue(matrixPDC, resultPDC, ownerKey, DataType.UUID);
        /*
        UUID ownerId = matrixPDC.get(ownerKey, DataType.UUID);
        if(ownerId != null) {
            resultPDC.set(ownerKey, DataType.UUID, ownerId);
        }
         */

        // Setting backpack name
        if (matrixMeta.hasDisplayName()) {
            resultMeta.displayName(matrixMeta.displayName());
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
