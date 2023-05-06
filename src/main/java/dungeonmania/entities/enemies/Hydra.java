package dungeonmania.entities.enemies;

import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.enemies.MovementBehaviour.RandomMovement;
import dungeonmania.util.Position;

public class Hydra extends Enemy {
    public static final double DEFAULT_HEALTH = 10.0;
    public static final double DEFAULT_ATTACK = 10.0;
    public static final double DEFAULT_HEAL_RATE = 0;
    public static final double DEFAULT_HEAL_AMOUNT = 1;

    public Hydra(Position position, double health, double attack, double healRate, double healAmount) {
        super(position, health, attack);
        super.changeMovement(new RandomMovement(this));
        super.changeBattleStatistics(new BattleStatistics(
                health,
                attack,
                0,
                BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER,
                healRate,
                healAmount));
    }
}
