package student.adventure;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ActionsTest {

  @Test
  public void getGo() {
    assertEquals("go", Actions.GO);
  }

  @Test
  public void getExamine() {
    assertEquals("examine", Actions.EXAMINE);
  }

  @Test
  public void getQuit() {
    assertEquals("quit", Actions.QUIT);
  }


  @Test
  public void getTake() {
    assertEquals("take", Actions.TAKE);
  }


  @Test
  public void getDrop() {
    assertEquals("drop", Actions.DROP);
  }
}