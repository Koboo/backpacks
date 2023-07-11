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
public class Messages {

    @YamlKey("not-allowed-to-open")
    @YamlCommentHead({
            "Player is not permitted to open the used backpack."
    })
    String notAllowedToOpen = "§cYou're not allowed to open that backpack.";

    @YamlKey("exceeds-limit-amount")
    @YamlCommentHead({
            "Player exceeds the set limit of backpack amount in own inventory."
    })
    String exceedsLimitAmount = "§cYou're exceeding the maximum amount of %limit_amount% backpacks in your inventory.";

    @YamlKey("not-allowed-to-craft-default")
    @YamlCommentHead({
            "Player is not permitted to craft the default backpack."
    })
    String notAllowedToCraftDefault = "§cYou're not allowed to craft the default backpack.";

    @YamlKey("not-allowed-to-craft-colored")
    @YamlCommentHead({
            "Player is not permitted to craft the colored backpack."
    })
    String notAllowedToCraftColored = "§cYou're not allowed to craft the colored backpack.";

    @YamlKey("not-allowed-in-world")
    @YamlCommentHead({
            "Player is not permitted to open backpack in current world."
    })
    String notAllowedToOpenInWorld = "§cYou're not allowed to open the backpack in world %world_name%.";
}
