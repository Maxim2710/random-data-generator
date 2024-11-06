package RandomDataGenerator.generator.generator;

import java.io.IOException;

public interface RandomDataGenerator {
    void generateData(int count, String filePath) throws IOException;
}
