package dungeonmania.entities.logicals;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LogicalCOAND implements LogicalStrategy {
    private LogicalEntity e;

    public LogicalCOAND(LogicalEntity e) {
        this.e = e;
    }

    @Override
    public boolean isActivated(GameMap map) {
        // All logical Entities
        List<LogicalEntity> logicalEntities = map.getEntities(LogicalEntity.class);
        List<LogicalEntity> visited = new ArrayList<>();
        List<LogicalEntity> on = new ArrayList<>();

        // Check if they are adjacent and are activated
        for (LogicalEntity l : logicalEntities) {
            if (l != e && Position.isAdjacent(e.getPosition(), l.getPosition()) && l instanceof Conductor
                    && !visited.contains(e)) {
                visited.add(e);
                if (l.isActivated(map)) {
                    on.add(e);
                }
            }
        }
        if (on.size() == 0 || on.size() != visited.size()) {
            return false;
        }
        int onTick = on.get(0).getActivatedTick();
        return on.stream().allMatch(e -> e.getActivatedTick() == onTick);
    }

    @Override
    public String toString() {
        return "co_and";
    }
}
