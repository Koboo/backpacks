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

    @YamlKey("not-allowed-to-open-message")
    @YamlCommentHead({
            "Player is not permitted to open the used backpack."
    })
    String notAllowedToOpen = "§cYou're not allowed to open that backpack.";

    @YamlKey("exceeds-limit-amount")
    @YamlCommentHead({
            "Player exceeds the set limit of backpack amount in own inventory."
    })
    String exceedsLimitAmount = "§cYou're exceeding the maximum amount of %limit_amount% backpacks in your inventory.";
}
