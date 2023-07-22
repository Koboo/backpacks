package eu.koboo.backpacks.config.appearance;

import eu.koboo.yaml.config.YamlComment;
import eu.koboo.yaml.config.YamlEmbedded;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Appearance {

    @YamlKey("allow-backpack-coloring")
    @YamlComment({
            "Enabling this option allows the player",
            "to craft different backpack-colors by combining",
            "any default dye with a backpack in the crafting table."
    })
    boolean allowColoring = true;

    @YamlKey("default-backpack-name")
    @YamlComment({
            "Change the default name of a fresh crafted backpack."
    })
    String defaultBackpackName = "Backpack";

    @YamlEmbedded("sounds")
    @YamlComment({
            "In this section you can change the sounds of the backpacks.",
            "You can also use the \"/playsound\" command to test the sound you want.",
            "See the list of available sounds:",
            "https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Sound.html"
    })
    Sounds sounds = new Sounds();
}
