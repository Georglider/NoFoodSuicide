package georglider.nofoodsuicide.data;

import georglider.nofoodsuicide.PluginInitializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigManager {

    private final PluginInitializer plugin;
    private FileConfiguration dataConfig = null;
    private File configFile = null;

    public ConfigManager(PluginInitializer plugin) {
        this.plugin = plugin;
        // Init config
        saveDefaultConfig();
    }

    public void reloadConfig() {
        if (this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), "config.yml");

        this.dataConfig = YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defaultStream = this.plugin.getResource("config.yml");

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

    public List<EntityDamageEvent.DamageCause> recoverReasons() {
        FileConfiguration config = getConfig();

        String[] recover_causes = config.getStringList("recover_cause").toArray(new String[0]);
        if (recover_causes.length != 0) {
            return Arrays.stream(recover_causes)
                    .map(EntityDamageEvent.DamageCause::valueOf)
                    .collect(Collectors.toList());
        }
        else {
            String[] not_recover_causes = config.getStringList("not_recover_cause").toArray(new String[0]);
            if (not_recover_causes.length != 0) {
                ArrayList<EntityDamageEvent.DamageCause> damageCauses = new ArrayList<>(Arrays.asList(EntityDamageEvent.DamageCause.values()));

                Arrays.stream(not_recover_causes)
                        .forEach(cause -> damageCauses.remove(EntityDamageEvent.DamageCause.valueOf(cause)));

                return damageCauses;
            }
            else {
                return Collections.emptyList();
            }
        }
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
            this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
        }

        if (!this.configFile.exists()) {
            this.plugin.saveResource("config.yml", false);
        }

        if (this.configFile.length() == 0) {
            this.getConfig().set("min_food_level", 6);
            this.getConfig().set("max_food_level", 20);
            this.getConfig().set("save_left_players_on_stop", false);
            this.getConfig().set("recover_cause", new String[]{"STARVATION", "VOID"});

            this.saveConfig();
        }
    }

}