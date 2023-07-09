package eu.koboo.backpacks.utils;

import eu.koboo.backpacks.BackpackPlugin;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.Locale;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
@RequiredArgsConstructor
public enum BackpackColor {

    LIGHT_BLUE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2U1YWJkYjczNzQ1NTNkMDU2NWNiY2IzMjk1YWVkNTE1YTg5N2ViY2U5ZTBiYzYwZjFjMWY4YWU1NGM3NDlkZiJ9fX0=",
            Material.LIGHT_BLUE_DYE
    ),
    LIGHT_GRAY(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzNjMGJmYTg3NWFiOGI4M2Q4ZDk1MTk3NzRjNmM3YzQ1YWQ5YTg4NDNmNjhhNGE1YzAwMDM3NDMyNjBlMmVjNSJ9fX0=",
            Material.LIGHT_GRAY_DYE
    ),
    MAGENTA(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTI4MDAzNDlkMTI2YjlmZmNhN2EyYmU2NTlkNzk2YWMwZTE1ZGYwODhlZmEyNjY4ZDZkM2RkNjM4ZTBmOTc1NCJ9fX0=",
            Material.MAGENTA_DYE
    ),
    GRAY(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmFjNzdiNzZmMGM2NGMxNGMwNDkzNmE1NTc5NmM0OWE4MmZmMTc0ODM4ZGI5MzBkNTJiMGNhYWNkZDIxNTkxNyJ9fX0=",
            Material.GRAY_DYE
    ),
    YELLOW(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTFiYmJjNWMyNDM4NGVjYjJmNjg0NGRhMjg1Y2NjZjllYjAxMWM3YTY2NzAxNzdjZjc1Y2Q2NTUxM2JjMTI3NCJ9fX0=",
            Material.YELLOW_DYE
    ),
    WHITE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTJiYjM4NTE2YjI5NTA0MTg2ZTExNTU5Y2Q1MjUwYWUyMThkYjRkZGQyN2FlNDM4NzI2Yzg0N2NlNmIzYzk4In19fQ==",
            Material.WHITE_DYE
    ),
    RED(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmNlMDE2OTlmNzk2Y2JkZTk1Yzg0OWJlZTNjYzM2OTg3ODQ1MmI0MGE0MTE0NDM2NmQ2NmI0YTA4MjZjZmFhMCJ9fX0=",
            Material.RED_DYE
    ),
    PURPLE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4N2M3MzJhYTM5ZDI5MTM0NjUwYjZhNzJjNmY0MWI5OTA4NWEyNmVjNWU1MTNiYTE4YzQwZDVlY2E5ZTY5YyJ9fX0=",
            Material.PURPLE_DYE
    ),
    PINK(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmUwODdjZDk3NWQyNjRjNzJhZGNhOWVmYzZmYzM0NDc2ZWMzOGUxOGRmNTM2YjMwNzYzNTI2N2EzN2ZhZjA3NiJ9fX0=",
            Material.PINK_DYE
    ),
    ORANGE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcwYjU2MjJiN2QwNjhmNTc4OGJmMTlhODM5ODM5MzdiMTZjNTk3MmM5MWY1ZWU3YmY1NGJjYzM2MzhmOWEzNiJ9fX0=",
            Material.ORANGE_DYE
    ),
    LIME(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIxMjhmNDhkOTk3MTg2NTYzZmJjNWI0N2E4OGMwZDBhYWM5MmZhMmMyODVjZDFmYWU0MjBjMzRmYThmMjAxMCJ9fX0=",
            Material.LIME_DYE
    ),
    BLACK(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTgyNjQxNzg2YTQyMjA4OGY3NWRjZWU3MDIwNWQ1ODA2MDBmNjlkNmFhMmY3N2QyNjc4YjU4ZDg5YjY5NzNhNiJ9fX0=",
            Material.BLACK_DYE
    ),
    CYAN(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODJjMzdiZmExMTg2M2QwMDI1NTFhMDJiNTE5ZDcxNTRkYTgyNjcwNWQyMDk4MjU5MTUyMmRlMmVhMWIyODhjMiJ9fX0=",
            Material.CYAN_DYE
    ),
    GREEN(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2E2YWQ4YWQ5MTNkZWYxM2JkNTc0MTY1NWU3N2QxMzRlYjFiNTdmMDI5NzBkYWE2YjMzMDgyNzU0ZDFhZmZjNCJ9fX0=",
            Material.GREEN_DYE
    ),
    BLUE(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGRjYzZlYjQwZjNiYWRhNDFlNDMzOTg4OGQ2ZDIwNzQzNzU5OGJkYmQxNzVjMmU3MzExOTFkNWE5YTQyZDNjOCJ9fX0=",
            Material.BLUE_DYE
    ),
    BROWN(
            "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjMwOGJmNWNjM2U5ZGVjYWYwNzcwYzNmZGFkMWUwNDIxMjFjZjM5Y2MyNTA1YmJiODY2ZTE4YzZkMjNjY2QwYyJ9fX0=",
            Material.BROWN_DYE
    );

    String value;
    Material material;
    NamespacedKey key;

    BackpackColor(String value, Material material) {
        this.value = value;
        this.material = material;
        this.key = NamespacedKey.fromString("backpack_recipe_" + name().toLowerCase(Locale.ROOT), BackpackPlugin.getPlugin());
    }
}
