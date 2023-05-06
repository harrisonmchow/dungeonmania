package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwampTileTest {
    @Test
    @Tag("21-1")
    @DisplayName("Test the player not affected by swamp tile")
    public void testPlayerUnaffectedBySwamp() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse initDungonRes = dmc.newGame(
                "d_SwampTileTest_testPlayerUnaffected", "c_SwampTileTest_testPlayerUnaffected");
        EntityResponse initPlayer = TestUtils.getPlayer(initDungonRes).get();

        // create the expected result
        EntityResponse expectedPlayer = new EntityResponse(initPlayer.getId(),
                initPlayer.getType(), new Position(2, 1), false);

        // move player right
        DungeonResponse actualDungonRes = dmc.tick(Direction.RIGHT);
        EntityResponse actualPlayer = TestUtils.getPlayer(actualDungonRes).get();

        // assert after movement
        assertTrue(TestUtils.entityResponsesEqual(expectedPlayer, actualPlayer));
    }

    @Test
    @Tag("21-2")
    @DisplayName("Test an enemy mercenary is affected")
    public void enemyAffected() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
                "d_SwampTileTest_enemyAffected", "c_SwampTileTest_testPlayerUnaffected");

        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        // The mercenary gets moves onto the swamp tile
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(),
                TestUtils.getEntities(res, "swamp_tile").get(0).getPosition());
        // Should be stuck on the swamp tile for the next 2 turns
        res = dmc.tick(Direction.RIGHT);
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(),
                TestUtils.getEntities(res, "swamp_tile").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(),
                TestUtils.getEntities(res, "swamp_tile").get(0).getPosition());

        // Can move off the tile
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(),
                TestUtils.getEntities(res, "swamp_tile").get(0).getPosition());
    }

    @Test
    @Tag("21-3")
    @DisplayName("Test an allied mercenary is still affected")
    public void allyAffected() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
                "d_SwampTileTest_enemyAffected", "c_SwampTileTest_testPlayerUnaffected");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        // Pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        // bribe mercenary
        res = dmc.interact(mercId);
        // The allied mercenary gets moves onto the swamp tile
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(),
                TestUtils.getEntities(res, "swamp_tile").get(0).getPosition());
        // Should be stuck on the swamp tile for the next 2 turns
        res = dmc.tick(Direction.RIGHT);
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(),
                TestUtils.getEntities(res, "swamp_tile").get(0).getPosition());

        res = dmc.tick(Direction.RIGHT);
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(),
                TestUtils.getEntities(res, "swamp_tile").get(0).getPosition());

        // Can move off the tile
        res = dmc.tick(Direction.RIGHT);
        assertNotEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(),
                TestUtils.getEntities(res, "swamp_tile").get(0).getPosition());
    }

    @Test
    @Tag("21-4")
    @DisplayName("Test an allied mercenary is still affected")
    public void adjacentAllyUnaffected() throws IllegalArgumentException, InvalidActionException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame(
                "d_SwampTileTest_AllyUnaffected", "c_SwampTileTest_testPlayerUnaffected");

        String mercId = TestUtils.getEntitiesStream(res, "mercenary").findFirst().get().getId();
        // Pick up treasure
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        // bribe mercenary
        res = dmc.interact(mercId);
        // They should now be adjacent.
        assertEquals(TestUtils.getEntities(res, "player").get(0).getPosition(), new Position(2, 0));
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(), new Position(3, 0));

        // Shouldn't get stuck
        res = dmc.tick(Direction.LEFT);
        assertEquals(TestUtils.getEntities(res, "player").get(0).getPosition(), new Position(1, 0));
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(), new Position(2, 0));
        res = dmc.tick(Direction.LEFT);
        assertEquals(TestUtils.getEntities(res, "player").get(0).getPosition(), new Position(0, 0));
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(), new Position(1, 0));
        // Mercenary on swamp tile, but it is allied
        res = dmc.tick(Direction.LEFT);
        assertEquals(TestUtils.getEntities(res, "player").get(0).getPosition(), new Position(-1, 0));
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(), new Position(0, 0));
        // Should move off it
        res = dmc.tick(Direction.LEFT);
        assertEquals(TestUtils.getEntities(res, "player").get(0).getPosition(), new Position(-2, 0));
        assertEquals(TestUtils.getEntities(res, "mercenary").get(0).getPosition(), new Position(-1, 0));
    }
}
