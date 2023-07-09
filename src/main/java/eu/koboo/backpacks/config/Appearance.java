package eu.koboo.backpacks.config;

import eu.koboo.yaml.config.YamlCommandHead;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Appearance {

    @YamlKey("allow-different-colors")
    @YamlCommandHead({"Enabled this option to allow", "crafting different backpack colors with dye."})
    boolean allowDifferentColors = true;

    @YamlKey("default-backpack-name")
    @YamlCommandHead({"Change the default name of a backpack", "to allow your players more customization."})
    String defaultBackpackName = "Backpack";
}
