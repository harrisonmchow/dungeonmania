package dungeonmania.entities.logicals;

import dungeonmania.map.GameMap;

public interface LogicalStrategy {
    public boolean isActivated(GameMap map);

    @Override
    String toString();
}
