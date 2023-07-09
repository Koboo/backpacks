package eu.koboo.backpacks.listener;

import com.jeff_media.morepersistentdatatypes.DataType;
import eu.koboo.backpacks.BackpackPlugin;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerItemCraft implements Listener {

    BackpackPlugin plugin;

    // Make backpacks craftable and unstackable
    @EventHandler
    public void onCraft(PrepareItemCraftEvent event) {
        CraftingInventory inventory = event.getInventory();
        ItemStack result = inventory.getResult();
        if (result == null) {
            return;
        }
        if (!plugin.isBackpack(result)) {
            return;
        }
        Recipe recipe = event.getRecipe();
        if (!(recipe instanceof Keyed keyed)) {
            return;
        }
        ItemMeta resultMeta = result.getItemMeta();
        if (resultMeta == null) {
            return;
        }
        PersistentDataContainer resultPDC = resultMeta.getPersistentDataContainer();
        if (!resultPDC.has(plugin.getItemUnstackableKey())) {
            UUID value = UUID.randomUUID();
            setUUID(resultMeta, value);
            result.setItemMeta(resultMeta);
        }
        if (keyed.getKey().toString().contains("as_backpack_recipe_")) {
            ItemStack backpackMatrixItem = null;
            for (ItemStack matrixItem : event.getInventory().getMatrix()) {
                if (matrixItem == null || matrixItem.getType() != Material.PLAYER_HEAD) {
                    continue;
                }
                if (!plugin.isBackpack(matrixItem)) {
                    continue;
                }
                backpackMatrixItem = matrixItem;
                break;
            }
            if (backpackMatrixItem == null) {
                result.setType(Material.AIR);
                result.setAmount(0);
                return;
            }
            ItemMeta matrixMeta = backpackMatrixItem.getItemMeta();
            PersistentDataContainer matrixPDC = matrixMeta.getPersistentDataContainer();
            NamespacedKey contentKey = plugin.getItemContentKey();
            String contentBase64 = matrixPDC.get(contentKey, PersistentDataType.STRING);
            if (contentBase64 == null) {
                return;
            }
            resultPDC.set(contentKey, PersistentDataType.STRING, contentBase64);
            UUID unstackableKey = matrixPDC.get(plugin.getItemUnstackableKey(), DataType.UUID);
            if (unstackableKey != null) {
                resultPDC.remove(plugin.getItemUnstackableKey());
                setUUID(resultMeta, unstackableKey);
            }
            if (matrixMeta.hasDisplayName()) {
                resultMeta.displayName(matrixMeta.displayName());
            }
            result.setItemMeta(resultMeta);
        }
    }

    private void setUUID(ItemMeta resultMeta, UUID value) {
        resultMeta.lore(Collections.singletonList(LegacyComponentSerializer.legacySection().deserialize("§8" + value)));
        resultMeta.getPersistentDataContainer().set(plugin.getItemUnstackableKey(), DataType.UUID, value);
    }
}
