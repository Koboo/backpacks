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
public class Permissions {

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
}
