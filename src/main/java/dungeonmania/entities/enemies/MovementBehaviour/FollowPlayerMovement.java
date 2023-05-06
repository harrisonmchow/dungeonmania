package dungeonmania.entities.enemies.MovementBehaviour;

import dungeonmania.Game;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class FollowPlayerMovement implements Movement {
    private Enemy enemy;
    private Player player;

    public FollowPlayerMovement(Enemy enemy, Player player) {
        this.enemy = enemy;
        this.player = player;
    }

    @Override
    public void move(Game game, GameMap map) {
        Position nextPos = player.getPreviousDistinctPosition();
        if (!nextPos.equals(player.getPosition())) {
            map.moveTo(enemy, nextPos);
        }
    }
}
