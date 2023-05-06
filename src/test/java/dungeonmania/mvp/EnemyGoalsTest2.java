package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyGoalsTest2 {
    @Test
    @Tag("15-1")
    @DisplayName("Test achieving a basic enemies goal with only one enemy and no spawners")
    public void killOneEnemy() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_enemyGoalsTest_KillOneEnemy", "c_enemyGoalsTest_KillOneEnemy");

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // move player to right to fight Spider
        // The player has 100 damage compared to spiders 5 health, so the player should
        // win
        res = dmc.tick(Direction.RIGHT);

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("15-2")
    @DisplayName("Test achieving a basic enemies goal by killing one enemy and destroying the zombie spawner")
    public void killAnEnemyThenDestroySpawner() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_enemyGoalsTest_KillAnEnemyThenDestroySpawner",
                "c_enemyGoalsTest_KillAnEnemyThenDestroySpawner");

        String spawnerId = TestUtils.getEntitiesStream(res, "zombie_toast_spawner").findFirst().get().getId();
        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // move player to right to fight Spider
        // The player has 100 damage compared to spiders 5 health, so the player should
        // win
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));

        // Move right 3 times, pick up the sword and end up on the left of
        // the spawner
        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.RIGHT);
        }
        res = dmc.tick(Direction.UP);
        // Can't interact with the spawner since we are diagonal:
        assertThrows(InvalidActionException.class, () -> dmc.interact(spawnerId));
        res = dmc.tick(Direction.DOWN);
        // Interact with the spawner
        res = assertDoesNotThrow(() -> dmc.interact(spawnerId));

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("15-3")
    @DisplayName("Killing an enemy and then use exit to achieve AND goal")
    public void killAnEnemyThenExitGoal() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_enemyGoalsTest_KillAnEnemyThenExitGoal",
                "c_enemyGoalsTest_KillAnEnemyThenExitGoal");

        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // move player to right to fight Spider
        // The player has 100 damage compared to spiders 5 health, so the player should
        // win
        res = dmc.tick(Direction.RIGHT);

        // assert enemy goal achieved but exit goal not achieved.
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":exit"));

        // Move right 3 times and move to exit which is at (5,3)
        for (int i = 0; i < 3; i++) {
            res = dmc.tick(Direction.RIGHT);
        }

        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }

    @Test
    @Tag("15-4")
    @DisplayName("Complex goal of Exit AND (enemies or treasure goal)")
    public void complexEnemyGoalAndOr() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_enemyGoalsTest_ComplexEnemyGoalAndOr",
                "c_enemyGoalsTest_ComplexEnemyGoalAndOr");
        // assert goal not met
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertTrue(TestUtils.getGoals(res).contains(":enemies"));
        assertTrue(TestUtils.getGoals(res).contains(":treasure"));

        // Move right and kill the spider. This should complete the enemies goal
        res = dmc.tick(Direction.RIGHT);
        assertTrue(TestUtils.getGoals(res).contains(":exit"));
        assertFalse(TestUtils.getGoals(res).contains(":enemies"));
        assertFalse(TestUtils.getGoals(res).contains(":treasure"));

        // Move up to reach the exit.
        res = dmc.tick(Direction.UP);
        // assert goal met
        assertEquals("", TestUtils.getGoals(res));
    }
}
