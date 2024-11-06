package RandomDataGenerator.generator;

import RandomDataGenerator.generator.generator.CSVDataGenerator;
import RandomDataGenerator.generator.generator.JsonDataGenerator;
import RandomDataGenerator.generator.generator.RandomDataGenerator;
import RandomDataGenerator.generator.generator.XmlDataGenerator;
import RandomDataGenerator.generator.config.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class GeneratorApplication {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		String format;
		while (true) {
			System.out.print("Введите формат (" + String.join(", ", Config.getOutputFormats()) + "): ");
			format = scanner.nextLine().trim().toLowerCase();
			if (Config.getProperty("output.formats", "json,xml,csv").contains(format)) {
				break;
			} else {
				System.out.println("Неподдерживаемый формат. Пожалуйста, введите один из: json, xml, csv.");
			}
		}

		int count;
		while (true) {
			System.out.print("Введите количество записей (от 1 до 500): ");
			String countInput = scanner.nextLine().trim();
			try {
				count = Integer.parseInt(countInput);
				if (count >= 1 && count <= 500) {
					break;
				} else {
					System.out.println("Пожалуйста, введите число от 1 до 500.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Некорректный ввод. Пожалуйста, введите целое число.");
			}
		}

		List<String> fields = new ArrayList<>();
		System.out.println("Выберите поля для генерации:");
		System.out.println("Доступные поля: name, surname, email, age, password, city, country, maritalstatus");
		System.out.println("Введите 'done' для завершения.");

		while (true) {
			System.out.print("Поле: ");
			String field = scanner.nextLine().trim().toLowerCase();
			if ("done".equals(field)) {
				break;
			} else if (!field.isEmpty() && !fields.contains(field)) {
				fields.add(field);
			} else {
				System.out.println("Поле уже добавлено или введено пустое значение. Попробуйте снова.");
			}
		}

		scanner.close();

		try {
			RandomDataGenerator generator;

			if ("json".equalsIgnoreCase(format)) {
				generator = new JsonDataGenerator(fields);
			} else if ("xml".equalsIgnoreCase(format)) {
				generator = new XmlDataGenerator(fields);
			} else if ("csv".equalsIgnoreCase(format)) {
				generator = new CSVDataGenerator(fields);
			} else {
				throw new IllegalArgumentException("Unsupported format: " + format);
			}

			String folderPath = Config.getOutputDirectory();
			File folder = new File(folderPath);
			if (!folder.exists()) {
				folder.mkdir();
			}

			String uniqueFileName = folderPath + "/" + format + "_" + UUID.randomUUID().toString() + "." + format;
			generator.generateData(count, uniqueFileName);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
