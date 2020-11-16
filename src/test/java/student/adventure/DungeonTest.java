package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;


public class DungeonTest {
  private Gson gson;

  @Before
  public void setUp() {
    gson = new Gson();
  }

  @Test
  public void pathIsLockedFalse() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    Connection testConnection = dungeon.findConnectionByDirection(0, "north");
    assertFalse(dungeon.pathIsLocked(testConnection));
  }

  @Test
  public void pathIsLockedTrue() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    Connection testConnection = dungeon.findConnectionByDirection(0, "south");
    assertTrue(dungeon.pathIsLocked(testConnection));
  }

  @Test (expected = IllegalArgumentException.class)
  public void pathIsLockedInvalidLockId() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/invalidLockIdDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    Connection testConnection = dungeon.findConnectionByDirection(0, "south");
    boolean southPathLocked = dungeon.pathIsLocked(testConnection);
  }

  @Test
  public void takeItem() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    assertTrue(dungeon.takeItem(0,"key"));
  }

  @Test
  public void itemNotInRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    assertFalse(dungeon.takeItem(0, "car"));
  }

  @Test
  public void repeatedTakeItem() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    dungeon.takeItem(0, "key");
    assertFalse(dungeon.takeItem(0, "key"));
  }

  @Test
  public void takeOtherItemsUnaffected() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    dungeon.takeItem(0, "pen");
    assertTrue(dungeon.takeItem(0, "key"));
  }

  @Test
  public void dropItem() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    assertFalse(dungeon.dropItem(0, "book"));
  }

  @Test
  public void itemAlreadyInRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    assertTrue(dungeon.dropItem(0, "key"));
  }

  @Test
  public void repeatedDropItem() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);

    dungeon.dropItem(0, "book");
    assertTrue(dungeon.dropItem(0, "book"));
  }

  @Test
  public void interactWithStructureUnlocksPath() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Structure targetStructure = dungeon.findStructureByAction(0,"Unlock door");
    dungeon.performStructureAction(0, targetStructure);


    Connection testConnection = dungeon.findConnectionByDirection(0, "south");
    assertFalse(dungeon.pathIsLocked(testConnection));
  }

  @Test
  public void interactWithUnrepeatableStructure() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Structure targetStructure = dungeon.findStructureByAction(0,"Unlock door");
    dungeon.performStructureAction(0, targetStructure);

    assertNull(dungeon.findStructureByAction(0,"Unlock door"));
  }

  @Test
  public void interactWithRepeatableStructure() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Structure targetStructure = dungeon.findStructureByAction(5,"Flip switch");
    dungeon.performStructureAction(5, targetStructure);

    assertEquals(targetStructure, dungeon.findStructureByAction(5,"Flip switch"));
  }

  @Test
  public void expectedGetRoomString() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    String expectedString = "-------\n"
        + "You are at the entrance of a mansion. To the north, "
        + "a narrow corridor stretches into the foyer.\n"
        + "-------\n"
        + "From here, you can go: North, South\n"
        + "Items visible: key, pen\n"
        + "Other action(s): Unlock door";

    assertEquals(expectedString, dungeon.getRoomString(0));
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidIdGetRoomString() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    String outputString = dungeon.getRoomString(100);
  }

  @Test
  public void expectedFindConnectionByDirection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Connection targetConnection = dungeon.findConnectionByDirection(0, "north");

    assertTrue(targetConnection.getId() == 2
    && Objects.equals(targetConnection.getDirection(),"North")
    && targetConnection.getLockId() == -1);
  }

  @Test
  public void invalidDirectionFindConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Connection targetConnection = dungeon.findConnectionByDirection(0, "down");

    assertNull(targetConnection);
  }

  @Test
  public void emptyDirectionFindConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Connection targetConnection = dungeon.findConnectionByDirection(0, "");

    assertNull(targetConnection);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidRoomIdFindConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Connection targetConnection = dungeon.findConnectionByDirection(100, "north");
  }

  @Test
  public void expectedFindStructureByAction() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Structure targetStructure = dungeon.findStructureByAction(0, "Unlock Door");

    ArrayList<Integer> expectedControls = new ArrayList<>(Arrays.asList(4));
    ArrayList<String> expectedRequiredItems = new ArrayList<>(Arrays.asList("key"));

    assertTrue(Objects.equals("Unlock door", targetStructure.getAction())
    && Objects.equals("You used the key to unlock the door.",targetStructure.getSuccessMessage())
    && Objects.equals(expectedControls, targetStructure.getControls())
    && Objects.equals(expectedRequiredItems, targetStructure.getRequiredItems())
    && targetStructure.isRepeatable() == false);
  }

  @Test
  public void invalidActionFindStructure() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Structure targetStructure = dungeon.findStructureByAction(0, "Drink potion");

    assertNull(targetStructure);
  }

  @Test
  public void emptyActionFindStructure() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Structure targetStructure = dungeon.findStructureByAction(0, "");

    assertNull(targetStructure);
  }

  @Test (expected = IllegalArgumentException.class)
  public void invalidRoomIdFindStructure() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/dungeon/expectedDungeon.json"));
    Dungeon dungeon = gson.fromJson(expectedJsonReader, Dungeon.class);
    Structure targetStructure = dungeon.findStructureByAction(90, "Unlock Door");
  }
}