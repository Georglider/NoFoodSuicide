package georglider.nofoodsuicide;

import georglider.nofoodsuicide.data.ConfigManager;
import georglider.nofoodsuicide.data.LeftPlayers;
import georglider.nofoodsuicide.data.PlayerRecoveryData;
import georglider.nofoodsuicide.exceptions.NoDeathCausesFound;
import georglider.nofoodsuicide.listeners.DeathListener;
import georglider.nofoodsuicide.listeners.JoinListener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class PluginInitializer extends JavaPlugin {

    public ConfigManager config;
    public LeftPlayers leftPlayersConfig;
    public final ConcurrentHashMap<String, PlayerRecoveryData> deadLeftPlayers = new ConcurrentHashMap<>();
    List<EntityDamageEvent.DamageCause> recoverReasons;
    @Override
    public void onEnable() {
        this.config = new ConfigManager(this);
        this.leftPlayersConfig = new LeftPlayers(this);

        this.recoverReasons = config.recoverReasons();

        getServer().getPluginManager().registerEvents(new DeathListener(deadLeftPlayers, recoverReasons), this);
        if (config.getConfig().getBoolean("save_left_players_on_stop")) {
            try {
                getServer().getPluginManager().registerEvents(new JoinListener(leftPlayersConfig, deadLeftPlayers, recoverReasons), this);
            } catch (NoDeathCausesFound e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onDisable() {
        if (config.getConfig().getBoolean("save_left_players_on_stop")) {
            List<EntityDamageEvent.DamageCause> recoverReasons = config.recoverReasons();
            int maxFoodLevel = config.getConfig().getInt("max_food_level");

            deadLeftPlayers.forEach(((s, playerRecoveryData) -> {
                if (recoverReasons.contains(playerRecoveryData.getCause())) {
                    leftPlayersConfig.getConfig().set(s, maxFoodLevel);
                } else {
                    leftPlayersConfig.getConfig().set(s, playerRecoveryData.getFoodLevel());
                }
            }));

            leftPlayersConfig.saveConfig();
        }
        // Plugin shutdown logic
    }
}
