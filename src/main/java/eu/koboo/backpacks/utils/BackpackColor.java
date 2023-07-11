package eu.koboo.backpacks.utils;

import eu.koboo.backpacks.BackpackPlugin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@RequiredArgsConstructor
public enum BackpackColor {

    NONE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTE2NWVlMTNhNjA2ZTFiNDQ2OTVhZjQ2YzM5YjUyY2U2NjY1N2E0YzRhNjIzZDBiMjgyYTdiOGNlMDUwOTQwNCJ9fX0=",
            "https://textures.minecraft.net/texture/9165ee13a606e1b44695af46c39b52ce66657a4c4a623d0b282a7b8ce0509404",
            null
    ),
    // Light blue
    LIGHT_BLUE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U1YWJkYjczNzQ1NTNkMDU2NWNiY2IzMjk1YWVkNTE1YTg5N2ViY2U5ZTBiYzYwZjFjMWY4YWU1NGM3NDlkZiJ9fX0=",
            "https://textures.minecraft.net/texture/82c37bfa11863d002551a02b519d7154da826705d20982591522de2ea1b288c2",
            Material.LIGHT_BLUE_DYE
    ),
    LIGHT_GRAY(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzNjMGJmYTg3NWFiOGI4M2Q4ZDk1MTk3NzRjNmM3YzQ1YWQ5YTg4NDNmNjhhNGE1YzAwMDM3NDMyNjBlMmVjNSJ9fX0=",
            "https://textures.minecraft.net/texture/33c0bfa875ab8b83d8d9519774c6c7c45ad9a8843f68a4a5c0003743260e2ec5",
            Material.LIGHT_GRAY_DYE
    ),
    // Magenta
    MAGENTA(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI4MDAzNDlkMTI2YjlmZmNhN2EyYmU2NTlkNzk2YWMwZTE1ZGYwODhlZmEyNjY4ZDZkM2RkNjM4ZTBmOTc1NCJ9fX0=",
            "https://textures.minecraft.net/texture/92800349d126b9ffca7a2be659d796ac0e15df088efa2668d6d3dd638e0f9754",
            Material.MAGENTA_DYE
    ),
    GRAY(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFjNzdiNzZmMGM2NGMxNGMwNDkzNmE1NTc5NmM0OWE4MmZmMTc0ODM4ZGI5MzBkNTJiMGNhYWNkZDIxNTkxNyJ9fX0=",
            "https://textures.minecraft.net/texture/bac77b76f0c64c14c04936a55796c49a82ff174838db930d52b0caacdd215917",
            Material.GRAY_DYE
    ),
    YELLOW(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFiYmJjNWMyNDM4NGVjYjJmNjg0NGRhMjg1Y2NjZjllYjAxMWM3YTY2NzAxNzdjZjc1Y2Q2NTUxM2JjMTI3NCJ9fX0=",
            "https://textures.minecraft.net/texture/51bbbc5c24384ecb2f6844da285cccf9eb011c7a6670177cf75cd65513bc1274",
            Material.YELLOW_DYE
    ),
    WHITE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJiYjM4NTE2YjI5NTA0MTg2ZTExNTU5Y2Q1MjUwYWUyMThkYjRkZGQyN2FlNDM4NzI2Yzg0N2NlNmIzYzk4In19fQ==",
            "https://textures.minecraft.net/texture/a2bb38516b29504186e11559cd5250ae218db4ddd27ae438726c847ce6b3c98",
            Material.WHITE_DYE
    ),
    RED(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmNlMDE2OTlmNzk2Y2JkZTk1Yzg0OWJlZTNjYzM2OTg3ODQ1MmI0MGE0MTE0NDM2NmQ2NmI0YTA4MjZjZmFhMCJ9fX0=",
            "https://textures.minecraft.net/texture/bce01699f796cbde95c849bee3cc369878452b40a41144366d66b4a0826cfaa0",
            Material.RED_DYE
    ),
    PURPLE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4N2M3MzJhYTM5ZDI5MTM0NjUwYjZhNzJjNmY0MWI5OTA4NWEyNmVjNWU1MTNiYTE4YzQwZDVlY2E5ZTY5YyJ9fX0=",
            "https://textures.minecraft.net/texture/6187c732aa39d29134650b6a72c6f41b99085a26ec5e513ba18c40d5eca9e69c",
            Material.PURPLE_DYE
    ),
    PINK(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUwODdjZDk3NWQyNjRjNzJhZGNhOWVmYzZmYzM0NDc2ZWMzOGUxOGRmNTM2YjMwNzYzNTI2N2EzN2ZhZjA3NiJ9fX0=",
            "https://textures.minecraft.net/texture/2e087cd975d264c72adca9efc6fc34476ec38e18df536b307635267a37faf076",
            Material.PINK_DYE
    ),
    ORANGE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcwYjU2MjJiN2QwNjhmNTc4OGJmMTlhODM5ODM5MzdiMTZjNTk3MmM5MWY1ZWU3YmY1NGJjYzM2MzhmOWEzNiJ9fX0=",
            "https://textures.minecraft.net/texture/270b5622b7d068f5788bf19a83983937b16c5972c91f5ee7bf54bcc3638f9a36",
            Material.ORANGE_DYE
    ),
    LIME(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIxMjhmNDhkOTk3MTg2NTYzZmJjNWI0N2E4OGMwZDBhYWM5MmZhMmMyODVjZDFmYWU0MjBjMzRmYThmMjAxMCJ9fX0=",
            "https://textures.minecraft.net/texture/b2128f48d997186563fbc5b47a88c0d0aac92fa2c285cd1fae420c34fa8f2010",
            Material.LIME_DYE
    ),
    BLACK(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTgyNjQxNzg2YTQyMjA4OGY3NWRjZWU3MDIwNWQ1ODA2MDBmNjlkNmFhMmY3N2QyNjc4YjU4ZDg5YjY5NzNhNiJ9fX0=",
            "https://textures.minecraft.net/texture/a82641786a422088f75dcee70205d580600f69d6aa2f77d2678b58d89b6973a6",
            Material.BLACK_DYE
    ),
    // "Light blue"
    CYAN(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODJjMzdiZmExMTg2M2QwMDI1NTFhMDJiNTE5ZDcxNTRkYTgyNjcwNWQyMDk4MjU5MTUyMmRlMmVhMWIyODhjMiJ9fX0=",
            "https://textures.minecraft.net/texture/82c37bfa11863d002551a02b519d7154da826705d20982591522de2ea1b288c2",
            Material.CYAN_DYE
    ),
    GREEN(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E2YWQ4YWQ5MTNkZWYxM2JkNTc0MTY1NWU3N2QxMzRlYjFiNTdmMDI5NzBkYWE2YjMzMDgyNzU0ZDFhZmZjNCJ9fX0=",
            "https://textures.minecraft.net/texture/ca6ad8ad913def13bd5741655e77d134eb1b57f02970daa6b33082754d1affc4",
            Material.GREEN_DYE
    ),
    // "Navy blue"
    BLUE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRjYzZlYjQwZjNiYWRhNDFlNDMzOTg4OGQ2ZDIwNzQzNzU5OGJkYmQxNzVjMmU3MzExOTFkNWE5YTQyZDNjOCJ9fX0=",
            "https://textures.minecraft.net/texture/8dcc6eb40f3bada41e4339888d6d207437598bdbd175c2e731191d5a9a42d3c8",
            Material.BLUE_DYE
    ),
    BROWN(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjMwOGJmNWNjM2U5ZGVjYWYwNzcwYzNmZGFkMWUwNDIxMjFjZjM5Y2MyNTA1YmJiODY2ZTE4YzZkMjNjY2QwYyJ9fX0=",
            "https://textures.minecraft.net/texture/2308bf5cc3e9decaf0770c3fdad1e042121cf39cc2505bbb866e18c6d23ccd0c",
            Material.BROWN_DYE
    );

    private static final Map<Material, BackpackColor> COLOR_BY_MATERIAL_MAP = new HashMap<>();

    static {
        for (BackpackColor color : values()) {
            COLOR_BY_MATERIAL_MAP.put(color.getMaterial(), color);
        }
    }

    public static BackpackColor getColorByMaterial(Material material) {
        return COLOR_BY_MATERIAL_MAP.get(material);
    }

    String base64;
    String url;
    Material material;
    NamespacedKey recipeKey;

    BackpackColor(String base64, String url, Material material) {
        this.base64 = base64;
        this.url = url;
        this.material = material;
        this.recipeKey = NamespacedKey.fromString(BackpackPlugin.RECIPE_KEY_PREFIX + "_" + name().toLowerCase(Locale.ROOT), BackpackPlugin.getPlugin());
    }
}
