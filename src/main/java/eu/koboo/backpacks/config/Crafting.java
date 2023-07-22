package eu.koboo.backpacks.config;

import eu.koboo.backpacks.utils.BackpackSize;
import eu.koboo.yaml.config.YamlComment;
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

    @YamlKey("create-recipes")
    @YamlComment({
            "Enable or disable the creation of crafting recipes of backpacks."
    })
    boolean createRecipes = true;

    @YamlKey("discover-all-recipes")
    @YamlComment({
            "This option decides if the player should",
            "discover all backpack crafting recipes at once, after he joined the server."
    })
    boolean discoverAllRecipes = true;

    @YamlKey("default-backpack-size")
    @YamlComment({
            "This option sets the default size of all backpacks.",
            "The value describes the amount of columns like in chests.",
            "Values:",
            "ONE, TWO, THREE, FOUR, FIVE, SIX"
    })
    BackpackSize size = BackpackSize.THREE;

    @YamlKey("crafting-book-category")
    @YamlComment({
            "Set the category of the crafting recipes",
            "in the recipe-book on the right side of the inventory.",
            "See this list:",
            "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/inventory/recipe/CraftingBookCategory.html"
    })
    CraftingBookCategory category = CraftingBookCategory.EQUIPMENT;

    @YamlKey("recipe-pattern")
    @YamlComment({
            "Define the crafting pattern for a backpack.",
            "Use the 3 lines like in a crafting-table and assign items in the list below."
    })
    List<String> craftingPattern = Arrays.asList("LLL", "SDS", "LLL");

    @YamlKey("recipe-items")
    @YamlComment({
            "Define the required item for the backpack crafting-recipe.",
            "Set a Material for every letter from the list above",
            "See list of items/materials:",
            "https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html"

    })
    List<String> itemList = Arrays.asList("L:LEATHER", "S:LEAD", "D:DIAMOND");
}
