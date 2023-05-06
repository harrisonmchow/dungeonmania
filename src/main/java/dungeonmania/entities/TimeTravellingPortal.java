package dungeonmania.entities;

import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class TimeTravellingPortal extends Entity {
    public TimeTravellingPortal(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (entity instanceof Player)
            return true;
        return false;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player)
            map.rewind(30);
    }
}
