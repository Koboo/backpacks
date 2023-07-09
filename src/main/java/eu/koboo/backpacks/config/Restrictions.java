package eu.koboo.backpacks.config;

import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Restrictions {

    @YamlKey("limit-amount-in-player-inventory")
    int maxPlayerInventoryAmount = 2;

    @YamlKey("allow-backpack-in-backpack")
    boolean allowRecursion = false;
}
