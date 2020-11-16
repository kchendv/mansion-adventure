package student.adventure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MessagesTest {

  @Test
  public void getEmptyCommand() {
    assertEquals("Nothing was entered.", Messages.EMPTY_COMMAND);
  }

  @Test
  public void getInvalidCommand() {
    assertEquals("I don't understand %s.", Messages.INVALID_COMMAND);
  }

  @Test
  public void getQuit() {
    assertEquals("Exiting game.", Messages.QUIT);
  }

  @Test
  public void getClear() {
    assertEquals("You made it out, congratulations!", Messages.SUCCESS);
  }

  @Test
  public void getInavlidDirection() {
    assertEquals("I can't go \"%s\"!", Messages.INVALID_DIRECTION);
  }

  @Test
  public void getPathLocked() {
    assertEquals("This path is locked.", Messages.PATH_LOCKED);

  }

  @Test
  public void getTakeItem() {
    assertEquals("You took the %s.", Messages.TAKE_ITEM);

  }

  @Test
  public void getItemNotInRoom() {
    assertEquals("There is no item \"%s\" in the room.", Messages.ITEM_NOT_IN_ROOM);
  }

  @Test
  public void getDropItem() {
    assertEquals("You dropped the %s.", Messages.DROP_ITEM);
  }

  @Test
  public void getItemAlreadyInRoom() {
    assertEquals("The item \"%s\" is already in the room!"
        , Messages.ITEM_ALREADY_IN_ROOM);
  }

  @Test
  public void getItemNotInInventory() {
    assertEquals("You don't have \"%s\"!", Messages.ITEM_NOT_IN_INVENTORY);
  }

  @Test
  public void getFailStructureItemRequirements() {
    assertEquals("You don't have the necessary item(s) to do this.",
        Messages.FAIL_STRUCTURE_ITEM_REQUIREMENTS);
  }

  @Test
  public void getRoomDescription() {
    assertEquals("-------\n%s\n-------\nFrom here, you can go: %s",
        Messages.ROOM_DESCRIPTION);
  }

  @Test
  public void getItemDescription() {
    assertEquals("\nItems visible: %s", Messages.ITEM_DESCRIPTION);
  }

  @Test
  public void getStructureDescription() {
    assertEquals("\nOther action(s): %s", Messages.STRUCTURE_DESCRIPTION);
  }
}