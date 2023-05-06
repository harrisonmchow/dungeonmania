package dungeonmania.battles;

import java.io.Serializable;

public class BattleRound implements Serializable {
    private double deltaSelfHealth;
    private double deltaTargetHealth;

    public BattleRound(double deltaSelfHealth, double deltaTargetHealth) {
        this.deltaSelfHealth = deltaSelfHealth;
        this.deltaTargetHealth = deltaTargetHealth;
    }

    public double getDeltaSelfHealth() {
        return deltaSelfHealth;
    }

    public double getDeltaTargetHealth() {
        return deltaTargetHealth;
    }
}
