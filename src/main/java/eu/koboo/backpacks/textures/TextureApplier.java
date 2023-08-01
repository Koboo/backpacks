package eu.koboo.backpacks.textures;

import eu.koboo.backpacks.utils.BackpackColor;
import eu.koboo.backpacks.utils.Platform;
import org.bukkit.inventory.meta.SkullMeta;

public interface TextureApplier {

    void applyTexture(BackpackColor color, SkullMeta skullMeta);

    static TextureApplier createApplier() {
        if (Platform.isPaper()) {
            return new PaperTextureApplier();
        }
        return new SpigotTextureApplier();
    }
}
