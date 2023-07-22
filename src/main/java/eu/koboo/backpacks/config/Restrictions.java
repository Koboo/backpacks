package eu.koboo.backpacks.config;

import eu.koboo.yaml.config.YamlComment;
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
    @YamlComment({
            "Use this option to limit the amount of backpacks",
            "in a player inventory.",
            "",
            "-1 = no limit of backpacks / disabled"
    })
    int maxPlayerInventoryAmount = 2;

    @YamlKey("allow-backpack-in-backpack")
    @YamlComment({
            "Use this option to choose if a player",
            "can put a backpack in a backpack in a backpack and so on.."
    })
    boolean allowRecursion = false;

    @YamlKey("allow-shulker-box-in-backpack")
    @YamlComment({
            "Use this option to choose if a player",
            "can put a shulker box in a backpack."
    })
    boolean allowShulkerInBackpack = false;

    @YamlKey("allow-backpack-in-shulker-box")
    @YamlComment({
            "Use this option to choose if a player",
            "can put a backpack in a shulker box."
    })
    boolean allowBackpackInShulker = false;

    @YamlKey("only-owner-can-open")
    @YamlComment({
            "Use this option to decide if only the owner",
            "of the backpack can open it."
    })
    boolean onlyOwnerCanOpen = false;

    @YamlKey("disabled-in-worlds")
    @YamlComment({
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
