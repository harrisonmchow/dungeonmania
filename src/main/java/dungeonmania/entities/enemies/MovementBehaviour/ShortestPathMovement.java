package dungeonmania.entities.enemies.MovementBehaviour;

import dungeonmania.Game;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ShortestPathMovement implements Movement {
    private Enemy enemy;

    public ShortestPathMovement(Enemy enemy) {
        this.enemy = enemy;
    }

    @Override
    public void move(Game game, GameMap map) {
        Position nextPos = map.dijkstraPathFind(enemy.getPosition(), map.getPlayerCurrPos(), enemy);
        map.moveTo(enemy, nextPos);
        enemy.isAdjacentToPlayer(map.getPlayer());
    }
}
