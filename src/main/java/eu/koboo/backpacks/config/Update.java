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
public class Update {

    @YamlKey("notify-on-update")
    @YamlComment({
            "Notify the users with the permissions if an update is available."
    })
    boolean notifyOnUpdate = true;
}
