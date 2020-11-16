package student.adventure;

import java.util.ArrayList;

/**
 * A collection of the Rooms and Locks that are found in a game
 */
public class Dungeon {
  private ArrayList<Room> rooms;
  private ArrayList<Lock> locks;

  /**
   * Checks if a Connection is blocked
   * @param connection the Connection
   * @return true if and only if the Connection is not locked
   */
  public boolean pathIsLocked(Connection connection) {
    if (connection.getLockId() == -1) {
      return false;
    }
    return getLockById(connection.getLockId()).isLocked();
  }

  /**
   * Attempts to take an item from a Room
   * If the item is not found, nothing is removed
   * @param roomId the id of the Room
   * @param item the item
   * @return true if and only if the item was found and taken
   */
  public boolean takeItem(int roomId, String item){
    return getRoomById(roomId).takeItem(item);
  }

  /**
   * Attempts to drop an item in a room
   * If the item is already in the room, nothing is dropped
   * @param roomId the id of the room
   * @param item the item
   * @return true if and only if the item was dropped in the room
   */
  public boolean dropItem(int roomId, String item){
    return getRoomById(roomId).dropItem(item);
  }

  /**
   * Interacts with a structure in a room
   * Flips all of the states of the locks that it controls
   * Disables the structure if the interaction is not repeatable
   * Plays a message
   * @param roomId the id of the room
   * @param structure the structure
   */
  public void performStructureAction(int roomId, Structure structure) {
    if(!structure.isRepeatable()) {
      getRoomById(roomId).disableStructure(structure);
    }

    for(Integer lockId: structure.getControls()) {
      getLockById(lockId).flipLockState();
    }

    System.out.println(structure.getSuccessMessage());
  }

  /**
   * Gets the description of the Room and possible player actions
   * @param roomId the id of the Room
   * @return a String
   */
  public String getRoomString(int roomId) {
    return  getRoomById(roomId).toString();
  }

  /**
   * Attempts to search for a Connection from a Room towards a specific direction
   * @param roomId the id of the Room
   * @param direction the direction
   * @return a Connection if one of the specified direction is found, null otherwise
   */
  public Connection findConnectionByDirection(int roomId, String direction) {
    for (Connection connection : getRoomById(roomId).getConnections()) {
      if (connection.getDirection().toLowerCase().equals(direction)) {
        return connection;
      }
    }
    return null;
  }

  /**
   * Attempts to search for a Structure of a Room with the specified action call
   * @param roomId the id of the room
   * @param action the action call
   * @return a Structure if one of the specified action is found, null otherwise
   */
  public Structure findStructureByAction(int roomId, String action) {
    for (Structure structure : getRoomById(roomId).getStructures()) {
      if (structure.getAction().toLowerCase().equals(action.toLowerCase())) {
        return structure;
      }
    }
    return null;
  }

  /**
   * Searches for a Room in rooms with the corresponding id
   * @param roomId the id of the Room
   * @return the Room if one with the matching id is found, null otherwise
   */
  private Room getRoomById(int roomId){
    for (Room room : rooms){
      if (room.getId() == roomId){
        return room;
      }
    }
    throw new IllegalArgumentException();
  }

  /**
   * Searches for a Lock in locks with the corresponding id
   * @param lockId the id of the Lock
   * @return the Lock if one with the matching id is found, null otherwise
   */
  private Lock getLockById(int lockId){
    for (Lock lock : locks){
      if (lock.getId() == lockId){
        return lock;
      }
    }
    throw new IllegalArgumentException();
  }
}
