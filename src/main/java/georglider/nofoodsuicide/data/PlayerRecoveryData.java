package georglider.nofoodsuicide.data;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerRecoveryData {

    private final int foodLevel;
    private final EntityDamageEvent.DamageCause cause;

    public PlayerRecoveryData(int foodLevel, EntityDamageEvent.DamageCause cause) {
        this.foodLevel = foodLevel;
        this.cause = cause;
    }

    public PlayerRecoveryData(Player p) {
        this.foodLevel = p.getFoodLevel();
        this.cause = p.getLastDamageCause().getCause();
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public EntityDamageEvent.DamageCause getCause() {
        return cause;
    }
}
