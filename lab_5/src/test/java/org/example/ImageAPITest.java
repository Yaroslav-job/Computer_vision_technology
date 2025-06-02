package org.example;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.opencv.core.Point;
import org.opencv.core.Scalar;

public class ImageAPITest {

    @Test
    public void testOpenCVInitialization() {
        try {
            // Проверка успешной инициализации OpenCV
            ImageAPI api = new ImageAPI();
            String os = api.getOperatingSystemType().name();
            String version = org.opencv.core.Core.getVersionString();

            System.out.println("OS version - " + os);
            System.out.println("Open CV version - " + version);

            // Проверка, что версия OpenCV не null и не пустая
            assertNotNull(version, "OpenCV version should not be null");
            assertFalse(version.isEmpty(), "OpenCV version should not be empty");
        } catch (Exception e) {
            fail("OpenCV initialization failed: " + e.getMessage());
        }
    }

    @Test
    public void testFloodFill() throws Exception {
        // Тестирование операции заливки области изображения
        ImageAPI api = new ImageAPI();
        api.floodFillImage(Paths.get("Images/1/1.jpg").toAbsolutePath().toString(), new Point(0, 0), new Scalar(0, 255, 0), null, null);
        // Проверить результат вручную
    }

    @Test
    public void testPyramidOperations() throws Exception {
        // Тестирование пирамидальных операций
        ImageAPI api = new ImageAPI();
        api.pyramidOperations(Paths.get("Images/1/1.jpg").toAbsolutePath().toString(), 2, true); // Понижение
        api.pyramidOperations(Paths.get("Images/1/1.jpg").toAbsolutePath().toString(), 2, false); // Повышение
        // Проверить результат вручную
    }

    @Test
    public void testIdentifyRectangles() throws Exception {
        // Тестирование идентификации прямоугольников
        ImageAPI api = new ImageAPI();
        api.identifyRectangles(Paths.get("Images/1/1.jpg").toAbsolutePath().toString(), 38, 47);
        // Проверить результат вручную
    }
}
