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
            "This message gets send if the player is not permitted",
            "to open used the backpack."
    })
    String notAllowedOpenMessage = "§cYou're not allowed to open that backpack.";
}
