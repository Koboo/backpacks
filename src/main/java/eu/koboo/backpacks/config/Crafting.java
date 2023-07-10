package eu.koboo.backpacks.config;

import eu.koboo.backpacks.utils.BackpackSize;
import eu.koboo.yaml.config.YamlCommandHead;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import java.util.Arrays;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Crafting {

    @YamlKey("allow-crafting")
    @YamlCommandHead({
            "Allow or disallow crafting of backpacks."
    })
    boolean allowCrafting = true;

    @YamlKey("discover-all-recipes")
    @YamlCommandHead({
            "This option decides if the player should",
            "discover all backpack recipes after he joined the server."
    })
    boolean discoverAllRecipes = true;

    @YamlKey("default-backpack-size")
    @YamlCommandHead({
            "This option sets the size of all backpacks.",
            "The value describes the amount of columns like chests.",
            "Values:",
            "ONE, TWO, THREE, FOUR, FIVE, SIX"
    })
    BackpackSize size = BackpackSize.THREE;

    @YamlKey("crafting-book-category")
    @YamlCommandHead({
            "Set the category of the crafting recipes.",
            "See this list:",
            "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/recipe/CraftingBookCategory.html"
    })
    CraftingBookCategory category = CraftingBookCategory.EQUIPMENT;

    @YamlKey("recipe-pattern")
    @YamlCommandHead({
            "Sets the pattern for the backpack to be crafted.",
            "Define the letters here and the item list below"
    })
    List<String> craftingPattern = Arrays.asList("LLL", "SDS", "LLL");

    @YamlKey("recipe-items")
    @YamlCommandHead({
            "Sets the required item for the backpack recipe.",
            "Define the items here and the letters list below.",
            "See list of items/materials:",
            "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html"

    })
    List<String> itemList = Arrays.asList("L:LEATHER", "S:LEAD", "D:DIAMOND");
}
