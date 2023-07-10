package eu.koboo.backpacks.config.appearance;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.Sound;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigSound {

    Sound sound;
    float volume;
    float pitch;
}
