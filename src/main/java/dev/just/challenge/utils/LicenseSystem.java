package dev.just.challenge.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class LicenseSystem {
    private static YamlConfiguration configuration;
    private static File file;
    public static void registerConfiguration() {
        File dir = new File("./plugins/JUtils");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file = new File(dir, "license.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        configuration = YamlConfiguration.loadConfiguration(file);
    }
    public static boolean getLicense(Plugin plugin) {
        registerConfiguration();
        if (!configuration.contains("LICENSE-KEY")) {
            configuration.set("LICENSE-KEY", "HIER-KEY-EINTRAGEN");
            try {
                configuration.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            log(ChatColor.RED + "Lizenz konnte nicht verifiziert werden.");
            log(ChatColor.RED + "Fehler aufgetreten, da die Datei gerade erst erstellt wurde. ");
            log(ChatColor.RED + "Dektiviere Plugin" + ChatColor.GRAY + "...");
            log(ChatColor.GRAY + "✗ " + ChatColor.BLUE + "Lizenzsystem" + ChatColor.GRAY + " ✗");
            return false;
        }
        if (configuration.get("LICENSE-KEY") == null || configuration.get("LICENSE-KEY").equals("HIER-KEY-EINTRAGEN")) {
            log(ChatColor.RED + "Lizenz konnte nicht verifiziert werden.");
            log(ChatColor.RED + "Fehler aufgetreten, da die Datei nicht verändert wurde. ");
            log(ChatColor.RED + "Dektiviere Plugin" + ChatColor.GRAY + "...");
            log(ChatColor.GRAY + "✗ " + ChatColor.BLUE + "Lizenzsystem" + ChatColor.GRAY + " ✗");
            return false;
        }
        return new AdvancedLicense((String) configuration.get("LICENSE-KEY"), "https://predestinarian-rati.000webhostapp.com/verify.php", plugin).register();
    }
    private static void log(String string) {
        System.out.println(string);
    }
}
