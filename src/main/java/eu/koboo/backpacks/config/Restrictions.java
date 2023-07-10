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
public class Restrictions {

    @YamlKey("limit-amount-in-player-inventory")
    @YamlCommandHead({
            "Use this option to limit the amount of backpacks",
            "a player can put into his inventory.",
            "",
            "-1 = no limit/max backpacks"
    })
    int maxPlayerInventoryAmount = 2;

    @YamlKey("allow-backpack-in-backpack")
    @YamlCommandHead({
            "Use this option to decide if a player",
            "can put a backpack in a backpack in a backpack and so on.."
    })
    boolean allowRecursion = false;

    @YamlKey("only-owner-can-open")
    @YamlCommandHead({
            "Use this option to decide if only the owner",
            "of the backpack can open it."
    })
    boolean onlyOwnerCanOpen = false;

    @YamlKey("open-every-backpack-permission")
    @YamlCommandHead({
            "This permission is used to check if the player",
            "can open the backpack, but only if the above option is set to true."
    })
    String openEveryBackpackPermission = "backpacks.open.every.backpack";
}
