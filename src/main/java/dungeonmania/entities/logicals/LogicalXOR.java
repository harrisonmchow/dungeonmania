package dungeonmania.entities.logicals;

import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LogicalXOR implements LogicalStrategy {
    private LogicalEntity e;

    public LogicalXOR(LogicalEntity e) {
        this.e = e;
    }

    @Override
    public boolean isActivated(GameMap map) {
        // All logical Entities
        List<LogicalEntity> logicalEntities = map.getEntities(LogicalEntity.class);
        int count = (int) logicalEntities.stream()
                .filter(l -> l != e && Position.isAdjacent(e.getPosition(), l.getPosition()) && l.isActivated(map)
                        && l instanceof Conductor)
                .count();

        return count == 1;
    }

    @Override
    public String toString() {
        return "xor";
    }
}
