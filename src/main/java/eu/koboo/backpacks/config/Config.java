package eu.koboo.backpacks.config;

import eu.koboo.backpacks.BackpackPlugin;
import eu.koboo.backpacks.config.appearance.Appearance;
import eu.koboo.yaml.config.YamlComment;
import eu.koboo.yaml.config.YamlEmbedded;
import eu.koboo.yaml.config.YamlKey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Config {

    @YamlKey(BackpackPlugin.CONFIG_VERSION_KEY)
    @YamlComment("!! Do not touch this or it will break your config file !!")
    int configVersion = 1;

    @YamlEmbedded("update-check")
    Update update = new Update();

    @YamlEmbedded("crafting")
    Crafting crafting = new Crafting();

    @YamlEmbedded("appearance")
    Appearance appearance = new Appearance();

    @YamlEmbedded("restrictions")
    Restrictions restrictions = new Restrictions();

    @YamlEmbedded("handling")
    Handling handling = new Handling();
}
