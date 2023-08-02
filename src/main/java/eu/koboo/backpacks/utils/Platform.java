package eu.koboo.backpacks.utils;

import lombok.Getter;

public class Platform {

    @Getter
    private static boolean spigot;
    @Getter
    private static boolean paper;
    @Getter
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
