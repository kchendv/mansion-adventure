package student.adventure;

import java.util.Objects;

/**
 * A connection of one room to another
 * Stored within the Room class to specify another room that it is connected with
 */
public class Connection {
  private int id = -1;
  private String direction;
  private int lockId =-1;

  // Getters
  public int getId() {
    return id;
  }

  public String getDirection() {
    return direction;
  }

  public int getLockId() {
    return lockId;
  }
}
