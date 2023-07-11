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
public class Handling {

    @YamlKey("open-backpack-through-inventory")
    @YamlCommentHead({
            "Set this to true, so players can open backpacks through ",
            "double-click on the item in their own inventory.",
            "It doesn't work if they click them e.g. in chests, hoppers, barrels, etc.",
            "They have to use it in their own inventory."
    })
    boolean openThroughInventory = false;

    @YamlKey("close-backpack-on-damage")
    @YamlCommentHead({
            "Set this to true, so players automatically",
            "closes the currently open backpacks",
            "after they get damaged by anything."
    })
    boolean closeOnDamage = false;

    @YamlKey("close-backpack-on-eject")
    @YamlCommentHead({
            "Set this to true, so players automatically",
            "closes the currently open backpacks",
            "after they get eject from any vehicle"
    })
    boolean closeOnEject = false;
}
