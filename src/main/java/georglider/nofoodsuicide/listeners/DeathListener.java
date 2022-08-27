package georglider.nofoodsuicide.listeners;

import georglider.nofoodsuicide.PluginInitializer;
import georglider.nofoodsuicide.data.ConfigManager;
import georglider.nofoodsuicide.data.PlayerRecoveryData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DeathListener implements Listener {

    ConfigManager config = PluginInitializer.getPlugin(PluginInitializer.class).config;
    ConcurrentHashMap<String, PlayerRecoveryData> deadLeftPlayers;
    int minFoodLevel = config.getConfig().getInt("min_food_level");
    List<EntityDamageEvent.DamageCause> recoverReasons;
    Boolean online = Bukkit.getOnlineMode();

    public DeathListener(ConcurrentHashMap<String, PlayerRecoveryData> deadLeftPlayers, List<EntityDamageEvent.DamageCause> recoverReasons) {
        this.deadLeftPlayers = deadLeftPlayers;
        this.recoverReasons = recoverReasons;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player entity = e.getEntity();
        if (!entity.hasPermission("nofoodsuicide.bypass")) {
            if (online) {
                deadLeftPlayers.put(entity.getUniqueId().toString(), new PlayerRecoveryData(entity));
            } else {
                deadLeftPlayers.put(entity.getName(), new PlayerRecoveryData(entity));
            }
        }
    }

    @EventHandler
    public void PlayerRespawnEvent(PlayerRespawnEvent e) {
        if (!e.getPlayer().hasPermission("nofoodsuicide.bypass")) {
            String db = online ? e.getPlayer().getUniqueId().toString() : e.getPlayer().getName();

            PlayerRecoveryData data = Optional.ofNullable(e.getPlayer().getLastDamageCause())
                    .map(a -> new PlayerRecoveryData(e.getPlayer().getFoodLevel(), a.getCause()))
                    .orElse(deadLeftPlayers.getOrDefault(db, new PlayerRecoveryData(0, EntityDamageEvent.DamageCause.CUSTOM)));
            deadLeftPlayers.remove(db);

            if (!recoverReasons.contains(data.getCause())) {
                Bukkit.getScheduler().runTaskLater(PluginInitializer.getPlugin(PluginInitializer.class),
                        () -> e.getPlayer().setFoodLevel(Math.max(data.getFoodLevel(), minFoodLevel)),
                        1L);
            }
        }
    }


}
