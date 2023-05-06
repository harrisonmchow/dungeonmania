package dungeonmania.entities;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logicals.Conductor;
import dungeonmania.entities.logicals.LogicalEntity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends LogicalEntity implements Conductor {
    private boolean activated;
    private List<Bomb> bombs = new ArrayList<>();

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER), "or");
    }

    public void subscribe(Bomb b) {
        bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (activated) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            bombs.stream().forEach(b -> b.notify(map));
            // Update the status of all other automatically when a switch is turned on
            map.getEntities(LogicalEntity.class).stream().forEach(e -> e.updateStatus(map));
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            // Update the status of all other automatically when a switch is turned on
            map.getEntities(LogicalEntity.class).stream().forEach(e -> e.updateStatus(map));
        }
    }

    @Override
    public boolean isActivated(GameMap map) {
        return activated;
    }
}
