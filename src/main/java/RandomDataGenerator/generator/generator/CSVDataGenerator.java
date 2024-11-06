package RandomDataGenerator.generator.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class CSVDataGenerator implements RandomDataGenerator {
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

    public CSVDataGenerator(List<String> fields) {
        this.fields = fields;
    }

    String generateRandomName() {
        Random random = new Random();
        String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
        return firstName + " " + lastName;
    }

    private String generateRandomEmail(int index) {
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
            file.write(String.join(",", fields) + "\n");

            for (int i = 0; i < count; i++) {
                StringBuilder sb = new StringBuilder();
                for (String field : fields) {
                    switch (field) {
                        case "name":
                            sb.append(generateRandomName()).append(",");
                            break;
                        case "surname":
                            sb.append(generateRandomName().split(" ")[1]).append(",");
                            break;
                        case "email":
                            sb.append(generateRandomEmail(i + 1)).append(",");
                            break;
                        case "age":
                            sb.append(generateRandomAge()).append(",");
                            break;
                        case "password":
                            sb.append(generateRandomPassword()).append(",");
                            break;
                        case "city":
                            sb.append(generateRandomCity()).append(",");
                            break;
                        case "country":
                            sb.append(generateRandomCountry()).append(",");
                            break;
                        case "maritalstatus":
                            sb.append(generateRandomMaritalStatus()).append(",");
                            break;
                        default:
                            sb.append("N/A,");
                            break;
                    }
                }

                sb.deleteCharAt(sb.length() - 1);
                sb.append("\n");
                file.write(sb.toString());
            }
        }

        System.out.println("CSV data generated and saved to " + filePath);
    }
}
