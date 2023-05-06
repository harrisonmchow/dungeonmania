package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.EntityResponse;
import dungeonmania.util.Direction;
//import dungeonmania.util.Position;
import dungeonmania.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import dungeonmania.response.models.BattleResponse;
import dungeonmania.response.models.RoundResponse;
//import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MidnightArmourTest {

    @Test
    @Tag("20-1")
    @DisplayName("Test building midnight armour")
    public void buildMidnightArmour() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_BuildMidnightArmour",
        "c_MidnightArmourTest_BuildMidnightArmour");

        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // Pick up Sword x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sword").size());

        // Pick up Sun Stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Midnight Armour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Sunstone and sword used in construction disappear from inventory
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("20-2")
    @DisplayName("Test midnight armour reduces enemy attack")
    public void testMidnightArmourDefence() throws InvalidActionException {
        DungeonManiaController controller;
        controller = new DungeonManiaController();
        String config = "c_MidnightArmourTests_defence";
        DungeonResponse res = controller.newGame("d_MidnightArmourTest_defence", config);

        // Pick up Wood and Sword
        res = controller.tick(Direction.RIGHT);
        res = controller.tick(Direction.RIGHT);

        // Pick up sun stone
        res = controller.tick(Direction.RIGHT);

        // Pick up key
        res = controller.tick(Direction.RIGHT);

        assertEquals(1, TestUtils.getInventory(res, "wood").size());
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        res = controller.build("midnight_armour");

        res = controller.tick(Direction.RIGHT);

        BattleResponse battle = res.getBattles().get(0);

        RoundResponse firstRound = battle.getRounds().get(0);

        // Assumption: Shield effect calculation to reduce damage makes enemyAttack =
        // enemyAttack - shield effect
        int enemyAttack = Integer.parseInt(TestUtils.getValueFromConfigFile("spider_attack", config));
        int armourEffect = Integer.parseInt(TestUtils.getValueFromConfigFile("midnight_armour_defence", config));
        int expectedDamage = (enemyAttack - armourEffect) / 10;
        // Delta health is negative so take negative here
        assertEquals(expectedDamage, -firstRound.getDeltaCharacterHealth(), 0.001);
    }

    @Test
    @Tag("20-3")
    @DisplayName("Test Midnight Armour increases attack damage")
    public void testMidnightArmourAttack() {
        DungeonManiaController controller = new DungeonManiaController();
        String config = "c_MidnightArmourTests_Attack";
        controller.newGame("d_MidnightArmourTest_Attack", config);
        // Pick up sword
        DungeonResponse res = controller.tick(Direction.RIGHT);

        // Pick up sun stone
        res = controller.tick(Direction.RIGHT);

        // Battle the zombie
        res = controller.tick(Direction.RIGHT);
        List<BattleResponse> battles = res.getBattles();
        BattleResponse battle = battles.get(0);

        // This is the attack without the sword
        double playerBaseAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("player_attack", config));
        double mArmourAttack = Double.parseDouble(TestUtils.getValueFromConfigFile("midnight_armour_attack", config));

        RoundResponse firstRound = battle.getRounds().get(0);

        assertEquals((playerBaseAttack + mArmourAttack) / 5, -firstRound.getDeltaEnemyHealth(), 0.001);
    }

    @Test
    @Tag("20-4")
    @DisplayName("Test cant build midnight armour due to zombies on map")
    public void cantBuildMidnightArmourZombiesPresent() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_ZombiesPresent",
        "c_MidnightArmourTest_ZombiesPresent");

        // Check there is 1 zombie
        assertEquals(1, getZombies(res).size());

        // Empty Inventory
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // Pick up Sword x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sword").size());

        // Pick up Sun Stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Cannot Build Midnight Armour
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // Sunstone and sword not used
        assertEquals(2, TestUtils.getInventory(res, "sword").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
    }

    @Test
    @Tag("20-5")
    @DisplayName("Test can build midnight armour with Hydra on map")
    public void canBuildMidnightArmourHydraPresent() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_MidnightArmourTest_HydraPresent",
        "c_MidnightArmourTest_HydraPresent");

        // Check there is 1 zombie (Hydra is a subclass of zombie)
        assertEquals(1, getHydras(res).size());

        // Empty Inventory
        assertEquals(0, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());

        // Pick up Sword x2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        assertEquals(2, TestUtils.getInventory(res, "sword").size());

        // Pick up Sun Stone
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Builds Midnight Armour
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // 1 Sword and 1 Sun Stone used
        assertEquals(1, TestUtils.getInventory(res, "sword").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
    }

    private List<EntityResponse> getZombies(DungeonResponse res) {
        return TestUtils.getEntities(res, "zombie_toast");
    }
    private List<EntityResponse> getHydras(DungeonResponse res) {
        return TestUtils.getEntities(res, "hydra");
    }
}
