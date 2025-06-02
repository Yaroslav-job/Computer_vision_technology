package org.example;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.opencv.core.Mat;

public class ImageAPITest {

    @Test
    public void testOpenCVInitialization() {
        try {
            // Установка базового пути для загрузки библиотек
            System.setProperty("basePath", Paths.get("").toAbsolutePath().toString());
            
            // Проверка успешной инициализации OpenCV
            ImageAPI api = new ImageAPI();
            String os = api.getOperatingSystemType().name();
            String version = org.opencv.core.Core.getVersionString();

            System.out.println("OS version - " + os);
            System.out.println("Open CV version - " + version);

            // Проверка, что версия OpenCV не пустая
            assertNotNull(version, "OpenCV version should not be null");
            assertFalse(version.isEmpty(), "OpenCV version should not be empty");
        } catch (Exception e) {
            // Ошибка при инициализации OpenCV
            fail("OpenCV initialization failed: " + e.getMessage());
        }
    }

    @Test
    public void testImageProcessing() throws Exception {
        // Установка базового пути для загрузки библиотек
        System.setProperty("basePath", Paths.get("").toAbsolutePath().toString());
        ImageAPI api = new ImageAPI();
        String basePath = System.getProperty("basePath");
        
        // Пути для входного и выходного изображений
        String inputPath = Paths.get(basePath, "Images/image1.jpg").toAbsolutePath().toString();
        String outputPath = Paths.get(basePath, "Images/image2.jpg").toAbsolutePath().toString();

        // Загрузка изображения
        Mat image = api.loadImage(inputPath);
        assertNotNull(image, "Image should be loaded successfully");

        // Отображение изображения
        api.showImage(image);
        Thread.sleep(3000); // Задержка 3 секунды

        // Обнуление первого канала изображения
        api.zeroChannel(image, 0);
        api.showImage(image);
        Thread.sleep(3000); // Задержка 3 секунды

        // Сохранение изображения и проверка успешной загрузки сохраненного файла
        api.saveImage(outputPath, image);
        Mat savedImage = api.loadImage(outputPath);
        assertNotNull(savedImage, "Saved image should be loaded successfully");
    }
}
