package dungeonmania.entities.logicals;

import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LogicalOR implements LogicalStrategy {
    private LogicalEntity e;

    public LogicalOR(LogicalEntity e) {
        this.e = e;
    }

    @Override
    public boolean isActivated(GameMap map) {
        // All logical Entities
        List<LogicalEntity> logicalEntities = map.getEntities(LogicalEntity.class);
        // Check if they are adjacent and are activated
        return logicalEntities.stream().anyMatch(
                l -> l != e && Position.isAdjacent(e.getPosition(), l.getPosition()) && l.isActivated(map)
                        && l instanceof Conductor);
    }

    @Override
    public String toString() {
        return "or";
    }
}
