package student.adventure;

/**
 * Stores the messages shown to the players
 */
public class Messages {
  public static final String EMPTY_COMMAND = "Nothing was entered.";
  public static final String INVALID_COMMAND = "I don't understand %s.";
  public static final String QUIT = "Exiting game.";
  public static final String SUCCESS = "You made it out, congratulations!";

  public static final String INVALID_DIRECTION = "I can't go \"%s\"!";
  public static final String PATH_LOCKED = "This path is locked.";

  public static final String TAKE_ITEM = "You took the %s.";
  public static final String ITEM_NOT_IN_ROOM = "There is no item \"%s\" in the room.";

  public static final String DROP_ITEM = "You dropped the %s.";
  public static final String ITEM_ALREADY_IN_ROOM = "The item \"%s\" is already in the room!";
  public static final String ITEM_NOT_IN_INVENTORY = "You don't have \"%s\"!";

  public static final String FAIL_STRUCTURE_ITEM_REQUIREMENTS
      = "You don't have the necessary item(s) to do this.";

  public static final String ROOM_DESCRIPTION  = "-------\n%s\n-------\nFrom here, you can go: %s";
  public static final String ITEM_DESCRIPTION  = "\nItems visible: %s";
  public static final String STRUCTURE_DESCRIPTION  = "\nOther action(s): %s";
}
