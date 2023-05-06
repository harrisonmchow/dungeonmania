package dungeonmania.entities.enemies;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.MovementBehaviour.SpiderMovement;
import dungeonmania.util.Position;

public class Spider extends Enemy {

    public static final int DEFAULT_SPAWN_RATE = 0;
    public static final double DEFAULT_ATTACK = 5;
    public static final double DEFAULT_HEALTH = 10;

    public Spider(Position position, double health, double attack) {
        super(position.asLayer(Entity.DOOR_LAYER + 1), health, attack);
        super.changeMovement(new SpiderMovement(this, position));
    };
}
