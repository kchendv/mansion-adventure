package student.adventure;

import java.util.ArrayList;

/**
 * A fixture in  a Room that the user is able to interact with
 */
public class Structure {
  private String action;
  private String successMessage;
  private ArrayList<Integer> controls;
  private ArrayList<String> requiredItems;
  private boolean isRepeatable;

  // Getters
  public String getAction() {
    return action;
  }

  public String getSuccessMessage() {
    return successMessage;
  }

  public ArrayList<Integer> getControls() {
    return controls;
  }

  public ArrayList<String> getRequiredItems() {
    return requiredItems;
  }

  public boolean isRepeatable() {
    return isRepeatable;
  }
}
