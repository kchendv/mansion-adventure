package student.adventure;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.Before;
import org.junit.Test;

public class GameEngineTest {
  // Code below derived from:
  // https://stackoverflow.com/questions/1119385/junit-test-for-system-out-println
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
  }

  @Test
  public void winGame() throws IOException {
    GameEngine game = new GameEngine("src/test/data/gameengine/twoRoomDungeon.json");
    // Code below derived from:
    // https://docs.oracle.com/javase/7/docs/api/java/util/Scanner.html
    String inputString = "Go south\n";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    assertTrue(outContent.toString().contains("You made it out, congratulations!"));
  }

  @Test
  public void quitGame() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "quit";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    assertTrue(outContent.toString().contains("Exiting game."));
  }

  @Test
  public void invalidCommand() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "abcde";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);
    assertTrue(outContent.toString().contains("I don't understand abcde"));
  }

  @Test
  public void emptyCommand() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "\n";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);
    assertTrue(outContent.toString().contains("Nothing was entered."));
  }

  @Test
  public void examine() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "examine\n";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedDescription = "You are at the entrance of a mansion.";

    assertTrue(outContent.toString().contains(expectedDescription));
  }

  @Test
  public void goInvalidDirection() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "go down";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "I can't go \"down\"!";

    assertTrue(outContent.toString().contains(expectedMessage));
  }

  @Test
  public void goPathLocked() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "go south";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "This path is locked.";

    assertTrue(outContent.toString().contains(expectedMessage));
  }

  @Test
  public void tryTakeItem() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "go north\ngo north\ngo west\ntake key";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "You took the key.";

    assertTrue(outContent.toString().contains(expectedMessage));
  }

  @Test
  public void tryDropItem() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "go north\ngo north\ngo west\ntake key\ndrop key";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "You dropped the key.";

    assertTrue(outContent.toString().contains(expectedMessage));
  }

  @Test
  public void invalidTakeItem() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "go north\ngo north\ngo west\ntake car";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "There is no item \"car\" in the room.";

    assertTrue(outContent.toString().contains(expectedMessage));
  }

  @Test
  public void invalidDropItem() throws IOException {
    GameEngine game = new GameEngine("src/main/resources/dungeon.json");
    String inputString = "go north\ndrop car";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "You don't have \"car\"!";

    assertTrue(outContent.toString().contains(expectedMessage));
  }

  @Test
  public void itemAlreadyInRoom() throws IOException {
    GameEngine game = new GameEngine("src/test/data/gameengine/repeatedItemDungeon.json");
    String inputString = "take paper\ngo north\ndrop paper";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "The item \"paper\" is already in the room!";

    assertTrue(outContent.toString().contains(expectedMessage));
  }

  @Test
  public void interactWithStructure() throws IOException {
    GameEngine game = new GameEngine("src/test/data/gameengine/repeatedItemDungeon.json");
    String inputString = "go north\ngo north\ngo west\ntake key"
        + "\ngo east\ngo south\ngo east\nunlock door";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "You used the key to unlock the door.";

    assertTrue(outContent.toString().contains(expectedMessage));
  }

  @Test
  public void failStructureItemRequirements() throws IOException {
    GameEngine game = new GameEngine("src/test/data/gameengine/repeatedItemDungeon.json");
    String inputString = "go north\ngo north\ngo west"
        + "\ngo east\ngo south\ngo east\nunlock door";
    Scanner testInput = new Scanner(inputString).useDelimiter("\n");
    game.playGame(testInput, false);

    String expectedMessage = "You don't have the necessary item(s) to do this.";

    assertTrue(outContent.toString().contains(expectedMessage));
  }
}