package RandomDataGenerator.generator.generator;

import static org.junit.jupiter.api.Assertions.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class JsonDataGeneratorTest {

    @Test
    public void testGenerateRandomEmail() {
        JsonDataGenerator generator = new JsonDataGenerator(Arrays.asList("email"));
        String email = generator.generateRandomEmail(1);
        assertNotNull(email);
        assertTrue(email.contains("@"));
    }

    @Test
    public void testMaritalStatus() {
        JsonDataGenerator generator = new JsonDataGenerator(Arrays.asList("maritalStatus"));
        String maritalStatus = generator.generateRandomMaritalStatus();
        assertNotNull(maritalStatus);
        assertTrue(Arrays.asList("Single", "Married", "Divorced", "Widowed", "Engaged", "In a Relationship", "Separated", "Complicated").contains(maritalStatus));
    }

    @Test
    public void testGenerateData() throws IOException, JSONException {
        JsonDataGenerator generator = new JsonDataGenerator(Arrays.asList("name", "email", "age"));

        Path tempFile = Files.createTempFile("test_output", ".json");
        generator.generateData(5, tempFile.toString());

        File file = tempFile.toFile();
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        JSONArray jsonArray = new JSONArray(content);

        assertEquals(5, jsonArray.length(), "Должно быть 5 элементов");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject person = jsonArray.getJSONObject(i);
            assertTrue(person.has("name"));
            assertTrue(person.has("email"));
            assertTrue(person.has("age"));

            String email = person.getString("email");
            assertTrue(email.contains("@"));

            int age = person.getInt("age");
            assertTrue(age >= 0, "Возраст должен быть положительным числом");
        }

        Files.delete(tempFile);
    }

    @Test
    public void testGeneratedDataStructure() throws IOException, JSONException {
        JsonDataGenerator generator = new JsonDataGenerator(Arrays.asList("name", "email", "age"));

        Path tempFile = Files.createTempFile("test_output", ".json");
        generator.generateData(5, tempFile.toString());

        File file = tempFile.toFile();
        assertTrue(file.exists());

        String content = new String(Files.readAllBytes(file.toPath()));
        JSONArray jsonArray = new JSONArray(content);

        assertEquals(5, jsonArray.length(), "Должно быть 5 элементов");

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject person = jsonArray.getJSONObject(i);
            assertTrue(person.has("name"));
            assertTrue(person.has("email"));
            assertTrue(person.has("age"));

            String name = person.getString("name");
            assertTrue(name.length() > 0, "Имя не должно быть пустым");

            String email = person.getString("email");
            assertTrue(email.contains("@"), "Электронная почта должна содержать @");

            int age = person.getInt("age");
            assertTrue(age >= 0, "Возраст должен быть положительным числом");
        }

        Files.delete(tempFile);
    }
}