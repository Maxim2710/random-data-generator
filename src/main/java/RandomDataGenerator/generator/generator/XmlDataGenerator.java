package RandomDataGenerator.generator.generator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class XmlDataGenerator implements RandomDataGenerator {
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


    public XmlDataGenerator(List<String> fields) {
        this.fields = fields;
    }

    String generateRandomName() {
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

    int generateRandomAge() {
        return new Random().nextInt(100);
    }

    String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        return password.toString();
    }

    String generateRandomCity() {
        Random random = new Random();
        return CITIES[random.nextInt(CITIES.length)];
    }

    String generateRandomCountry() {
        Random random = new Random();
        return COUNTRIES[random.nextInt(COUNTRIES.length)];
    }

    String generateRandomMaritalStatus() {
        Random random = new Random();
        return MARITAL_STATUSES[random.nextInt(MARITAL_STATUSES.length)];
    }

    @Override
    public void generateData(int count, String filePath) throws IOException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("People");
            doc.appendChild(root);

            for (int i = 0; i < count; i++) {
                Element person = doc.createElement("Person");
                root.appendChild(person);

                for (String field : fields) {
                    Element element = doc.createElement(field);
                    switch (field) {
                        case "name":
                            element.appendChild(doc.createTextNode(generateRandomName()));
                            break;
                        case "surname":
                            element.appendChild(doc.createTextNode(generateRandomName().split(" ")[1]));
                            break;
                        case "email":
                            element.appendChild(doc.createTextNode(generateRandomEmail(i + 1)));
                            break;
                        case "age":
                            element.appendChild(doc.createTextNode(String.valueOf(generateRandomAge())));
                            break;
                        case "password":
                            element.appendChild(doc.createTextNode(generateRandomPassword()));
                            break;
                        case "city":
                            element.appendChild(doc.createTextNode(generateRandomCity()));
                            break;
                        case "country":
                            element.appendChild(doc.createTextNode(generateRandomCountry()));
                            break;
                        case "maritalstatus":
                            element.appendChild(doc.createTextNode(generateRandomMaritalStatus()));
                            break;
                        default:
                            element.appendChild(doc.createTextNode("N/A"));
                    }
                    person.appendChild(element);
                }
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));
            transformer.transform(source, result);

            System.out.println("XML data generated and saved to " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}