package student.adventure;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An instance of the game
 */
public class GameEngine {
  private static final int START_ID = 0;
  private static final int END_ID = 1;
  private final ArrayList<String> inventory = new ArrayList<>();
  private final Dungeon dungeon;
  private int currentId = START_ID;
  private boolean isOngoing = true;


  /**
   * Creates the game engine for a new Dungeon based on a Json file
   * @param sourcePath the Json file used to create the Dungeon map
   * @throws IOException if and only if a Dungeon cannot be created from the specified file
   */
  public GameEngine(String sourcePath) throws IOException {
    Gson gson = new Gson();
    Reader dungeonJsonReader = Files.newBufferedReader(
        Paths.get(sourcePath));
    dungeon = gson.fromJson(dungeonJsonReader, Dungeon.class);
  }

  /**
   * Main loop for the game
   * @param input the Scanner used to input commands
   */
  public void playGame(Scanner input, boolean userInputting) {
    // true if the player's enters a new Room
    // or re-examines the current Room
    boolean isNextRoomOrExamineCommand = true;

    while(isOngoing && (userInputting || input.hasNextLine())) {
      if (isNextRoomOrExamineCommand){
        isNextRoomOrExamineCommand = false;
        System.out.println(getCurrentRoomMessage());
      }
      System.out.print("> ");
      String nextAction = input.nextLine();
      isNextRoomOrExamineCommand = processAction(nextAction);
    }
  }

  /**
   * Returns the description and possible player actions for the current room
   * @return a string
   */
  private String getCurrentRoomMessage() {
    return dungeon.getRoomString(currentId);
  }

  /**
   * Processes the player's input
   * @param action the user input
   * @return true if the player enters a new room or re-examines the room
   */
  private boolean processAction(String action) {
    // Code below derived from:
    // https://stackoverflow.com/questions/13081527/splitting-string-on-multiple-spaces-in-java
    String[] parsedAction = action.toLowerCase().split("\\s+",2);

    switch (parsedAction[0]) {
      case "":
        System.out.println(Messages.EMPTY_COMMAND);
        return false;

      case Actions.EXAMINE:
        return true;

      case Actions.QUIT:
        isOngoing = false;
        System.out.println(Messages.QUIT);
        return false;

      case Actions.GO:
        return travel(parsedAction[1]);

      case Actions.TAKE:
        takeItem(parsedAction[1]);
        return false;

      case Actions.DROP:
        dropItem(parsedAction[1]);
        return false;

      default:
        otherAction(action);
        return false;
    }
  }

  /**
   * Moves the player to a new Room if there is path in the specified direction
   * and the path is not locked
   * @param direction the direction
   * @return true if the player successfully enters a new room
   */
  private boolean travel(String direction) {
    Connection targetConnection = dungeon.findConnectionByDirection(currentId, direction);
    if (targetConnection == null) {
      System.out.printf((Messages.INVALID_DIRECTION) + "%n",direction);
      return false;
    } else if (dungeon.pathIsLocked(targetConnection)) {
      System.out.println(Messages.PATH_LOCKED);
      return false;
    } else {
      currentId = targetConnection.getId();
      if (currentId == END_ID) {
        // Player is in the end room, game ends
        isOngoing = false;
        System.out.println(Messages.SUCCESS);
      }
      return true;
    }
  }

  /**
   * Attempts to take the specified item from the current Room
   * @param item the item
   */
  private void takeItem(String item) {
    boolean doesItemExist = dungeon.takeItem(currentId, item);
    if (doesItemExist) {
      inventory.add(item);
      System.out.printf(Messages.TAKE_ITEM + "%n", item);
    } else {
      System.out.printf(Messages.ITEM_NOT_IN_ROOM + "%n", item);
    }
  }

  /**
   * Attempts to drop the specified item in the current room
   * @param item the item
   */
  private void dropItem(String item) {
    if (inventory.contains(item)) {
      boolean wasItemInRoom = dungeon.dropItem(currentId, item);
      if (wasItemInRoom) {
        System.out.printf(Messages.ITEM_ALREADY_IN_ROOM + "%n", item);
      } else {
        inventory.remove(item);
        System.out.printf(Messages.DROP_ITEM + "%n", item);
      }
    } else {
      System.out.printf(Messages.ITEM_NOT_IN_INVENTORY + "%n", item);
    }
  }

  /**
   * Handles other action inputs by the player
   * @param action the action
   */
  private void otherAction(String action) {
    // Attempts to find a Structure in the room with the input action call
    Structure targetStructure = dungeon.findStructureByAction(currentId, action);
    if (targetStructure == null) {
      // A matching Structure is not found
      System.out.printf(Messages.INVALID_COMMAND + "%n", action);
    } else if (!inventory.containsAll(targetStructure.getRequiredItems())) {
      // The player does not have the necessary item(s) to interact with the structure
      System.out.println(Messages.FAIL_STRUCTURE_ITEM_REQUIREMENTS);
    } else {
      // The player interacts with the Structure using the item(s) required
      dungeon.performStructureAction(currentId, targetStructure);
      for (String item : targetStructure.getRequiredItems()) {
        inventory.remove(item);
      }
    }
  }
}
