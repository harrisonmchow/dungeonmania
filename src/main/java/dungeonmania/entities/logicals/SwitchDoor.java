package dungeonmania.entities.logicals;

//import dungeonmania.entities.Entity;
import dungeonmania.util.Position;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class SwitchDoor extends LogicalEntity {
    private boolean doorOpen = false;

    public SwitchDoor(Position position, String logic) {
        super(position, logic);
    }

    @Override
    public void updateStatus(GameMap map) {
        if (this.isActivated(map)) {
            this.doorOpen = true;
        } else {
            this.doorOpen = false;
        }
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return doorOpen;
    }

}
