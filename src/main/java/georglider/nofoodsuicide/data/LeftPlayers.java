package georglider.nofoodsuicide.data;

import georglider.nofoodsuicide.PluginInitializer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class LeftPlayers {

    private final PluginInitializer plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public LeftPlayers(PluginInitializer plugin) {
        this.plugin = plugin;
        // Init config
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "players.yml");

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("players.yml");

        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.dataConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig() {
        if (this.dataConfig == null) {
            reloadConfig();
        }
        return this.dataConfig;
    }

    public void saveConfig() {
        if (this.dataConfig == null || this.configFile == null)
            return;

        try {
            this.getConfig().save(this.configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDefaultConfig() {
        if (this.configFile == null) {
            this.configFile = new File(this.plugin.getDataFolder(), "players.yml");
        }

        if (!this.configFile.exists()) {
            this.plugin.saveResource("players.yml", false);
        }

        if (this.configFile.length() == 0) {
            if (Bukkit.getOnlineMode()) {
                this.getConfig().set("069a79f4-44e9-4726-a5be-fca90e38aaf5", 20);
            } else {
                this.getConfig().set("Notch", 20);
            }
            this.saveConfig();
        }
    }

}
