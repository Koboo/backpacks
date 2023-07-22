package eu.koboo.backpacks.config;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.yaml.config.YamlComment;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Messages {

    @YamlKey(BackpackPlugin.MESSAGE_VERSION_KEY)
    @YamlComment("!! Do not touch this or it will break your message file !!")
    int configVersion = 1;

    @YamlKey("not-allowed-to-open")
    @YamlComment({
            "Player is not permitted to open the current backpack."
    })
    String notAllowedToOpen = "§cYou're not allowed to open that backpack.";

    @YamlKey("exceeds-limit-amount")
    @YamlComment({
            "Player exceeds the maximum limit of backpacks in his inventory."
    })
    String exceedsLimitAmount = "§cYou're exceeding the maximum amount of %limit_amount% backpacks in your inventory.";

    @YamlKey("not-allowed-to-craft-default")
    @YamlComment({
            "Player is not permitted to craft the default backpack."
    })
    String notAllowedToCraftDefault = "§cYou're not allowed to craft the default backpack.";

    @YamlKey("not-allowed-to-craft-colored")
    @YamlComment({
            "Player is not permitted to craft the colored backpack."
    })
    String notAllowedToCraftColored = "§cYou're not allowed to craft the colored backpack.";

    @YamlKey("not-allowed-in-world")
    @YamlComment({
            "Player is not permitted to open the backpack in the current world."
    })
    String notAllowedToOpenInWorld = "§cYou're not allowed to open the backpack in world %world_name%.";

    @YamlKey("command-no-permissions")
    @YamlComment({
            "Player is not permitted to use the specific command."
    })
    String commandNoPermissions = "§cYou're not allowed to execute that command.";

    @YamlKey("command-reload-success")
    @YamlComment({
            "Player has successfully reloaded the plugin config."
    })
    String commandReloadSuccess = "§aYou've reloaded the backpack config!";

    @YamlKey("command-color-not-found")
    @YamlComment({
            "The given color could not be found."
    })
    String commandColorNotFound = "§cCouldn't find the given color %color%!";

    @YamlKey("command-player-not-online")
    @YamlComment({
            "The given player is not online."
    })
    String commandPlayerNotOnline = "§cThe player %name% is not online!";

    @YamlKey("command-give-success")
    @YamlComment({
            "The player got a new backpack!"
    })
    String commandGiveSuccess = "§aYou've got a new backpack!";

    @YamlKey("command-give-success.other")
    @YamlComment({
            "The sender message if a player got a new backpack!"
    })
    String commandGiveSuccessOther = "§aYou gave %name% a new backpack.";
}
