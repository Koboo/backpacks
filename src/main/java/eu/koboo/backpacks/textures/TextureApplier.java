package eu.koboo.backpacks.textures;

import eu.koboo.backpacks.utils.BackpackColor;
import io.papermc.lib.PaperLib;
import org.bukkit.inventory.meta.SkullMeta;

public interface TextureApplier {

    void applyTexture(BackpackColor color, SkullMeta skullMeta);

    static TextureApplier createApplier() {
        if (PaperLib.isPaper()) {
            return new PaperTextureApplier();
        }
        return new SpigotTextureApplier();
    }
}
