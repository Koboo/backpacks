package eu.koboo.backpacks.config;

import eu.koboo.backpacks.config.appearance.Appearance;
import eu.koboo.yaml.config.YamlEmbedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Config {

    @YamlEmbedded("crafting")
    Crafting crafting = new Crafting();

    @YamlEmbedded("appearance")
    Appearance appearance = new Appearance();

    @YamlEmbedded("restrictions")
    Restrictions restrictions = new Restrictions();

    @YamlEmbedded("handling")
    Handling handling = new Handling();

    @YamlEmbedded("messages")
    Messages messages = new Messages();

    @YamlEmbedded("permissions")
    Permissions permissions = new Permissions();

}
