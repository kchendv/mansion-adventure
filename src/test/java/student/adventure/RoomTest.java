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
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;


public class RoomTest {
  private Gson gson;

  @Before
  public void setUp() {
    gson = new Gson();
  }

  @Test
  public void takeItem() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertTrue(room.takeItem("key"));
  }

  @Test
  public void itemNotInRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertFalse(room.takeItem("car"));
  }

  @Test
  public void repeatedTakeItem() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    room.takeItem("key");
    assertFalse(room.takeItem("key"));
  }

  @Test
  public void takeOtherItemsUnaffected() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    room.takeItem("pen");
    assertTrue(room.takeItem("key"));
  }

  @Test
  public void dropItem() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertFalse(room.dropItem("book"));
  }

  @Test
  public void itemAlreadyInRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertTrue(room.dropItem("key"));
  }

  @Test
  public void repeatedDropItem() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    room.dropItem("book");
    assertTrue(room.dropItem("book"));
  }

  @Test
  public void disableStructure() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    Structure targetStructure = room.getStructures().get(0);

    assertTrue(room.getStructures().contains(targetStructure));
    room.disableStructure(targetStructure);
    assertFalse(room.getStructures().contains(targetStructure));
  }

  @Test
  public void structureNotInRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    Reader structureJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/expectedStructure.json"));
    Structure targetStructure = gson.fromJson(structureJsonReader, Structure.class);

    room.disableStructure(targetStructure);
    assertEquals(1, room.getStructures().size());
  }

  @Test
  public void testStringOutput() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);
    String expectedString = "-------\n"
        + "You are at the entrance of a mansion. To the north, "
        + "a narrow corridor stretches into the foyer.\n"
        + "-------\n"
        + "From here, you can go: North\n"
        + "Items visible: key, pen\n"
        + "Other action(s): Unlock door";
    assertEquals(expectedString, room.toString());
  }

  @Test
  public void testStringOutputWithoutConnections() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/emptyConnectionsRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);
    String expectedString = "-------\n"
        + "You are at the entrance of a mansion. To the north, "
        + "a narrow corridor stretches into the foyer.\n"
        + "-------\n"
        + "From here, you can go: \n"
        + "Items visible: key, pen\n"
        + "Other action(s): Unlock door";
    assertEquals(expectedString, room.toString());
  }


  @Test
  public void testStringOutputWithoutItems() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/emptyItemsRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);
    String expectedString = "-------\n"
        + "You are at the entrance of a mansion. To the north, "
        + "a narrow corridor stretches into the foyer.\n"
        + "-------\n"
        + "From here, you can go: North\n"
        + "Other action(s): Unlock door";
    assertEquals(expectedString, room.toString());
  }

  @Test
  public void testStringOutputWithoutStructures() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/emptyStructuresRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);
    String expectedString = "-------\n"
        + "You are at the entrance of a mansion. To the north, "
        + "a narrow corridor stretches into the foyer.\n"
        + "-------\n"
        + "From here, you can go: North\n"
        + "Items visible: key, pen";
    assertEquals(expectedString, room.toString());
  }

  @Test
  public void getId() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertEquals(0, room.getId());
  }

  @Test
  public void getConnections() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    Reader expectedConnectionJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoomConnection.json"));
    Connection expectedConnection = gson.fromJson(expectedConnectionJsonReader, Connection.class);

    assertTrue(expectedConnection.getId() == room.getConnections().get(0).getId()
    && Objects.equals(expectedConnection.getDirection(),
        room.getConnections().get(0).getDirection())
    && Objects.equals(expectedConnection.getLockId(),
        room.getConnections().get(0).getLockId()));
  }

  @Test
  public void getStructures() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    Reader expectedStructureJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/expectedRoomStructure.json"));
    Structure expectedStructure = gson.fromJson(expectedStructureJsonReader, Structure.class);

    assertTrue(Objects.equals(expectedStructure.getAction(),
        room.getStructures().get(0).getAction())
        && Objects.equals(expectedStructure.getControls(),
            room.getStructures().get(0).getControls())
        && Objects.equals(expectedStructure.getRequiredItems(),
            room.getStructures().get(0).getRequiredItems())
        && Objects.equals(expectedStructure.getSuccessMessage(),
            room.getStructures().get(0).getSuccessMessage())
        && Objects.equals(expectedStructure.isRepeatable(),
        room.getStructures().get(0).isRepeatable())
    );
  }

  @Test
  public void emptyConnectionsRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/emptyConnectionsRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertEquals(0, room.getConnections().size());
  }

  @Test
  public void emptyStructureRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/emptyStructuresRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertEquals(0, room.getStructures().size());
  }

  @Test
  public void noIdRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/missingIdRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertEquals(-1, room.getId());
  }

  @Test
  public void nullConnectionsRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/missingConnectionsRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertNull(room.getConnections());
  }

  @Test
  public void nullStructureRoom() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/room/missingStructuresRoom.json"));
    Room room = gson.fromJson(expectedJsonReader, Room.class);

    assertNull(room.getStructures());
  }
}