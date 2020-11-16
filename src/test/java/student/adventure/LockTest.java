package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;


public class LockTest {
  private Gson gson;

  @Before
  public void setUp() {
    gson = new Gson();
  }

  @Test
  public void getLockId() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/expectedLock.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);

    assertEquals(4, lock.getId());
  }

  @Test
  public void getFalseLockState() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/expectedLock.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);

    assertFalse(lock.isLocked());
  }

  @Test
  public void getTrueLockState() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/expectedTrueLockState.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);

    assertTrue(lock.isLocked());
  }

  @Test
  public void flipTrueToFalse() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/expectedTrueLockState.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);
    lock.flipLockState();
    assertFalse(lock.isLocked());
  }

  @Test
  public void flipFalseToTrue() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/expectedLock.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);
    lock.flipLockState();
    assertTrue(lock.isLocked());
  }

  @Test (expected = JsonSyntaxException.class)
  public void invalidTypeId() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/invalidTypeIdLock.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);
    assertEquals(-1, lock.getId());
  }

  @Test (expected = JsonSyntaxException.class)
  public void invalidTypeIsLocked() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/invalidTypeIsLockedLock.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);
  }

  @Test
  public void missingIsLocked() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/missingIsLockedLock.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);
    assertFalse(lock.isLocked());
  }

  @Test
  public void missingId() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/lock/missingIdLock.json"));
    Lock lock = gson.fromJson(expectedJsonReader, Lock.class);
    assertEquals(-1, lock.getId());
  }
}