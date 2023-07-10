package eu.koboo.backpacks.config.appearance;

import eu.koboo.backpacks.config.Config;
import eu.koboo.yaml.config.YamlCommentHead;
import eu.koboo.yaml.config.YamlEmbedded;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Sound;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Sounds {

    @YamlKey("sounds-enabled")
    @YamlCommentHead({"Choose if it should a play a sound on opening or closing a backpack."})
    boolean useSounds = true;

    @YamlEmbedded("open-sound")
    @YamlCommentHead({"The sound, that should play if the player OPENS a backpack."})
    ConfigSound openSound = new ConfigSound(Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

    @YamlEmbedded("close-sound")
    @YamlCommentHead({"The sound, that should play if the player CLOSES a backpack."})
    ConfigSound closeSound = new ConfigSound(Sound.BLOCK_CHEST_CLOSE, 0.5f, 0.5f);
}
