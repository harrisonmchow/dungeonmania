package dungeonmania.entities.logicals;

import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class LightBulbOff extends LogicalEntity {

    public LightBulbOff(Position position, String logic) {
        super(position, logic);
    }

    @Override
    public void updateStatus(GameMap map) {
        if (this.isActivated(map)) {
            map.addEntity(new LightBulbOn(this.getPosition(), logicType()));
            map.removeNode(this);
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return false;
    }
}
