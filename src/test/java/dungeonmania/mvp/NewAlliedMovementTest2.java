package dungeonmania.mvp;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import static org.junit.jupiter.api.Assertions.*;

public class NewAlliedMovementTest2 {
    @Test
    @Tag("16-1")
    @DisplayName("Testing whether an ally will go towards the player via Dijkstra's")
    public void alliesApproachPlayer() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_NewAlliedMovementTest_AlliesApproachPlayer",
                "c_NewAlliedMovementTest_AlliesApproachPlayer");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Move right to pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Player and mercenary move toward each other
        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.LEFT);
        }
        assertEquals(new Position(4, 2), getMercPos(res));
        assertEquals(new Position(3, 2), getPlayerPos(res));
    }

    @Test
    @Tag("16-2")
    @DisplayName("Testing whether an ally will follow the players movement once it is adjacent")
    public void followsPlayer() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_NewAlliedMovementTest_AlliesApproachPlayer",
                "c_NewAlliedMovementTest_AlliesApproachPlayer");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();

        // Move right to pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());

        // Player and mercenary move toward each other
        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.LEFT);
        }
        assertEquals(new Position(4, 2), getMercPos(res));
        assertEquals(new Position(3, 2), getPlayerPos(res));

        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(4, 2), getPlayerPos(res));
        assertEquals(new Position(3, 2), getMercPos(res));
        res = dmc.tick(Direction.UP);
        assertEquals(new Position(4, 1), getPlayerPos(res));
        assertEquals(new Position(4, 2), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(3, 1), getPlayerPos(res));
        assertEquals(new Position(4, 1), getMercPos(res));
        res = dmc.tick(Direction.LEFT);
        assertEquals(new Position(2, 1), getPlayerPos(res));
        assertEquals(new Position(3, 1), getMercPos(res));
    }

    @Test
    @Tag("16-3")
    @DisplayName("Testing whether bribing a mercenary whilst one square away puts them in the correct position")
    public void onePositionAwayBribe() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_NewAlliedMovementTest_OnePositionAwayBribe",
                "c_NewAlliedMovementTest_AlliesApproachPlayer");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        assertEquals(new Position(5, 2), getPlayerPos(res));
        assertEquals(new Position(10, 2), getMercPos(res));
        // Move right to pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(6, 2), getPlayerPos(res));
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        // Move right so that the Player and Mercenary are next to each other
        res = dmc.tick(Direction.RIGHT);
        assertEquals(new Position(7, 2), getPlayerPos(res));
        assertEquals(new Position(8, 2), getMercPos(res));
        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        // The mercenary should now be in the Players previous distinct position
        assertEquals(new Position(7, 2), getPlayerPos(res));
        assertEquals(new Position(6, 2), getMercPos(res));

    }

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }

    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }
}
