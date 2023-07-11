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
public class Permissions {

    @YamlKey("open-every-backpack-permission")
    @YamlCommentHead({
            "This permission is used to check if the player can open the backpack.",
            "!! Only takes action, if only owners can open backpacks !!"
    })
    String openEveryBackpack = "backpacks.open.every.backpack";

    @YamlKey("craft-default-backpack")
    @YamlCommentHead({
            "This permission is used to check if the player can craft the default backpack."
    })
    String craftDefaultBackpack = "backpacks.craft.default";

    @YamlKey("craft-colored-backpacks")
    @YamlCommentHead({
            "This permission is used to check if the player can craft the colored backpacks."
    })
    String craftColoredBackpack = "backpacks.craft.colored";

    @YamlKey("ignore-world-restriction")
    @YamlCommentHead({
            "This permission is used to check if the player can ignore the disabled worlds when opening backpacks."
    })
    String ignoreWorldRestriction = "backpacks.ignore.world.restriction";
}
