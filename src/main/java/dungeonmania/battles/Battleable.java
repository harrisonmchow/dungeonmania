package dungeonmania.battles;

import java.io.Serializable;

/**
 * Entities implement this interface can do battles
 */
public interface Battleable extends Serializable {
    public BattleStatistics getBattleStatistics();
}
