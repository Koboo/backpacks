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
            "This permission is used to check if the player",
            "can open the backpack, but only if the above option is set to true."
    })
    String openEveryBackpackPermission = "backpacks.open.every.backpack";
}
