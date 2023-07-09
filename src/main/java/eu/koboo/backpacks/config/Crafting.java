package eu.koboo.backpacks.config;

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

    @YamlKey("crafting-book-category")
    CraftingBookCategory category = CraftingBookCategory.EQUIPMENT;

    @YamlKey("recipe-pattern")
    @YamlCommandHead({
            "Sets the pattern for the backpack to be crafted.",
            "Define the letters here"
    })
    List<String> craftingPattern = Arrays.asList("LLL", "SDS", "LLL");

    @YamlKey("recipe-items")
    List<String> itemList = Arrays.asList("L:LEATHER", "S:LEAD", "D:DIAMOND");
}
