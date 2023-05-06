package dungeonmania.entities;

import dungeonmania.util.Position;
//import dungeonmania.Game;

//import java.util.List;
//import java.util.stream.Collectors;

import dungeonmania.entities.enemies.Enemy;
//import dungeonmania.entities.enemies.Mercenary;
//import dungeonmania.entities.enemies.ZombieToast;
import dungeonmania.entities.enemies.MovementBehaviour.SwampTileMovement;
import dungeonmania.map.GameMap;
//import dungeonmania.util.Position;
import dungeonmania.entities.enemies.MovementBehaviour.*;;

public class SwampTile extends Entity {
    private int movementFactor;

    public SwampTile(Position position, int movementFactor) {
        super(position);
        this.movementFactor = movementFactor;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Enemy) {
            Enemy e = (Enemy) entity;
            if (!(e.isAllied() && Position.isAdjacent(e.getPosition(), map.getPlayerCurrPos()))) {
                Movement prevMov = e.getMovement();
                e.changeMovement(new SwampTileMovement(e, movementFactor, prevMov));
            }
        }
    }

}
