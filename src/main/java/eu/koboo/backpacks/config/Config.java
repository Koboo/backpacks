package eu.koboo.backpacks.config;

import eu.koboo.backpacks.config.appearance.Appearance;
import eu.koboo.yaml.config.YamlCommentHead;
import eu.koboo.yaml.config.YamlEmbedded;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class Config {

    @YamlEmbedded("restrictions")
    @YamlCommentHead({
            "Restrict the usage of the backpacks",
            "by limiting amount or backpacks in backpacks."
    })
    Restrictions restrictions = new Restrictions();

    @YamlEmbedded("appearance")
    @YamlCommentHead({
            "Change the options related to",
            "the appearance of the backpacks."
    })
    Appearance appearance = new Appearance();

    @YamlEmbedded("crafting")
    @YamlCommentHead({
            "Change the crafting recipe of the backpack.",
    })
    Crafting crafting = new Crafting();

    @YamlEmbedded("messages")
    @YamlCommentHead({
            "Change the messages of the plugin."
    })
    Messages messages = new Messages();

    @YamlEmbedded("permissions")
    @YamlCommentHead({
            "Change the permissions of the plugin."
    })
    Permissions permissions = new Permissions();

}
