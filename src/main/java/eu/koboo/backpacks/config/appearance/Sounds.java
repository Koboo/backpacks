package eu.koboo.backpacks.config.appearance;

import eu.koboo.yaml.config.YamlComment;
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
    @YamlComment({
            "Choose if it should a play the configured sound on opening or closing a backpack.",
            "\"false\" to disable it"
    })
    boolean useSounds = true;

    @YamlKey("only-player-sounds")
    @YamlComment({
            "true = only the player opening will hear the sound",
            "false = every nearby player will here the sound"
    })
    boolean onlyPlayerSounds = true;

    @YamlEmbedded("open-sound")
    @YamlComment({
            "The sound, that should play if the player OPENS a backpack."
    })
    ConfigSound openSound = ConfigSound.of(Sound.BLOCK_CHEST_OPEN, 0.5f, 0.5f);

    @YamlEmbedded("close-sound")
    @YamlComment({
            "The sound, that should play if the player CLOSES a backpack."
    })
    ConfigSound closeSound = ConfigSound.of(Sound.BLOCK_CHEST_CLOSE, 0.5f, 0.5f);
}
