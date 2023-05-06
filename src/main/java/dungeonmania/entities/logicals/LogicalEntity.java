package dungeonmania.entities.logicals;

import dungeonmania.map.GameMap;

import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public class LogicalEntity extends Entity {
    private int activatedTick;

    private LogicalStrategy logic;

    public LogicalEntity(Position position, String logic) {
        super(position);
        this.logic = LogicalFactory.constructLogicalStrategy(logic, this);
    }

    public boolean isActivated(GameMap map) {
        return logic.isActivated(map);
    }

    public int getActivatedTick() {
        return activatedTick;
    }

    public void updateStatus(GameMap map) {
        return;
    }

    public String logicType() {
        return logic.toString();
    }
}
