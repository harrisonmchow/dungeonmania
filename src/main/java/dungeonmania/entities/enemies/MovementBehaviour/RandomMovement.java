package dungeonmania.entities.enemies.MovementBehaviour;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import dungeonmania.entities.enemies.Enemy;
import dungeonmania.Game;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class RandomMovement implements Movement {
    private Enemy enemy;
    private Random randGen = new Random();

    public RandomMovement(Enemy e) {
        enemy = e;
    }

    @Override
    public void move(Game game, GameMap map) {
        Position nextPos;
        List<Position> pos = enemy.getAdjacentPositionsToCurrPos();
        pos = pos
                .stream()
                .filter(p -> map.canMoveTo(enemy, p)).collect(Collectors.toList());
        if (pos.size() == 0) {
            nextPos = enemy.getPosition();
            map.moveTo(enemy, nextPos);
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
            map.moveTo(enemy, nextPos);
        }
    }
}
