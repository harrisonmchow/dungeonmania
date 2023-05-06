package dungeonmania.entities.enemies.MovementBehaviour;

import java.io.Serializable;

import dungeonmania.Game;
import dungeonmania.map.GameMap;

public interface Movement extends Serializable {
    public void move(Game game, GameMap map);
}
