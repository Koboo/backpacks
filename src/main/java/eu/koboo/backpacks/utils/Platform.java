package eu.koboo.backpacks.utils;

import lombok.Getter;

@Getter
public class Platform {

    private static boolean spigot;
    private static boolean paper;
    private static boolean folia;

    static {
        try {
            Class.forName("org.spigotmc.SpigotConfig");
            spigot = true;
        } catch (ClassNotFoundException ignored) {
            spigot = false;
        }

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            paper = true;
        } catch (ClassNotFoundException ignored) {
            paper = false;
        }

        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            folia = true;
        } catch (ClassNotFoundException ignored) {
            folia = false;
        }
    }

}
