package eu.koboo.backpacks.config;

import eu.koboo.yaml.config.YamlCommentHead;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Restrictions {

    @YamlKey("limit-amount-in-player-inventory")
    @YamlCommentHead({
            "Use this option to limit the amount of backpacks",
            "a player can put into his inventory.",
            "",
            "-1 equals no limit or max backpacks"
    })
    int maxPlayerInventoryAmount = 2;

    @YamlKey("allow-backpack-in-backpack")
    @YamlCommentHead({
            "Use this option to choose if a player",
            "can put a backpacks in a backpack in a backpack and so on.."
    })
    boolean allowRecursion = false;

    @YamlKey("allow-shulker-box-in-backpack")
    @YamlCommentHead({
            "Use this option to choose if a player",
            "can put a shulker boxes in a backpack."
    })
    boolean allowShulkerBoxIn = false;

    @YamlKey("only-owner-can-open")
    @YamlCommentHead({
            "Use this option to decide if only the owner",
            "of the backpack can open it."
    })
    boolean onlyOwnerCanOpen = false;

    @YamlKey("disabled-in-worlds")
    @YamlCommentHead({
            "Use this option to disable opening of backpacks",
            "in certain worlds.",
            "Examples:",
            "disabled-in-worlds: | ",
            "  - world",
            "  - world_nether",
            "  - world_the_end",
    })
    List<String> disabledWorldNames = new ArrayList<>();


}
