package eu.koboo.backpacks.config;

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
    boolean allowDifferentColors = true;

    @YamlKey("default-backpack-name")
    String defaultBackpackName = "Backpack";
}
