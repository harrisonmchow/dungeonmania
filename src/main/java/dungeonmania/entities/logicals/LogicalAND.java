package dungeonmania.entities.logicals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LogicalAND implements LogicalStrategy {
    private LogicalEntity e;

    public LogicalAND(LogicalEntity e) {
        this.e = e;
    }

    @Override
    public boolean isActivated(GameMap map) {
        int count = 0;
        int total = 0;
        // All logical Entities
        List<LogicalEntity> logicalEntities = map.getEntities(LogicalEntity.class);
        List<LogicalEntity> visited = new ArrayList<>();

        // Check if they are adjacent and are activated
        for (LogicalEntity l : logicalEntities) {
            if (l != e && Position.isAdjacent(e.getPosition(), l.getPosition()) && l instanceof Conductor
                    && !visited.contains(e)) {
                total++;
                visited.add(e);
                if (l.isActivated(map)) {
                    count++;
                }
            }
        }
        return count == total && count >= 2;
    }

    @Override
    public String toString() {
        return "and";
    }
}
