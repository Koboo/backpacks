package eu.koboo.backpacks.listener;

import com.jeff_media.morepersistentdatatypes.DataType;
import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.config.Config;
import eu.koboo.backpacks.config.appearance.Appearance;
import eu.koboo.backpacks.config.appearance.ConfigSound;
import eu.koboo.backpacks.config.appearance.Sounds;
import eu.koboo.backpacks.utils.BackpackSize;
import eu.koboo.backpacks.utils.ItemUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
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

import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ListenerOpenClose implements Listener {

    BackpackPlugin plugin;

    // Handle backpack open event
    @EventHandler
    public void onInteractBackpack(PlayerInteractEvent event) {
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
    public void onPlaceBackpack(BlockPlaceEvent event) {
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
    @EventHandler
    public void onCloseBackpack(InventoryCloseEvent event) {
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

        // Search for the backpack item by the id
        ItemStack backpackItem = plugin.findBackpackById(player, backpackId);
        if (backpackItem == null) {
            return;
        }

        String contentBase64 = ItemUtils.toBase64(event.getInventory());

        ItemMeta itemMeta = backpackItem.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

        // Getting previous content of backapck
        String previousContent = pdc.get(plugin.getItemContentKey(), PersistentDataType.STRING);
        // Same serialized content, ignoring
        if (previousContent != null
                && !previousContent.isEmpty()
                && previousContent.equals(contentBase64)) {
            return;
        }

        // Setting the new serialized content
        pdc.set(plugin.getItemContentKey(), PersistentDataType.STRING, contentBase64);
        backpackItem.setItemMeta(itemMeta);

        // Resetting the open backpack id
        plugin.setOpenBackpackId(player, null);

        Sounds sounds = plugin.getBackpackConfig().getAppearance().getSounds();
        if(sounds.isUseSounds()) {
            ConfigSound closeSound = sounds.getCloseSound();
            if(sounds.isOnlyPlayerSounds()) {
                player.playSound(
                        player.getLocation(), closeSound.getSound(), closeSound.getVolume(), closeSound.getPitch()
                );
            } else {
                player.getWorld().playSound(
                        player.getLocation(), closeSound.getSound(), closeSound.getVolume(), closeSound.getPitch()
                );
            }
        }

    }

    private void openBackpack(Player player, ItemStack backpackItem) {
        if(backpackItem == null) {
            return;
        }

        // Checking we got any itemMeta to use
        ItemMeta itemMeta = backpackItem.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        Config backpackConfig = plugin.getBackpackConfig();

        // Getting the inventory name by item or config
        Component inventoryName = null;
        if (itemMeta.hasDisplayName()) {
            inventoryName = itemMeta.displayName();
        }
        Appearance appearance = backpackConfig.getAppearance();
        if (inventoryName == null) {
            inventoryName = LegacyComponentSerializer.legacySection()
                    .deserialize(appearance.getDefaultBackpackName());
        }

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

        // Check if the player is permitted to open the used backpack
        if(backpackConfig.getRestrictions().isOnlyOwnerCanOpen()) {
            UUID ownerId = pdc.get(plugin.getItemOwnerKey(), DataType.UUID);
            if(ownerId != null) {
                if(!player.hasPermission(backpackConfig.getPermissions().getOpenEveryBackpack())
                        && !player.getUniqueId().equals(ownerId)) {
                    player.sendMessage(LegacyComponentSerializer.legacySection()
                            .deserialize(backpackConfig.getMessages().getNotAllowedToCraftColored()));
                    return;
                }
            }
        }

        // Getting the backpack size from config or item directly
        BackpackSize backpackSize = backpackConfig.getCrafting().getSize();
        String sizeString = pdc.get(plugin.getItemSizeKey(), PersistentDataType.STRING);
        if (sizeString != null) {
            backpackSize = BackpackSize.valueOf(sizeString.toUpperCase(Locale.ROOT));
        }

        // Creating the inventory by size and name
        Inventory inventory = Bukkit.createInventory(player, backpackSize.getSlotAmount(), inventoryName);

        // Setting the content into the backpack inventory
        String content = pdc.get(plugin.getItemContentKey(), PersistentDataType.STRING);
        Map<Integer, ItemStack> slotMap = ItemUtils.fromBase64(content);
        if (slotMap != null && !slotMap.isEmpty()) {
            for (Integer slot : slotMap.keySet()) {
                ItemStack itemStack = slotMap.get(slot);
                inventory.setItem(slot, itemStack);
            }
        }

        // Setting the open backpack id into the players pdc
        UUID backpackId = plugin.getBackpackIdByItem(backpackItem);
        plugin.setOpenBackpackId(player, backpackId);

        // Opening the inventory of the backpack
        player.openInventory(inventory);

        Sounds sounds = appearance.getSounds();
        if(sounds.isUseSounds()) {
            ConfigSound openSound = sounds.getOpenSound();
            if(sounds.isOnlyPlayerSounds()) {
                player.playSound(
                        player.getLocation(), openSound.getSound(), openSound.getVolume(), openSound.getPitch()
                );
            } else {
                player.getWorld().playSound(
                        player.getLocation(), openSound.getSound(), openSound.getVolume(), openSound.getPitch()
                );
            }
        }
    }
}
