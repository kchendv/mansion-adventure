package student.adventure;

/*
 * A lock that blocks a certain Connection
 */
public class Lock {
  private int id = -1;
  private boolean isLocked = false;

  /**
   * Unlocks a locked Lock, and locks an unlocked Lock
   */
  public void flipLockState(){
    isLocked = !isLocked;
  }

  // Getters
  public int getId() {
    return id;
  }

  public boolean isLocked() {
    return isLocked;
  }
}
