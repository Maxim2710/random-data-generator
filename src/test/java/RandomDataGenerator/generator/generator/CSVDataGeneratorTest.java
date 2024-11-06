package RandomDataGenerator.generator.generator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class CSVDataGeneratorTest {

    @Test
    public void testGenerateRandomName() {
        CSVDataGenerator generator = new CSVDataGenerator(Arrays.asList("name"));
        String name = generator.generateRandomName();
        assertNotNull(name);
        assertTrue(name.contains(" "), "Имя должно содержать пробел");
    }

    @Test
    public void testGenerateRandomMaritalStatus() {
        CSVDataGenerator generator = new CSVDataGenerator(Arrays.asList("maritalstatus"));
        String maritalStatus = generator.generateRandomMaritalStatus();
        assertNotNull(maritalStatus);
        assertTrue(Arrays.asList("Single", "Married", "Divorced", "Widowed", "Engaged", "In a Relationship", "Separated", "Complicated").contains(maritalStatus));
    }

    @Test
    public void testGenerateData() throws IOException {
        CSVDataGenerator generator = new CSVDataGenerator(Arrays.asList("name", "email", "age"));

        Path tempFile = Files.createTempFile("test_output", ".csv");
        generator.generateData(5, tempFile.toString());

        File file = tempFile.toFile();
        assertTrue(file.exists());
        assertTrue(file.length() > 0, "Файл не должен быть пустым");

        List<String> lines = Files.readAllLines(file.toPath());
        assertTrue(lines.size() > 0, "Файл не должен быть пустым");

        String header = lines.get(0);
        assertTrue(header.contains("name"));
        assertTrue(header.contains("email"));
        assertTrue(header.contains("age"));

        for (String line : lines.subList(1, lines.size())) {
            String[] fields = line.split(",");
            assertTrue(fields.length >= 3, "Строка должна содержать хотя бы 3 столбца");
        }

        Files.delete(tempFile);
    }

    @Test
    public void testGeneratedDataStructure() throws IOException {
        CSVDataGenerator generator = new CSVDataGenerator(Arrays.asList("name", "email", "age"));

        Path tempFile = Files.createTempFile("test_output", ".csv");
        generator.generateData(5, tempFile.toString());

        File file = tempFile.toFile();
        assertTrue(file.exists());

        List<String> lines = Files.readAllLines(file.toPath());

        assertFalse(lines.isEmpty(), "CSV файл не должен быть пустым");

        String header = lines.get(0);
        assertTrue(header.contains("name"));
        assertTrue(header.contains("email"));
        assertTrue(header.contains("age"));

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] columns = line.split(",");

            assertEquals(3, columns.length, "Каждая строка должна содержать 3 столбца");

            String name = columns[0];
            String email = columns[1];
            String ageStr = columns[2];

            assertTrue(name.length() > 0, "Имя не должно быть пустым");

            assertTrue(email.contains("@"), "Email должен содержать @");

            try {
                int age = Integer.parseInt(ageStr);
                assertTrue(age >= 0, "Возраст должен быть положительным числом");
            } catch (NumberFormatException e) {
                fail("Возраст должен быть числом");
            }
        }

        Files.delete(tempFile);
    }
}