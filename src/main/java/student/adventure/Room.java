package student.adventure;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * A single room in the dungeon
 */
public class Room {
  private int id = -1;
  private String message;
  private ArrayList<String> items;
  private ArrayList<Structure> structures;
  private ArrayList<Connection> connections;

  /**
   * Attempts to remove an item from the items in the Room
   * If the item is not found, nothing is removed
   * @param item the item
   * @return true if and only if the item was found and removed
   */
  public boolean takeItem(String item) {
    return items.remove(item);
  }

  /**
   * Attempts to add an item to the items in the Room
   * If the item is already in the Room, nothing is added
   * @param item the item
   * @return true if and only if the item was already in the room, and not added
   */
  public boolean dropItem(String item) {
    if (items.contains(item)) {
      return true;
    } else{
      items.add(item);
      return false;
    }
  }

  /**
   * Removes the specified structure from the
   * list of structures of the room
   * @param structure the structure
   */
  public void disableStructure(Structure structure) {
    structures.remove(structure);
  }

  /**
   * Returns the description of the room and possible player actions
   * @return a string
   */
  @Override
  public String toString() {
    // Code below derived from:
    // https://stackoverflow.com/questions/668952/the-simplest-way-to-comma-delimit-a-list
    // https://www.cs.colostate.edu/~cs160/.Summer16/resources/Java_printf_method_quick_reference.pdf
    StringJoiner possibleDirections = new StringJoiner(", ");
    for (Connection connection : connections) {
      possibleDirections.add(connection.getDirection());
    }

    String outputString = String.format(Messages.ROOM_DESCRIPTION, message, possibleDirections);

    if (items.size() != 0 ) {
      StringJoiner availableItems = new StringJoiner(", ");
      for (String item : items) {
        availableItems.add(item);
      }
      outputString += String.format(Messages.ITEM_DESCRIPTION, availableItems);
    }

    if (structures.size() != 0 ) {
      StringJoiner availableOtherActions = new StringJoiner(", ");
      for (Structure structure : structures) {
        availableOtherActions.add(structure.getAction());
      }
      outputString += String.format(Messages.STRUCTURE_DESCRIPTION, availableOtherActions);
    }
    return outputString;
  }

  // Getters
  public int getId() {
    return id;
  }

  public ArrayList<Connection> getConnections() {
    return connections;
  }

  public ArrayList<Structure> getStructures() {
    return structures;
  }


}
