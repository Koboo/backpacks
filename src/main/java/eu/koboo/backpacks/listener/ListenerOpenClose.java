package eu.koboo.backpacks.listener;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.utils.ItemUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Map;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerOpenClose implements Listener {

    BackpackPlugin plugin;

    // Handle backpack open event
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR) {
            return;
        }
        if (event.useItemInHand() == Event.Result.DENY) {
            return;
        }
        Player player = event.getPlayer();
        EntityEquipment equipment = player.getEquipment();
        EquipmentSlot usedHand = event.getHand();
        if (usedHand == null) {
            return;
        }
        ItemStack itemInHand = equipment.getItem(usedHand);
        if (!plugin.isBackpack(itemInHand)) {
            return;
        }
        event.setUseInteractedBlock(Event.Result.DENY);
        event.setUseItemInHand(Event.Result.DENY);
        openBackpack(player, itemInHand);
    }

    // Handle backpack place (right-click on block)
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        ItemStack itemInHand = event.getItemInHand();
        if (!plugin.isBackpack(itemInHand)) {
            return;
        }
        event.setCancelled(true);
        openBackpack(event.getPlayer(), itemInHand);
    }

    // Handle backpack close event
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }
        InventoryType type = event.getInventory().getType();
        if (type != InventoryType.CHEST) {
            return;
        }
        UUID backpackId = plugin.getOpenBackpackId(player);
        if (backpackId == null) {
            return;
        }
        plugin.saveBackpack(player, event.getInventory(), backpackId);
    }

    public void openBackpack(Player player, ItemStack backpackItem) {

        Component inventoryName = null;
        ItemMeta itemMeta = backpackItem.getItemMeta();
        if (itemMeta != null && itemMeta.hasDisplayName()) {
            inventoryName = itemMeta.displayName();
        }
        if (inventoryName == null) {
            inventoryName = Component.text("Backpack");
        }
        Inventory inventory = Bukkit.createInventory(player, plugin.getBackpackConfig().getSize().getSlotAmount(), inventoryName);

        if (itemMeta != null) {
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            String contentBase64 = pdc.get(plugin.getItemContentKey(), PersistentDataType.STRING);

            if (contentBase64 != null) {

                Map<Integer, ItemStack> slotMap = ItemUtils.fromBase64(contentBase64);
                if (slotMap != null && !slotMap.isEmpty()) {
                    for (Integer slot : slotMap.keySet()) {
                        ItemStack itemStack = slotMap.get(slot);
                        inventory.setItem(slot, itemStack);
                    }
                }
            }
        }
        UUID backpackId = plugin.getBackpackId(backpackItem);
        plugin.setOpenBackpackId(player, backpackId);
        player.openInventory(inventory);
    }
}
