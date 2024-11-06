package RandomDataGenerator.generator.generator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class XmlDataGeneratorTest {

    private XmlDataGenerator xmlDataGenerator;
    private List<String> fields;

    @BeforeEach
    public void setUp() {
        fields = Arrays.asList("name", "surname", "email", "age", "password", "city", "country", "maritalStatus");
        xmlDataGenerator = new XmlDataGenerator(fields);
    }

    @Test
    public void testGenerateRandomName() {
        String name = xmlDataGenerator.generateRandomName();
        assertNotNull(name, "Имя не должно быть null");
        assertTrue(name.contains(" "), "Имя должно содержать пробел");
    }

    @Test
    public void testGenerateRandomEmail() {
        String email = xmlDataGenerator.generateRandomEmail(1);
        assertNotNull(email, "Электронная почта не должна быть null");
        assertTrue(email.contains("@"), "Электронная почта должна содержать символ '@'");
    }

    @Test
    public void testGenerateRandomAge() {
        int age = xmlDataGenerator.generateRandomAge();
        assertTrue(age >= 0 && age < 100, "Возраст должен быть в пределах от 0 до 99");
    }

    @Test
    public void testGenerateRandomPassword() {
        String password = xmlDataGenerator.generateRandomPassword();
        assertNotNull(password, "Пароль не должен быть null");
        assertTrue(password.length() >= 10, "Пароль должен содержать хотя бы 10 символов");
    }

    @Test
    public void testGenerateRandomCity() {
        String city = xmlDataGenerator.generateRandomCity();
        assertNotNull(city, "Город не должен быть null");
        assertTrue(city.length() > 0, "Город должен содержать хотя бы один символ");
    }

    @Test
    public void testGenerateRandomCountry() {
        String country = xmlDataGenerator.generateRandomCountry();
        assertNotNull(country, "Страна не должна быть null");
        assertTrue(country.length() > 0, "Страна должна содержать хотя бы один символ");
    }

    @Test
    public void testGenerateRandomMaritalStatus() {
        String maritalStatus = xmlDataGenerator.generateRandomMaritalStatus();
        assertNotNull(maritalStatus, "Семейное положение не должно быть null");
        assertTrue(maritalStatus.length() > 0, "Семейное положение должно содержать хотя бы один символ");
    }

    @Test
    public void testGenerateXmlData() throws IOException {
        Path tempFile = Files.createTempFile("generated_data", ".xml");
        xmlDataGenerator.generateData(5, tempFile.toString());

        File file = tempFile.toFile();
        assertTrue(file.exists(), "Файл с данными не был создан");

        try {
            javax.xml.parsers.DocumentBuilderFactory dbFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(file);
            org.w3c.dom.NodeList nList = doc.getElementsByTagName("Person");

            assertTrue(nList.getLength() > 0, "XML файл не содержит элементов <Person>");
        } catch (Exception e) {
            fail("Ошибка при парсинге XML: " + e.getMessage());
        }

        Files.delete(tempFile);
    }

    @Test
    public void testGenerateXmlDataWithMissingFields() throws IOException {
        List<String> partialFields = Arrays.asList("name", "email");
        XmlDataGenerator partialGenerator = new XmlDataGenerator(partialFields);

        Path tempFile = Files.createTempFile("generated_data_partial", ".xml");
        partialGenerator.generateData(3, tempFile.toString());

        File file = tempFile.toFile();
        assertTrue(file.exists(), "Файл с данными не был создан");

        try {
            javax.xml.parsers.DocumentBuilderFactory dbFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
            javax.xml.parsers.DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = dBuilder.parse(file);
            org.w3c.dom.NodeList nList = doc.getElementsByTagName("Person");

            assertTrue(nList.getLength() > 0, "XML файл не содержит элементов <Person>");
        } catch (Exception e) {
            fail("Ошибка при парсинге XML: " + e.getMessage());
        }

        Files.delete(tempFile);
    }
}
