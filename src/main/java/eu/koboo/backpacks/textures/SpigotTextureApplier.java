package eu.koboo.backpacks.textures;

import eu.koboo.backpacks.utils.BackpackColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class SpigotTextureApplier implements TextureApplier {

    @Override
    public void applyTexture(BackpackColor color, SkullMeta skullMeta) {
        PlayerProfile playerProfile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = playerProfile.getTextures();
        try {
            textures.setSkin(new URL(color.getUrl()));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        playerProfile.setTextures(textures);
        skullMeta.setOwnerProfile(playerProfile);
    }
}
