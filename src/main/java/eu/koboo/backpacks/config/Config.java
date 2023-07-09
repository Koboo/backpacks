package eu.koboo.backpacks.config;

import eu.koboo.backpacks.utils.BackpackSize;
import eu.koboo.yaml.config.YamlCommandHead;
import eu.koboo.yaml.config.YamlEmbedded;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Config {

    @YamlEmbedded("restrictions")
    @YamlCommandHead({
            "Restrict the usage of the backpacks",
            "by limiting amount or backpacks in backpacks."
    })
    Restrictions restrictions = new Restrictions();

    @YamlEmbedded("appearance")
    @YamlCommandHead({
            "Change the options related to",
            "the appearance of the backpacks."
    })
    Appearance appearance = new Appearance();

    @YamlKey("discover-all-recipes")
    @YamlCommandHead({
            "This option decides if the player should",
            "discover all backpack recipes after he joined the server."
    })
    boolean discoverAllRecipes = true;

    @YamlKey("backpack-size")
    @YamlCommandHead({
            "This option sets the size of all backpacks.",
            "The value describes the amount of columns like chests.",
            "Values:",
            "ONE, TWO, THREE, FOUR, FIVE, SIX"
    })
    BackpackSize size = BackpackSize.THREE;

}
