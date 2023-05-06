package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.enemies.MovementBehaviour.FollowPlayerMovement;
import dungeonmania.entities.enemies.MovementBehaviour.ShortestPathMovement;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;
    private boolean allied = false;
    private boolean mindControlled = false;
    private int startTick;
    private int mindControlDuration;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        super.changeMovement(new ShortestPathMovement(this));
    }

    @Override
    public boolean isAllied() {
        return allied || mindControlled;
    }

    @Override
    public void setAllied() {
        allied = true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (isAllied())
            return;
        super.onOverlap(map, entity);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        if (isAllied())
            return entity instanceof Mercenary || entity instanceof Player;
        else
            return entity instanceof Player;
    }

    /**
     * check whether the current merc can be bribed
     *
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }
    }

    @Override
    public void interact(Player player, Game game) {
        if (player.hasSceptre()) {
            mindControlled = true;
            mindControlDuration = player.getMindControlDuration();
            startTick = game.getTick();
        } else {
            setAllied();
            bribe(player);
        }
        isAdjacentToPlayer(player);
    }

    @Override
    public boolean isInteractable(Player player) {
        return (!isAllied() && canBeBribed(player)) || player.hasSceptre();
    }

    @Override
    public void isAdjacentToPlayer(Player player) {
        if (isAllied() && Position.isAdjacent(player.getPosition(), getPosition())) {
            this.changeMovement(new FollowPlayerMovement(this, player));
        }
    }

    private void stopMindControl() {
        mindControlled = false;
        this.changeMovement(new ShortestPathMovement(this));
    }

    private boolean hasMindControlledFinished(Game game) {
        return mindControlled && (game.getTick() - startTick >= mindControlDuration - 2);
    }

    @Override
    public void move(Game game) {
        getMovement().move(game, game.getMap());
        if (hasMindControlledFinished(game)) {
            stopMindControl();
        }
    }
}
