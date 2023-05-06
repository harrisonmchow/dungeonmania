package dungeonmania.entities.enemies.MovementBehaviour;

//import java.util.List;
//import java.util.Random;
//import java.util.stream.Collectors;

import dungeonmania.entities.enemies.Enemy;
import dungeonmania.Game;
import dungeonmania.map.GameMap;
//import dungeonmania.util.Position;

public class SwampTileMovement implements Movement {
    private Enemy enemy;
    private int stuckFor;
    private Movement prevMovement;

    public SwampTileMovement(Enemy e, int stuckFor, Movement prevMovement) {
        this.enemy = e;
        this.stuckFor = stuckFor;
        this.prevMovement = prevMovement;
    }

    public void move(Game game, GameMap map) {
        if (stuckFor > 1) {
            stuckFor--;
        } else {
            enemy.changeMovement(prevMovement);
        }
    }

}
