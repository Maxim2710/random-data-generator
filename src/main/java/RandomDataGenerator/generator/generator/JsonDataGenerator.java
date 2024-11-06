package RandomDataGenerator.generator.generator;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class JsonDataGenerator implements RandomDataGenerator {
    private final List<String> fields;
    private static final String[] FIRST_NAMES = {
            "John", "Jane", "Alex", "Emily", "Michael", "Sarah", "David", "Olivia", "Ethan", "Sophia",
            "James", "Isabella", "William", "Ava", "Benjamin", "Mia", "Samuel", "Charlotte", "Lucas", "Amelia"
    };

    private static final String[] LAST_NAMES = {
            "Doe", "Smith", "Johnson", "Brown", "Williams", "Taylor", "Davis", "Miller", "Wilson", "Moore",
            "Anderson", "Thomas", "Jackson", "White", "Harris", "Martin", "Thompson", "Garcia", "Martinez", "Roberts"
    };

    private static final String[] DOMAINS = {
            "example.com", "email.com", "test.org", "sample.net", "mail.com", "webmail.net", "contact.org", "xyz.co",
            "myemail.com", "emailservice.net", "fastmail.com", "mailbox.org", "coolmail.net", "protonmail.com"
    };

    private static final String[] CITIES = {
            "New York", "Los Angeles", "Chicago", "Houston", "Phoenix", "San Francisco", "Boston", "Seattle", "Denver", "Miami",
            "Dallas", "Washington D.C.", "Philadelphia", "Atlanta", "San Diego", "Minneapolis", "Cleveland", "Detroit", "Austin", "Portland"
    };

    private static final String[] COUNTRIES = {
            "USA", "Canada", "UK", "Germany", "Australia", "France", "Italy", "Spain", "India", "China",
            "Russia", "Japan", "Brazil", "Mexico", "South Korea", "South Africa", "Argentina", "Canada", "Norway", "Netherlands"
    };

    private static final String[] MARITAL_STATUSES = {
            "Single", "Married", "Divorced", "Widowed", "Engaged", "In a Relationship", "Separated", "Complicated"
    };


    public JsonDataGenerator(List<String> fields) {
        this.fields = fields;
    }

    private String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    String generateRandomEmail(int index) {
        Random random = new Random();
        String domain = DOMAINS[random.nextInt(DOMAINS.length)];
        return "user" + index + "@" + domain;
    }

    private int generateRandomAge() {
        return new Random().nextInt(100);
    }

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    private String generateRandomCity() {
        Random random = new Random();
        return CITIES[random.nextInt(CITIES.length)];
    }

    private String generateRandomCountry() {
        Random random = new Random();
        return COUNTRIES[random.nextInt(COUNTRIES.length)];
    }

    String generateRandomMaritalStatus() {
        Random random = new Random();
        return MARITAL_STATUSES[random.nextInt(MARITAL_STATUSES.length)];
    }

    @Override
    public void generateData(int count, String filePath) throws IOException {
        try (FileWriter file = new FileWriter(filePath)) {
            file.write("[\n");
            for (int i = 0; i < count; i++) {
                JSONObject json = new JSONObject();

                for (String field : fields) {
                    switch (field) {
                        case "name":
                            json.put("name", generateRandomName());
                            break;
                        case "surname":
                            json.put("surname", generateRandomName().split(" ")[1]);
                            break;
                        case "email":
                            json.put("email", generateRandomEmail(i + 1));
                            break;
                        case "age":
                            json.put("age", generateRandomAge());
                            break;
                        case "password":
                            json.put("password", generateRandomPassword());
                            break;
                        case "city":
                            json.put("city", generateRandomCity());
                            break;
                        case "country":
                            json.put("country", generateRandomCountry());
                            break;
                        case "maritalstatus":
                            json.put("maritalStatus", generateRandomMaritalStatus());
                            break;
                        default:
                            json.put(field, "N/A");
                    }
                }

                file.write(json.toString(4));
                if (i < count - 1) file.write(",\n");

            }
            file.write("\n]");
        }
        System.out.println("JSON data generated and saved to " + filePath);
    }
}