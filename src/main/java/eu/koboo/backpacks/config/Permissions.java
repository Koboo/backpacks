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
public class Permissions {

    @YamlKey(BackpackPlugin.PERMISSION_VERSION_KEY)
    @YamlComment("!! Do not touch this or it will break your permission file !!")
    int configVersion = 1;

    @YamlKey("use-permissions")
    @YamlComment({
            "Enable or disable permission checks."
    })
    boolean usePermissions = false;

    @YamlKey("update-notification")
    @YamlComment({
            "This permission is used to check if the player will get notifications if an update is available.",
    })
    String updateNotify = "backpacks.update.notify";

    @YamlKey("ignore-owner-restriction")
    @YamlComment({
            "This permission is used to check if the player can open the backpack.",
            "!! Only takes action, if only owners can open their backpacks !!"
    })
    String openEveryBackpack = "backpacks.ignore.owner.restriction";

    @YamlKey("craft-default-backpack")
    @YamlComment({
            "This permission is used to check if the player can craft the default backpack."
    })
    String craftDefaultBackpack = "backpacks.craft.default";

    @YamlKey("craft-colored-backpacks")
    @YamlComment({
            "This permission is used to check if the player can craft any colored backpacks."
    })
    String craftColoredBackpack = "backpacks.craft.colored";

    @YamlKey("ignore-world-restriction")
    @YamlComment({
            "This permission is used to check if the player can ignore the disabled worlds when opening backpacks.",
            "!! Only takes action, if player is in disabled world !!"
    })
    String ignoreWorldRestriction = "backpacks.ignore.world.restriction";

    @YamlKey("command-usage")
    @YamlComment({
            "This permission is used to check if the player can execute any command."
    })
    String commandUsage = "backpacks.command";

    @YamlKey("command-reload")
    @YamlComment({
            "This permission is used to check if the player can execute the config reload command."
    })
    String commandReload = "backpacks.command.reload";

    @YamlKey("command-give")
    @YamlComment({
            "This permission is used to check if the player can execute the give command."
    })
    String commandGive = "backpacks.command.give";

}
