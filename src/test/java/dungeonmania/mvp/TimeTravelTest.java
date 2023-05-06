package dungeonmania.mvp;

// import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
// import dungeonmania.util.Position;

public class TimeTravelTest {

        @Test
        @Tag("23-1")
        @DisplayName("Can pick up Time Turner")
        public void pickUpTimeTurner() {
                DungeonManiaController dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_TimeTravelTest_PickUpTimeTurner",
                                "c_TimeTravelTest_PickUpTimeTurner");

                assertEquals(1, TestUtils.getEntities(res, "time_turner").size());
                assertEquals(0, TestUtils.getInventory(res, "time_turner").size());

                // pick up Time Turner
                res = dmc.tick(Direction.RIGHT);
                assertEquals(1, TestUtils.getInventory(res, "time_turner").size());
                assertEquals(0, TestUtils.getEntities(res, "time_turner").size());
        }

        @Test
        @Tag("23-2")
        @DisplayName("Can pick up Time Turner")
        public void overlapPortal() {
                DungeonManiaController dmc = new DungeonManiaController();
                DungeonResponse res = dmc.newGame("d_TimeTravelTest_portal",
                                "c_TimeTravelTest_PickUpTimeTurner");

                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                res = dmc.tick(Direction.RIGHT);
                assertEquals(1, TestUtils.getEntities(res, "time_travelling_portal").size());
                assertEquals(TestUtils.getEntities(res, "player").get(0).getPosition(),
                                TestUtils.getEntities(res, "time_travelling_portal").get(0).getPosition());
        }

        //
        // @Test
        //
        // @Tag("23-2")
        //
        // @DisplayName("Testing invalid rewind")
        // public void invalidRewind() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_PickUpTimeTurner",
        // "c_TimeTravelTest_PickUpTimeTurner");
        //
        // assertEquals(1, TestUtils.getEntities(res, "time_turner").size());
        // assertEquals(0, TestUtils.getInventory(res, "time_turner").size());
        //
        //// pick up Time Turner
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "time_turner").size());
        // assertEquals(0, TestUtils.getEntities(res, "time_turner").size());
        //
        // res = dmc.tick(Direction.RIGHT);
        // assertThrows(IllegalArgumentException.class, () -> dmc.rewind(5));
        // }
        //
        // @Test
        //
        // @Tag("23-3")
        //
        // @DisplayName("Using time turner moves game back 1 tick")
        // public void validRewind() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_PickUpTimeTurner",
        // "c_TimeTravelTest_PickUpTimeTurner");
        //
        // Position originalPos = getPlayerPos(res);
        // assertEquals(1, TestUtils.getEntities(res, "time_turner").size());
        // assertEquals(0, TestUtils.getInventory(res, "time_turner").size());
        //
        //// pick up Time Turner
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "time_turner").size());
        // assertEquals(0, TestUtils.getEntities(res, "time_turner").size());
        // assertDoesNotThrow(() -> dmc.rewind(1));
        //// Rewinds but does not put them in the old position
        // assert (!getPlayerPos(res).equals(originalPos));
        // assert (getPlayerPos(res).equals(Position.translateBy(originalPos,
        // Direction.RIGHT)));
        // assert (getOlderSelfPos(res).equals(originalPos));
        // }
        //
        // @Test
        //
        // @Tag("23-4")
        //
        // @DisplayName("Using time turner moves game back 5 ticks")
        // public void validRewind5() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_PickUpTimeTurner",
        // "c_TimeTravelTest_PickUpTimeTurner");
        //
        // Position originalPos = getPlayerPos(res);
        // assertEquals(1, TestUtils.getEntities(res, "time_turner").size());
        // assertEquals(0, TestUtils.getInventory(res, "time_turner").size());
        //
        //// pick up Time Turner
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "time_turner").size());
        // assertEquals(0, TestUtils.getEntities(res, "time_turner").size());
        // res = dmc.tick(Direction.RIGHT);
        // res = dmc.tick(Direction.RIGHT);
        // res = dmc.tick(Direction.RIGHT);
        // res = dmc.tick(Direction.RIGHT);
        // assertDoesNotThrow(() -> dmc.rewind(5));
        // assert (!getPlayerPos(res).equals(originalPos));
        // assert (getOlderSelfPos(res).equals(originalPos));
        // }
        //
        // @Test
        //
        // @Tag("23-5")
        //
        // @DisplayName("Using time turner moves game back 5 ticks including zombie")
        // public void rewindWithZombie() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_rewindWithZombie",
        // "c_TimeTravelTest_PickUpTimeTurner");
        //
        // Position originalZPos = getZombiePos(res);
        // assertEquals(1, TestUtils.getEntities(res, "time_turner").size());
        // assertEquals(0, TestUtils.getInventory(res, "time_turner").size());
        //
        //// pick up Time Turner
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "time_turner").size());
        // assertEquals(0, TestUtils.getEntities(res, "time_turner").size());
        // res = dmc.tick(Direction.RIGHT);
        // res = dmc.tick(Direction.RIGHT);
        // res = dmc.tick(Direction.RIGHT);
        // res = dmc.tick(Direction.RIGHT);
        // assertDoesNotThrow(() -> dmc.rewind(5));
        // assert (getZombiePos(res).equals(originalZPos));
        // }
        //
        // @Test
        //
        // @Tag("23-6")
        //
        // @DisplayName("Using time turner moves game back 5 ticks including zombie")
        // public void inventory() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_inventory",
        // "c_TimeTravelTest_PickUpTimeTurner");
        //
        // Position originalSwordPos = TestUtils.getEntities(res,
        // "sword").get(0).getPosition();
        // assertEquals(1, TestUtils.getEntities(res, "time_turner").size());
        // assertEquals(0, TestUtils.getInventory(res, "time_turner").size());
        //
        //// pick up Time Turner
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "time_turner").size());
        // assertEquals(0, TestUtils.getEntities(res, "time_turner").size());
        //// Pick up sword
        // assertEquals(1, TestUtils.getEntities(res, "sword").size());
        // assertEquals(0, TestUtils.getInventory(res, "sword").size());
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "sword").size());
        // assertEquals(0, TestUtils.getEntities(res, "sword").size());
        //
        // res = dmc.tick(Direction.RIGHT);
        // res = dmc.tick(Direction.RIGHT);
        // res = dmc.tick(Direction.RIGHT);
        // assertDoesNotThrow(() -> dmc.rewind(5));
        //
        //// The sword should be on both the map and in the inventory
        // assertEquals(1, TestUtils.getInventory(res, "sword").size());
        // assertEquals(1, TestUtils.getEntities(res, "sword").size());
        // assert (TestUtils.getEntities(res,
        // "sword").get(0).getPosition().equals(originalSwordPos));
        // }
        //
        // @Test
        //
        // @Tag("23-7")
        //
        // @DisplayName("Testing invalid rewind")
        // public void invalidTicks() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_PickUpTimeTurner",
        // "c_TimeTravelTest_PickUpTimeTurner");
        //
        // assertEquals(1, TestUtils.getEntities(res, "time_turner").size());
        // assertEquals(0, TestUtils.getInventory(res, "time_turner").size());
        //
        //// pick up Time Turner
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "time_turner").size());
        // assertEquals(0, TestUtils.getEntities(res, "time_turner").size());
        //
        // res = dmc.tick(Direction.RIGHT);
        // assertThrows(IllegalArgumentException.class, () -> dmc.rewind(-10));
        // }
        //
        // @Test
        //
        // @Tag("23-8")
        //
        // @DisplayName("Testing can go through time travelling portal")
        // public void portal() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_portal",
        // "c_TimeTravelTest_PickUpTimeTurner");
        // Position originalPos = getPlayerPos(res);
        //// pick up sword
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "sword").size());
        // assertEquals(0, TestUtils.getEntities(res, "sword").size());
        //
        // res = dmc.tick(Direction.RIGHT);
        //// Move through time travelling portal
        // res = dmc.tick(Direction.RIGHT);
        //// It should be the original game state
        // assertEquals(1, TestUtils.getEntities(res, "older_player").size());
        // assertEquals(1, TestUtils.getEntities(res, "sword").size());
        // assert (getOlderSelfPos(res).equals(originalPos));
        //
        //// Player should still have sword
        // assertEquals(1, TestUtils.getInventory(res, "sword").size());
        // }
        //
        // @Test
        //
        // @Tag("23-9")
        //
        // @DisplayName("Testing player can fight and defeat older self using portal")
        // public void defeatOlder() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_defeatOlder",
        // "c_TimeTravelTest_PickUpTimeTurner");
        //// Pick up sword
        // for (int i = 0; i < 8; i++) {
        // res = dmc.tick(Direction.RIGHT);
        // }
        // assertEquals(1, TestUtils.getInventory(res, "sword").size());
        //// Go through Portal
        // res = dmc.tick(Direction.RIGHT);
        //// Older self should now exist
        // assertEquals(1, TestUtils.getEntities(res, "older_player").size());
        //
        //// Go back and fight them
        // for (int i = 0; i < 5; i++) {
        // res = dmc.tick(Direction.LEFT);
        // }
        //// Player has sword, + 100 attack.
        //// Player has base attack of 1, health of 10
        //// Older Player has same stats and original player
        //// Player should win against Older Player
        // assertEquals(0, TestUtils.getEntities(res, "older_player").size());
        // assertEquals(1, TestUtils.getEntities(res, "player").size());
        // }
        //
        // @Test
        //
        // @Tag("23-10")
        //
        // @DisplayName("Testing player can fight and defeat older self using time
        // turner"
        // )
        // public void defeatOlderTurner() {
        // DungeonManiaController dmc = new DungeonManiaController();
        // DungeonResponse res = dmc.newGame("d_TimeTravelTest_defeatOlderTurner",
        // "c_TimeTravelTest_PickUpTimeTurner");
        //// Pick up sword
        // for (int i = 0; i < 8; i++) {
        // res = dmc.tick(Direction.RIGHT);
        // }
        // assertEquals(1, TestUtils.getInventory(res, "sword").size());
        //// Pick up turner and use it
        // res = dmc.tick(Direction.RIGHT);
        // assertEquals(1, TestUtils.getInventory(res, "time_turner").size());
        // assertDoesNotThrow(() -> dmc.rewind(5));
        //// Older self should now exist
        // assertEquals(1, TestUtils.getEntities(res, "older_player").size());
        //
        //// Go back and fight them
        // for (int i = 0; i < 4; i++) {
        // res = dmc.tick(Direction.LEFT);
        // }
        //// Player has sword, + 100 attack.
        //// Player has base attack of 1, health of 10
        //// Older Player has same stats and original player
        //// Player should win against Older Player
        // assertEquals(0, TestUtils.getEntities(res, "older_player").size());
        // assertEquals(1, TestUtils.getEntities(res, "player").size());
        // }
        //
        // private Position getPlayerPos(DungeonResponse res) {
        // return TestUtils.getEntities(res, "player").get(0).getPosition();
        // }
        //
        // private Position getZombiePos(DungeonResponse res) {
        // return TestUtils.getEntities(res, "zombie").get(0).getPosition();
        // }
        //
        // private Position getOlderSelfPos(DungeonResponse res) {
        // return TestUtils.getEntities(res, "older_player").get(0).getPosition();
        // }

}
