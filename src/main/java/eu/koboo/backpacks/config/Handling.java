package eu.koboo.backpacks.config;

import eu.koboo.yaml.config.YamlComment;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Handling {

    @YamlKey("open-backpack-through-inventory")
    @YamlComment({
            "Set this to true, so players can open backpacks through ",
            "double-click on the item in their own inventory.",
            "It doesn't work if they click them e.g. in chests, hoppers, barrels, etc.",
            "They have to use it in their own inventory."
    })
    boolean openThroughInventory = true;

    @YamlKey("close-backpack-on-damage")
    @YamlComment({
            "Set this to true, so players automatically",
            "close the currently open backpack after they get damaged."
    })
    boolean closeOnDamage = true;

    @YamlKey("close-backpack-on-eject")
    @YamlComment({
            "Set this to true, so players automatically",
            "close the currently open backpack after they get eject from any vehicle or rideable animal."
    })
    boolean closeOnEject = true;

    @YamlKey("open-backpack-cooldown")
    @YamlComment({
            "Set the cooldown of the player opening backpacks.",
            "The value is given in seconds."
    })
    int openCooldown = 1;
}
