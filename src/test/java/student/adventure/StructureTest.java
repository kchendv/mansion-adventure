package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;


public class StructureTest {
  private Gson gson;

  @Before
  public void setUp() {
    gson = new Gson();
  }

  @Test
  public void getAction() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/expectedStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertEquals("Unlock door", structure.getAction());
  }

  @Test
  public void getSuccessMessage() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/expectedStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertEquals("You used the key to unlock the door.", structure.getSuccessMessage());
  }

  @Test
  public void getControls() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/expectedStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    ArrayList<Integer> expectedControls = new ArrayList<>(Arrays.asList(0));
    assertEquals(expectedControls, structure.getControls());
  }

  @Test
  public void getRequiredItems() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/expectedStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    ArrayList<String> expectedRequiredItems = new ArrayList<>(Arrays.asList("key"));
    assertEquals(expectedRequiredItems, structure.getRequiredItems());
  }

  @Test
  public void getRepeatableFalse() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/expectedStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertFalse(structure.isRepeatable());
  }

  @Test
  public void getRepeatableTrue() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/isRepeatableTrueStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertTrue(structure.isRepeatable());
  }

  @Test
  public void missingAction() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/missingActionStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertNull(structure.getAction());
  }

  @Test
  public void missingSuccessMessage() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/missingSuccessMessageStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertNull(structure.getSuccessMessage());
  }

  @Test
  public void missingControls() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/missingControlsStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertNull(structure.getControls());
  }

  @Test
  public void missingRequiredItems() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/missingRequiredItemsStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertNull(structure.getRequiredItems());
  }

  @Test
  public void missingIsRepeatable() throws IOException {
    Reader expectedJsonReader = Files.newBufferedReader(
        Paths.get("src/test/data/structure/missingIsRepeatableStructure.json"));
    Structure structure = gson.fromJson(expectedJsonReader, Structure.class);

    assertFalse(structure.isRepeatable());
  }
}