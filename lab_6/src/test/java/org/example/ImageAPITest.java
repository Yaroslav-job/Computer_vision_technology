package org.example;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageAPITest {

    private static final String BASE_PATH = Paths.get("Images").toAbsolutePath().toString() + "/";

    @Test
    public void testOpenCVInitialization() {
        try {
            ImageAPI api = new ImageAPI(); // Инициализация API
            String os = api.getOperatingSystemType().name(); // Получение типа ОС
            String version = org.opencv.core.Core.getVersionString(); // Получение версии OpenCV

            System.out.println("OS version - " + os);
            System.out.println("Open CV version - " + version);

            assertNotNull(version, "OpenCV version should not be null"); // Проверка на null
            assertFalse(version.isEmpty(), "OpenCV version should not be empty"); // Проверка на пустую строку
        } catch (Exception e) {
            fail("OpenCV initialization failed: " + e.getMessage()); // Ошибка при инициализации
        }
    }

    @Test
    public void testDetectEdges() throws Exception {
        ImageAPI api = new ImageAPI();
        Mat edges = api.detectEdges(BASE_PATH + "image.jpg", 50, 150); // Обнаружение границ
        assertNotNull(edges, "Edges should not be null"); // Проверка результата
        Imgcodecs.imwrite(BASE_PATH + "output_edges.jpg", edges); // Сохранение результата
    }

    @Test
    public void testPyramidOperations() throws Exception {
        ImageAPI api = new ImageAPI();
        Mat srcImage = Imgcodecs.imread(BASE_PATH + "image.jpg"); // Загрузка изображения
        Mat downImage = api.pyramidDown(srcImage, 2); // Уменьшение изображения
        Mat upImage = api.pyramidUp(downImage, 2); // Увеличение изображения

        assertNotNull(downImage, "Downscaled image should not be null"); // Проверка результата
        assertNotNull(upImage, "Upscaled image should not be null"); // Проверка результата

        Imgcodecs.imwrite(BASE_PATH + "output_down.jpg", downImage); // Сохранение уменьшенного изображения
        Imgcodecs.imwrite(BASE_PATH + "output_up.jpg", upImage); // Сохранение увеличенного изображения
    }

    @Test
    public void testCountRectangles() throws Exception {
        ImageAPI api = new ImageAPI();
        int count = api.countRectangles(BASE_PATH + "image.jpg", 50, 50); // Подсчет прямоугольников
        System.out.println("Found rectangles: " + count);
        assertTrue(count >= 0, "Rectangle count should be non-negative"); // Проверка результата
    }
}
