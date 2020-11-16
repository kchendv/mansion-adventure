package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;


public class ConnectionTest {
  private Gson gson;

  @Before
  public void setUp() {
    gson = new Gson();
  }

  @Test
  public void getConnectionId() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/expectedConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);

    assertEquals(9, connection.getId());
  }

  @Test
  public void getConnectionDirection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/expectedConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);

    assertEquals("West", connection.getDirection());
  }

  @Test
  public void getConnectionLockId() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/expectedConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);

    assertEquals(3, connection.getLockId());
  }

  @Test (expected = JsonSyntaxException.class)
  public void invalidTypeIdConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/invalidTypeIdConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);
  }

  @Test (expected = JsonSyntaxException.class)
  public void invalidTypeLockIdConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/invalidTypeLockIdConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);
  }

  @Test
  public void otherTypeDirectionConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/otherTypeDirectionConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);
    assertEquals("3", connection.getDirection());
  }

  @Test
  public void missingIdConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/missingIdConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);

    assertEquals(-1, connection.getId());
  }

  @Test
  public void missingLockIdConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/missingLockIdConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);

    assertEquals(-1, connection.getLockId());
  }

  @Test
  public void missingDirectionConnection() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/connection/missingDirectionConnection.json"));
    Connection connection = gson.fromJson(expectedJsonReader, Connection.class);

    assertNull(connection.getDirection());
  }
}