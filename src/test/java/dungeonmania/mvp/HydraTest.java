package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class HydraTest {
    @Test
    @Tag("17-1")
    @DisplayName("Testing Hydra movement")
    public void hydraMovement() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_HydraTest_hydraMovement",
                "c_HydraTest_hydraMovement");
        assertEquals(1, getHydras(res).size());

        // Teams may assume that random movement includes choosing to stay still, so we
        // should just
        // check that they do move at least once in a few turns
        boolean hydraMoved = false;
        Position prevPosition = getHydras(res).get(0).getPosition();
        for (int i = 0; i < 5; i++) {
            res = dmc.tick(Direction.UP);
            if (!prevPosition.equals(getHydras(res).get(0).getPosition())) {
                hydraMoved = true;
                break;
            }
        }
        assertTrue(hydraMoved);
    }

    @Test
    @Tag("17-2")
    @DisplayName("Testing Hydra movement constraints")
    public void hydraWallInteraction() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_HydraTest_hydraWallInteraction",
                "c_HydraTest_hydraMovement");
        assertEquals(1, getHydras(res).size());

        // In this dungeon, we have trapped the hydra in with walls.
        // Since it has the same constraints as a zombie, it should not be able to move
        boolean hydraMoved = false;
        Position prevPosition = getHydras(res).get(0).getPosition();
        for (int i = 0; i < 5; i++) {
            res = dmc.tick(Direction.UP);
            if (!prevPosition.equals(getHydras(res).get(0).getPosition())) {
                hydraMoved = true;
                break;
            }
        }
        assertFalse(hydraMoved);
    }

    @Test
    @Tag("17-3")
    @DisplayName("Testing Hydra gaining health")
    public void hydraGainHealth() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_HydraTest_hydraGainHealth",
                "c_HydraTest_hydraGainHealth");

        // The Hydra will always gain health after being attacked, so the health should
        // not change.
        // The Hydra will defeat the Player
        assertEquals(1, getHydras(res).size());
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        List<EntityResponse> entities = postBattleResponse.getEntities();

        // Test that the Hydra has won the battle
        assertEquals(1, TestUtils.countEntityOfType(entities, "hydra"));
        assertEquals(0, TestUtils.countEntityOfType(entities, "player"));

        // The Hydra will win the battle in one round
        // (Attack = 1000, Damage Dealt = (1000 - 0) / 10 = 100, Player Health = 50)
        // It will always gain 10 health per round, so,
        // deltaEnemyHealth should = +10
        int playerHealth = Integer
                .parseInt(TestUtils.getValueFromConfigFile("player_health", "c_HydraTest_hydraGainHealth"));
        assertEquals(10, battle.getRounds().get(0).getDeltaEnemyHealth(), 0.001);
        // Delta health is negative so take negative here
        System.out.println("Change in Player health is: " + -battle.getRounds().get(0).getDeltaCharacterHealth());
        assertTrue(-battle.getRounds().get(0).getDeltaCharacterHealth() >= playerHealth);
    }

    @Test
    @Tag("17-4")
    @DisplayName("Testing Hydra never gaining health")
    public void hydraNeverGainHealth() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_HydraTest_hydraNeverGainHealth",
                "c_HydraTest_hydraNeverGainHealth");

        // The Hydra will never gain health after being attacked, so the battle
        // will be like any other enemy
        assertEquals(1, getHydras(res).size());
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        List<EntityResponse> entities = postBattleResponse.getEntities();

        // Test that the Player has won the battle
        assertEquals(0, TestUtils.countEntityOfType(entities, "hydra"));
        assertEquals(1, TestUtils.countEntityOfType(entities, "player"));

        int hydraHealth = Integer
                .parseInt(TestUtils.getValueFromConfigFile("hydra_health", "c_HydraTest_hydraNeverGainHealth"));
        // Delta health is negative so take negative here
        System.out.println("Change in Hydra health is: " + -battle.getRounds().get(0).getDeltaEnemyHealth());
        assertTrue(-battle.getRounds().get(0).getDeltaEnemyHealth() >= hydraHealth);
    }

    @Test
    @Tag("17-5")
    @DisplayName("Multiple battles where Hydra gains health")
    public void hydraGainHealthMultiple() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_HydraTest_hydraGainHealth",
                "c_HydraTest_hydraGainHealthMultiple");

        // The Hydra will always gain health since rate = 1, and never take damage
        // The Hydra will defeat the Player
        assertEquals(1, getHydras(res).size());
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        List<EntityResponse> entities = postBattleResponse.getEntities();

        // Test that the Hydra has won the battle
        assertEquals(1, TestUtils.countEntityOfType(entities, "hydra"));
        assertEquals(0, TestUtils.countEntityOfType(entities, "player"));

        // The Hydra will win the battle in 5 rounds
        // (Attack = 100, Damage Dealt = (100 - 0) / 10 = 10, Player Health = 50)
        // Hydra will always gain 15 health per round, so,
        // deltaEnemyHealth should = +15
        int healing = Integer.parseInt(TestUtils.getValueFromConfigFile("hydra_health_increase_amount",
                "c_HydraTest_hydraGainHealthMultiple"));
        for (int i = 0; i < 5; i++) {
            assertEquals(healing, battle.getRounds().get(i).getDeltaEnemyHealth(), 0.001);
            assertEquals(-10, battle.getRounds().get(i).getDeltaCharacterHealth(), 0.001);
        }
    }

    @Test
    @Tag("17-6")
    @DisplayName("Multiple battles where Hydra gains 0 health but also takes no damage")
    public void hydraTakesNoDamage() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_HydraTest_hydraGainHealth",
                "c_HydraTest_hydraTakesNoDamage");

        // The Hydra will always gain health since rate = 1, and never take damage
        // The Hydra will defeat the Player
        assertEquals(1, getHydras(res).size());
        DungeonResponse postBattleResponse = dmc.tick(Direction.RIGHT);
        BattleResponse battle = postBattleResponse.getBattles().get(0);
        List<EntityResponse> entities = postBattleResponse.getEntities();

        // Test that the Hydra has won the battle
        assertEquals(1, TestUtils.countEntityOfType(entities, "hydra"));
        assertEquals(0, TestUtils.countEntityOfType(entities, "player"));

        // The Hydra will win the battle in 5 rounds
        // (Attack = 100, Damage Dealt = (100 - 0) / 10 = 10, Player Health = 50)
        // Hydra will always gain 0 health per round, so, deltaEnemyHealth should = 0
        for (int i = 0; i < 5; i++) {
            assertEquals(0, battle.getRounds().get(i).getDeltaEnemyHealth(), 0.001);
            assertEquals(-10, battle.getRounds().get(i).getDeltaCharacterHealth(), 0.001);
        }

    }

    private List<EntityResponse> getHydras(DungeonResponse res) {
        return TestUtils.getEntities(res, "hydra");
    }
}
