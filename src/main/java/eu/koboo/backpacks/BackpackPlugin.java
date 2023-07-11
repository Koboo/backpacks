package eu.koboo.backpacks;

import com.jeff_media.morepersistentdatatypes.DataType;
import eu.koboo.backpacks.config.Config;
import eu.koboo.backpacks.listener.*;
import eu.koboo.backpacks.textures.TextureApplier;
import eu.koboo.backpacks.utils.BackpackColor;
import eu.koboo.backpacks.utils.InventoryUtils;
import eu.koboo.yaml.config.ConfigurationLoader;
import lombok.Getter;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.recipe.CraftingBookCategory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.PluginLoadOrder;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.*;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

@Plugin(name = "PROJECT_NAME", version = "PROJECT_VERSION")
@ApiVersion(ApiVersion.Target.v1_20)
@Author("PROJECT_GROUP")
@Description("PROJECT_DESCRIPTION")
@LoadOrder(PluginLoadOrder.POSTWORLD)
@Website("PROJECT_WEBSITE")
@SuppressWarnings("all")
public class BackpackPlugin extends JavaPlugin {

    @Getter
    private static BackpackPlugin plugin;

    public static final int BSTATS_ID = 19062;

    // If player has put a backpack into that inventories, he will collect another backpack,
    // so we need to drop other backpacks, because the backpack from the top inventory is put into his inventory.
    public static final List<InventoryType> INVENTORY_TYPES_ADDED_AFTER_CLOSE = Arrays.asList(
            InventoryType.WORKBENCH,
            InventoryType.CRAFTING,
            InventoryType.ANVIL,
            InventoryType.ENCHANTING,
            InventoryType.STONECUTTER
    );

    // The string key of the root recipe and the key prefix of the colored recipes
    public static final String RECIPE_KEY_PREFIX = "backpack_recipe";

    // The raw slot of the helmet equipment slot in the player inventory
    public static final int HELMET_RAW_SLOT = 5;

    /* TODO:
        - Open cooldown
        - blacklisted items in backpacks
        - Fix equipping in creative
        - Create different slot types
        - Permissions per color
        - Permissions for commands
        - Auto insert in chests, barrels, enderchests, trapped chest, shulker boxes
        - Backpack command
            - Reload
            - Give
            - Recipe
        - More display config:
            - Show slots used
            - Show raw material list
         - UpdateNotification (console and permission)
         - Developer API (Events)
    */

    ConfigurationLoader configurationLoader;

    @Getter
    Config backpackConfig;

    TextureApplier textureApplier;

    @Getter
    NamespacedKey itemIdentifierKey;
    @Getter
    NamespacedKey itemUnstackableKey;
    @Getter
    NamespacedKey itemContentKey;
    /*
    @Getter
    NamespacedKey itemSizeKey;
    */
    @Getter
    NamespacedKey itemOwnerKey;
    @Getter
    NamespacedKey rootBackpackRecipeKey;
    @Getter
    NamespacedKey openBackpackKey;

    @Getter
    List<Keyed> recipeKeyList;

    Metrics metrics;

    @Override
    public void onEnable() {
        /*
        try {
            Class.forName("com.destroystokyo.paper.profile.PlayerProfile");
        } catch (Exception e) {
            getLogger().log(Level.WARNING, "You're not running PaperMC! Backpacks will only run on PaperMC. Shutting down..");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        */

        plugin = this;

        itemIdentifierKey = NamespacedKey.fromString("backpack_item", this);
        itemUnstackableKey = NamespacedKey.fromString("backpack_unstackable", this);
        itemContentKey = NamespacedKey.fromString("backpack_content", this);
        //itemSizeKey = NamespacedKey.fromString("backpack_size", this);
        itemOwnerKey = NamespacedKey.fromString("backpack_owner", this);
        rootBackpackRecipeKey = NamespacedKey.fromString(RECIPE_KEY_PREFIX, this);
        openBackpackKey = NamespacedKey.fromString("backpack_open_backpack", this);

        getDataFolder().mkdirs();
        configurationLoader = new ConfigurationLoader();

        reloadConfig();

        textureApplier = TextureApplier.createApplier();

        Bukkit.getPluginManager().registerEvents(new ListenerAutoClose(this), this);
        Bukkit.getPluginManager().registerEvents(new ListenerBackpackInShulkerBox(this), this);
        Bukkit.getPluginManager().registerEvents(new ListenerCancelEquip(this), this);
        Bukkit.getPluginManager().registerEvents(new ListenerCancelRecursion(this), this);
        Bukkit.getPluginManager().registerEvents(new ListenerDiscoverRecipes(this), this);
        Bukkit.getPluginManager().registerEvents(new ListenerItemCraft(this), this);
        Bukkit.getPluginManager().registerEvents(new ListenerLimitAmount(this), this);
        Bukkit.getPluginManager().registerEvents(new ListenerOpenClose(this), this);
        Bukkit.getPluginManager().registerEvents(new ListenerShulkerBoxInBackpack(this), this);

        metrics = new Metrics(this, BSTATS_ID);

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void reloadConfig() {
        File configFile = createConfigFile();
        loadConfigFile(configFile);
        createRecipes();
    }

    private File createConfigFile() {
        return new File(getDataFolder(), "config.yml");
    }

    private void loadConfigFile(File configFile) {
        try {
            if (!configFile.exists()) {
                configurationLoader.saveToFile(new Config(), configFile);
                getLogger().log(Level.INFO, "Successful exported default configuration file..");
            }
            backpackConfig = configurationLoader.loadFromFile(Config.class, configFile);
            getLogger().log(Level.INFO, "Successful loaded configuration file!");

            configurationLoader.saveToFile(backpackConfig, configFile);
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Couldn't read configuration, shutting down:", e);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void createRecipes() {
        if (recipeKeyList != null) {
            recipeKeyList.clear();
        }
        recipeKeyList = new ArrayList<>();

        if (!backpackConfig.getCrafting().isCreateRecipes()) {
            return;
        }
        Bukkit.removeRecipe(rootBackpackRecipeKey);

        List<String> craftingPattern = backpackConfig.getCrafting().getCraftingPattern();
        if (craftingPattern.isEmpty()) {
            throw new RuntimeException("Couldn't create crafting recipe, no recipe pattern provided");
        }
        if (craftingPattern.size() > 3) {
            throw new RuntimeException("Couldn't create crafting recipe, recipe pattern has more than 3 lines");
        }

        ShapedRecipe rootBackpackRecipe = new ShapedRecipe(rootBackpackRecipeKey, createBackpack(BackpackColor.BROWN));
        rootBackpackRecipe.shape(
                craftingPattern.get(0),
                craftingPattern.get(1),
                craftingPattern.get(2)
        );
        for (String materialLine : backpackConfig.getCrafting().getItemList()) {
            String[] lineSplit = materialLine.split(":");
            String charKey = lineSplit[0];
            Material material = Material.valueOf(lineSplit[1].toUpperCase(Locale.ROOT));
            rootBackpackRecipe.setIngredient(charKey.charAt(0), material);
        }
        rootBackpackRecipe.setCategory(backpackConfig.getCrafting().getCategory());
        Bukkit.addRecipe(rootBackpackRecipe);
        recipeKeyList.add(rootBackpackRecipe);

        boolean allowDifferentColors = backpackConfig.getAppearance().isAllowColoring();
        for (BackpackColor color : BackpackColor.values()) {
            Bukkit.removeRecipe(color.getRecipeKey());
            if (!allowDifferentColors) {
                continue;
            }
            ShapelessRecipe colorRecipe = new ShapelessRecipe(color.getRecipeKey(), createBackpack(color));
            colorRecipe.addIngredient(1, Material.PLAYER_HEAD);
            colorRecipe.addIngredient(1, color.getMaterial());
            colorRecipe.setCategory(CraftingBookCategory.EQUIPMENT);
            Bukkit.addRecipe(colorRecipe);
            recipeKeyList.add(colorRecipe);
        }
    }

    public ItemStack createBackpack(BackpackColor color) {

        if (color == null) {
            color = BackpackColor.NONE;
        }

        ItemStack headItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) headItem.getItemMeta();

        textureApplier.applyTexture(color, skullMeta);

        String backpackName = backpackConfig.getAppearance().getDefaultBackpackName();
        skullMeta.setDisplayName(backpackName);

        PersistentDataContainer pdc = skullMeta.getPersistentDataContainer();
        pdc.set(itemIdentifierKey, DataType.BOOLEAN, true);
        //pdc.set(itemSizeKey, DataType.STRING, backpackConfig.getCrafting().getSize().name());

        headItem.setItemMeta(skullMeta);
        return headItem;
    }

    public boolean isBackpack(ItemStack itemStack) {
        if (itemStack == null) {
            return false;
        }
        if (itemStack.getType() != Material.PLAYER_HEAD) {
            return false;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null || !itemMeta.hasDisplayName()) {
            return false;
        }
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.has(itemIdentifierKey, DataType.BOOLEAN);
    }

    public UUID getBackpackIdByItem(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return null;
        }
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        return pdc.get(itemUnstackableKey, DataType.UUID);
    }

    public ItemStack findBackpackById(Player player, UUID backpackId) {
        ItemStack backpackItem = null;
        Inventory bottomInventory = player.getOpenInventory().getBottomInventory();
        for (ItemStack content : bottomInventory.getContents()) {
            if (content == null || content.getType() != Material.PLAYER_HEAD) {
                continue;
            }
            UUID contentId = getBackpackIdByItem(content);
            if (!contentId.equals(backpackId)) {
                continue;
            }
            backpackItem = content;
            break;
        }
        return backpackItem;
    }

    public UUID getOpenBackpackId(Player player) {
        // Getting the open backpack id from the players pdc
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        return pdc.getOrDefault(openBackpackKey, DataType.UUID, null);
    }

    public void setOpenBackpackId(Player player, UUID uuid) {
        // Saving the open backpack id into the players pdc
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        if (uuid != null) {
            pdc.set(openBackpackKey, DataType.UUID, uuid);
            return;
        }
        // if uuid is null, we just remove it from the pdc
        pdc.remove(openBackpackKey);
    }

    public boolean hasOpenBackback(Player player) {
        Inventory top = player.getOpenInventory().getTopInventory();
        if (top.getType() != InventoryType.CHEST) {
            return false;
        }
        UUID backpackId = getOpenBackpackId(player);
        if (backpackId == null) {
            return false;
        }
        ItemStack itemInHand = findBackpackById(player, backpackId);
        if (!isBackpack(itemInHand)) {
            return false;
        }
        return true;
    }

    public void handleShift(Player player, int slot, ItemStack currentItem, InventoryType.SlotType slotType) {

        PlayerInventory playerInventory = player.getInventory();
        boolean freeHotbar = slotType == InventoryType.SlotType.CONTAINER;
        int shiftSlot = InventoryUtils.findFreeSlot(playerInventory, freeHotbar);

        if (shiftSlot == -1) {
            return;
        }

        playerInventory.setItem(shiftSlot, currentItem);
        playerInventory.setItem(slot, new ItemStack(Material.AIR));
    }

    public int countBackpacks(Player player) {
        int count = 0;
        InventoryView view = player.getOpenInventory();
        count += countBackpacks(view.getBottomInventory());
        Inventory topInventory = view.getTopInventory();
        if (INVENTORY_TYPES_ADDED_AFTER_CLOSE.contains(topInventory.getType())) {
            int topCount = countBackpacks(topInventory);
            count += topCount;
        }
        ItemStack onCursor = player.getItemOnCursor();
        if (plugin.isBackpack(onCursor)) {
            count += 1;
        }
        return count;
    }

    public int countBackpacks(Inventory inventory) {
        int count = 0;
        for (ItemStack content : inventory.getContents()) {
            if (content == null || content.getType() == Material.AIR) {
                continue;
            }
            if (!isBackpack(content)) {
                continue;
            }
            count += 1;
        }
        return count;
    }
}
