package eu.koboo.backpacks.config.appearance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Sound;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class ConfigSound {

    Sound sound;
    float volume;
    float pitch;

    public static ConfigSound of(Sound sound, float volume, float pitch) {
        ConfigSound configSound = new ConfigSound();
        configSound.setSound(sound);
        configSound.setVolume(volume);
        configSound.setPitch(pitch);
        return configSound;
    }
}
