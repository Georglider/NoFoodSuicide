package georglider.nofoodsuicide.listeners;

import georglider.nofoodsuicide.data.LeftPlayers;
import georglider.nofoodsuicide.data.PlayerRecoveryData;
import georglider.nofoodsuicide.exceptions.NoDeathCausesFound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class JoinListener implements Listener {

    final ConcurrentHashMap<String, PlayerRecoveryData> deadLeftPlayers;
    final EntityDamageEvent.DamageCause fakeCause;
    LeftPlayers leftPlayersConfig;
    Boolean online = Bukkit.getOnlineMode();
    public JoinListener(LeftPlayers leftPlayersConfig, ConcurrentHashMap<String, PlayerRecoveryData> deadLeftPlayers, List<EntityDamageEvent.DamageCause> recoverReasons) throws NoDeathCausesFound {
        this.leftPlayersConfig = leftPlayersConfig;
        this.deadLeftPlayers = deadLeftPlayers;
        this.fakeCause = Arrays.stream(EntityDamageEvent.DamageCause.values())
                .filter(cause -> !recoverReasons.contains(cause))
                .findFirst().orElseThrow(NoDeathCausesFound::new);
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("nofoodsuicide.bypass")) {
            if (online) {
                int level = leftPlayersConfig.getConfig().getInt(player.getUniqueId().toString());
                if (level != 0) {
                    deadLeftPlayers.put(player.getUniqueId().toString(), new PlayerRecoveryData(level, this.fakeCause));
                    leftPlayersConfig.getConfig().set(player.getUniqueId().toString(), null);
                    leftPlayersConfig.saveConfig();
                }
            } else {
                int level = leftPlayersConfig.getConfig().getInt(player.getName());
                if (level != 0) {
                    deadLeftPlayers.put(player.getName(), new PlayerRecoveryData(level, this.fakeCause));
                    leftPlayersConfig.getConfig().set(player.getName(), null);
                    leftPlayersConfig.saveConfig();
                }
            }
        }
    }

}
