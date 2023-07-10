package eu.koboo.backpacks.config;

import eu.koboo.yaml.config.YamlCommentHead;
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
    @YamlCommentHead({
            "Enabling this option allows the player",
            "to craft different backpack colors by combining",
            "any dye with a backpack."
    })
    boolean allowColoring = true;

    @YamlKey("default-backpack-name")
    @YamlCommentHead({
            "Change the default name of a backpack",
            "to allow your players more customization."
    })
    String defaultBackpackName = "Backpack";
}
