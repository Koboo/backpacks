package eu.koboo.backpacks.textures;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import eu.koboo.backpacks.utils.BackpackColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.UUID;

public class PaperTextureApplier implements TextureApplier {

    @Override
    public void applyTexture(BackpackColor color, SkullMeta skullMeta) {

        PlayerProfile playerProfile = Bukkit.createProfile(UUID.randomUUID());
        playerProfile.getProperties().add(new ProfileProperty("textures", color.getBase64()));

        skullMeta.setPlayerProfile(playerProfile);
    }
}
