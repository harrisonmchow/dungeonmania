package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class PersistenceTest {

    @Test
    @Tag("22-1")
    @DisplayName("Test saving game, reloading without moving")
    public void saving() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_basicPosition",
                "c_PersistenceTest_basicPosition");
        Position playerPos = TestUtils.getEntities(res, "player").get(0).getPosition();
        Position spider1Pos = TestUtils.getEntities(res, "spider").get(0).getPosition();
        Position spider2Pos = TestUtils.getEntities(res, "spider").get(1).getPosition();
        assertDoesNotThrow(() -> dmc.saveGame("PersistenceTest_saving"));

        // Wait 2 seconds for bytes to go to file
        TimeUnit.SECONDS.sleep(2);
        res = dmc.loadGame("PersistenceTest_saving");
        assert (TestUtils.getEntities(res, "player").get(0).getPosition().equals(playerPos));
        assert (TestUtils.getEntities(res, "spider").get(0).getPosition().equals(spider1Pos));
        assert (TestUtils.getEntities(res, "spider").get(1).getPosition().equals(spider2Pos));
    }

    @Test
    @Tag("22-2")
    @DisplayName("Test saving game then reopening in succession")
    public void moveThenSaveAndReopen() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_basicPosition",
                "c_PersistenceTest_basicPosition");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.UP);
        res = dmc.tick(Direction.UP);
        Position player = TestUtils.getEntities(res, "player").get(0).getPosition();
        Position spider1 = TestUtils.getEntities(res, "spider").get(0).getPosition();
        Position spider2 = TestUtils.getEntities(res, "spider").get(1).getPosition();
        assertDoesNotThrow(() -> dmc.saveGame("PersistenceTest_saving2"));
        // Wait 5 seconds for data to load into the file

        TimeUnit.SECONDS.sleep(5);
        res = dmc.loadGame("PersistenceTest_saving2");
        assert (player.equals(TestUtils.getEntities(res, "player").get(0).getPosition()));
        assert (spider1.equals(TestUtils.getEntities(res, "spider").get(0).getPosition()));
        assert (spider2.equals(TestUtils.getEntities(res, "spider").get(1).getPosition()));
    }

    @Test
    @Tag("22-3")
    @DisplayName("Test saving, loading then achieving goal")
    public void saveLoadThenAchieveGoal() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_saveLoadThenAchieveGoal",
                "c_PersistenceTest_saveLoadThenAchieveGoal");
        assertDoesNotThrow(() -> dmc.saveGame("saveLoadThenAchieveGoal"));
        // Wait 2 seconds for data to load into the file

        TimeUnit.SECONDS.sleep(2);
        res = dmc.loadGame("saveLoadThenAchieveGoal");
        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("22-4")
    @DisplayName("Test inventory is saved")
    public void inventoryTest() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_inventoryTest",
                "c_PersistenceTest_inventoryTest");

        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        res = dmc.tick(Direction.DOWN); // Pick up treasure
        res = dmc.tick(Direction.RIGHT); // Pick up sword
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        dmc.saveGame("PersistenceTest_inventoryTest");

        // Wait 2 seconds for data to load into the file
        TimeUnit.SECONDS.sleep(2);
        res = dmc.loadGame("PersistenceTest_inventoryTest");
        res = dmc.tick(Direction.RIGHT);
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("22-5")
    @DisplayName("Test mercenary is still allied")
    public void allied() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_allied",
                "c_PersistenceTest_allied");
        String mercId = TestUtils.getEntitiesStream(res,
                "mercenary").findFirst().get().getId();
        res = dmc.tick(Direction.RIGHT); // Pick up treasure

        // achieve bribe
        res = assertDoesNotThrow(() -> dmc.interact(mercId));
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        dmc.saveGame("PersistenceTest_allied");
        // Wait 2 seconds for data to load into the file

        TimeUnit.SECONDS.sleep(2);
        res = dmc.loadGame("PersistenceTest_allied");
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Assert that the mercenary didn't fight the player, and both still exist
        assertEquals(1, TestUtils.getEntities(res, "player").size());
        assertEquals(1, TestUtils.getEntities(res, "mercenary").size());
    }

    @Test
    @Tag("22-6")
    @DisplayName("Test buildable entity is saved")
    public void buildable() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_buildable",
                "c_PersistenceTest_buildable");

        res = dmc.tick(Direction.RIGHT); // Pick up wood
        res = dmc.tick(Direction.RIGHT); // Pick up key
        res = dmc.tick(Direction.RIGHT); // Pick up sunstone
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        dmc.saveGame("PersistenceTest_buildable");

        // Wait 2 seconds for data to load into the file
        TimeUnit.SECONDS.sleep(2);
        res = dmc.loadGame("PersistenceTest_buildable");
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
    }

    @Test
    @Tag("22-7")
    @DisplayName("Test inventory can be used to build things even after persistence")
    public void saveThenBuild() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_buildable",
                "c_PersistenceTest_buildable");

        res = dmc.tick(Direction.RIGHT); // Pick up wood
        res = dmc.tick(Direction.RIGHT); // Pick up key
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        dmc.saveGame("PersistenceTest_buildable");
        // Wait 2 seconds for data to load into the file
        TimeUnit.SECONDS.sleep(2);
        res = dmc.loadGame("PersistenceTest_buildable");

        res = dmc.tick(Direction.RIGHT); // Pick up sunstone
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
    }

    @Test
    @Tag("22-8")
    @DisplayName("Test buildable can be used to mind control mercenary")
    public void useBuildable() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_useBuildable",
                "c_PersistenceTest_buildable");
        String mercId = TestUtils.getEntitiesStream(res,
                "mercenary").findFirst().get().getId();
        res = dmc.tick(Direction.RIGHT); // Pick up wood
        res = dmc.tick(Direction.RIGHT); // Pick up key
        res = dmc.tick(Direction.RIGHT); // Pick up sunstone
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        dmc.saveGame("PersistenceTest_buildable");

        // Wait 2 seconds for data to load into the file
        TimeUnit.SECONDS.sleep(2);
        res = dmc.loadGame("PersistenceTest_buildable");
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());
        assertDoesNotThrow(() -> dmc.interact(mercId));
    }

    @Test
    @Tag("22-9")
    @DisplayName("Test key can still be used after persistence")
    public void keys() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_PersistenceTest_keys",
                "c_PersistenceTest_buildable");

        res = dmc.tick(Direction.RIGHT); // Pick up key
        dmc.saveGame("PersistenceTest_keys");

        // Wait 2 seconds for data to load into the file
        TimeUnit.SECONDS.sleep(2);
        res = dmc.loadGame("PersistenceTest_keys");
        res = dmc.tick(Direction.RIGHT); // Pick up key
        res = dmc.tick(Direction.RIGHT); // Go through the door
        res = dmc.tick(Direction.RIGHT); // Reach exit
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("22-10")
    @DisplayName("Test can't load a non existent file")
    public void doesntExist() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        assertThrows(IllegalArgumentException.class, () -> dmc.loadGame("nonExistent File"));
    }

    @Test
    @Tag("22-11")
    @DisplayName("Test saved file exists in directory")
    public void fileInDirectory() throws InterruptedException {
        DungeonManiaController dmc = new DungeonManiaController();
        assertTrue(dmc.allGames().contains("PersistenceTest_saving"));
    }
}
