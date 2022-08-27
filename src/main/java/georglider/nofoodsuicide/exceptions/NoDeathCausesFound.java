package georglider.nofoodsuicide.exceptions;

public class NoDeathCausesFound extends Exception {
    public NoDeathCausesFound() {
        super("Can't find reason to set food level to it's previous value because all death causes are recovery one's\n" +
                "Please set save_left_players_on_stop to false, or change [recover_cause, not_recover_cause] list");
    }
}
