package eu.koboo.backpacks.config;

import eu.koboo.backpacks.utils.BackpackSize;
import eu.koboo.yaml.config.YamlCommentHead;
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
    Message message = new Message();

    @YamlEmbedded("permissions")
    @YamlCommentHead({
            "Change the permissions of the plugin."
    })
    Permission permission = new Permission();

}
