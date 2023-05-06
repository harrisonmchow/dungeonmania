package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class SceptreTest {
    @Test
    @Tag("19-1")
    @DisplayName("Test player can build sceptre with 1 x wood, 1 x treasure, 1 x SunStone")
    public void buildSceptreWoodTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreWoodTreasure", "c_SceptreTest_buildSceptre");

        // Pick up wood
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // pick up treasure
        assertEquals(1, TestUtils.getEntities(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // pick up sunstone
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Check materials are removed from inventory
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("19-2")
    @DisplayName("Test player can build sceptre with 2 x arrows, 1 x treasure, 1 x Sunstone")
    public void buildSceptreArrowTreasure() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreArrowTreasure", "c_SceptreTest_buildSceptre");

        // Pick up arrow
        assertEquals(2, TestUtils.getEntities(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getEntities(res, "arrow").size());
        assertEquals(1, TestUtils.getInventory(res, "arrow").size());

        // pick up treasure
        assertEquals(1, TestUtils.getEntities(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // pick up sunstone
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up second arrow
        assertEquals(1, TestUtils.getEntities(res, "arrow").size());
        assertEquals(1, TestUtils.getInventory(res, "arrow").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "arrow").size());
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Build sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Check materials are removed from inventory
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("19-3")
    @DisplayName("Test player can build sceptre with 1 x wood, 1 x key, 1 x SunStone")
    public void buildSceptreWoodKey() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreWoodKey", "c_SceptreTest_buildSceptre");

        // Pick up wood
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // pick up key
        assertEquals(1, TestUtils.getEntities(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // pick up sunstone
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Check materials are removed from inventory
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("19-4")
    @DisplayName("Test player can build sceptre with 2 x arrows, 1 x treasure, 1 x Sunstone")
    public void buildSceptreArrowKey() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreArrowKey", "c_SceptreTest_buildSceptre");

        // Pick up arrow
        assertEquals(2, TestUtils.getEntities(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getEntities(res, "arrow").size());
        assertEquals(1, TestUtils.getInventory(res, "arrow").size());

        // pick up key
        assertEquals(1, TestUtils.getEntities(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "key").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // pick up sunstone
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Pick up second arrow
        assertEquals(1, TestUtils.getEntities(res, "arrow").size());
        assertEquals(1, TestUtils.getInventory(res, "arrow").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "arrow").size());
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Build sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Check materials are removed from inventory
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("19-5")
    @DisplayName("Test player can build sceptre with 1 x wood, 1 x Sunstone, 1 x Sunstone")
    public void buildSceptreWoodSunStone() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_buildSceptreWoodSunStone", "c_SceptreTest_buildSceptre");

        // Pick up wood
        assertEquals(1, TestUtils.getEntities(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // pick up key
        assertEquals(2, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // pick up sunstone
        assertEquals(1, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        res = dmc.tick(Direction.RIGHT);
        assertEquals(0, TestUtils.getEntities(res, "sun_stone").size());
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());

        // Build sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Check materials are removed from inventory
        // SunStone used to replace the key/treasure should be retained
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("19-6")
    @DisplayName("Test Player can mind control Mercenary using sceptre")
    public void mindControl() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_SceptreTest_mindControl", "c_SceptreTest_buildSceptre");
        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        // Collect materials for sceptre
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        // attempt interaction
        assertThrows(InvalidActionException.class, () -> dmc.interact(mercId));
        // Build sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        // Can now mind control
        assertDoesNotThrow(() -> dmc.interact(mercId)); // tick 1

        // Check that the Mercenary is allied and now follows the player
        res = dmc.tick(Direction.RIGHT); // tick 2
        assertEquals(new Position(5, 1), getPlayerPos(res));
        assertEquals(new Position(6, 1), getMercPos(res));

        res = dmc.tick(Direction.UP); // tick 3
        assertEquals(new Position(5, 0), getPlayerPos(res));
        assertEquals(new Position(5, 1), getMercPos(res));

        res = dmc.tick(Direction.LEFT); // tick 4
        assertEquals(new Position(4, 0), getPlayerPos(res));
        assertEquals(new Position(5, 0), getMercPos(res));

        res = dmc.tick(Direction.LEFT); // tick 5
        assertEquals(new Position(3, 0), getPlayerPos(res));
        assertEquals(new Position(4, 0), getMercPos(res));

        res = dmc.tick(Direction.LEFT); // enemy again
        assertEquals(new Position(2, 0), getPlayerPos(res));
        assertEquals(new Position(3, 0), getMercPos(res));

        // Now the mercenary isn't mind controlled anymore, so they can battle
        // The player should win
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.countEntityOfType(res.getEntities(), "player"));
        assertEquals(0, TestUtils.countEntityOfType(res.getEntities(), "mercenary"));
    }

    private Position getMercPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "mercenary").get(0).getPosition();
    }

    private Position getPlayerPos(DungeonResponse res) {
        return TestUtils.getEntities(res, "player").get(0).getPosition();
    }
}
