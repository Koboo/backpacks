package eu.koboo.backpacks.config;

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
    Restrictions restrictions = new Restrictions();

    @YamlEmbedded("appearance")
    Appearance appearance = new Appearance();

    @YamlKey("discover-all-recipes")
    boolean discoverAllRecipes = true;

}
