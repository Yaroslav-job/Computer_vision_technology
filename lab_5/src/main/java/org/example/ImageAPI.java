package org.example;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageAPI {

    // Логгер для записи информации о выполнении программы
    private static final Logger log = LoggerFactory.getLogger(ImageAPI.class);

    // Перечисление для определения типа операционной системы
    public enum OSType {
        WINDOWS, MACOS, LINUX, OTHER
    }

    // Метод для определения типа операционной системы
    public OSType getOperatingSystemType() {
        // Получение имени ОС и преобразование его в нижний регистр
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);
        // Проверка на соответствие известным типам ОС
        if ((os.contains("mac")) || (os.contains("darwin"))) {
            return OSType.MACOS;
        } else if (os.contains("win")) {
            return OSType.WINDOWS;
        } else if (os.contains("nux")) {
            return OSType.LINUX;
        } else {
            return OSType.OTHER;
        }
    }

    // Конструктор, загружающий библиотеку OpenCV в зависимости от ОС
    public ImageAPI() throws Exception {
        OSType osType = getOperatingSystemType();
        String nativeLibraryPath;
        log.info("Checking OS.....");
        // Выбор пути к библиотеке на основе типа ОС
        switch (osType) {
            case WINDOWS -> nativeLibraryPath = Config.getProp("pathToNativeLibWin");
            case MACOS -> nativeLibraryPath = Config.getProp("pathToNativeLibMac");
            case LINUX -> nativeLibraryPath = Config.getProp("pathToNativeLibLinux");
            default -> throw new Exception("Unsupported operating system");
        }

        // Проверка на наличие пути к библиотеке
        if (nativeLibraryPath == null || nativeLibraryPath.isEmpty()) {
            throw new Exception("Native library path not found in configuration for OS: " + osType);
        }

        // Преобразование относительного пути в абсолютный
        nativeLibraryPath = Paths.get(nativeLibraryPath).toAbsolutePath().toString();
        System.load(nativeLibraryPath); // Загрузка библиотеки
        log.info("OpenCV library loaded successfully");
    }

    // Метод для выполнения операции заливки области изображения
    public void floodFillImage(String imagePath, Point seedPoint, Scalar newVal, Scalar loDiff, Scalar upDiff) {
        Mat srcImage = Imgcodecs.imread(Paths.get(imagePath).toAbsolutePath().toString());
        Mat mask = new Mat();
        // Если параметры loDiff и upDiff не заданы, генерируем случайные значения
        if (loDiff == null || upDiff == null) {
            Random random = new Random();
            loDiff = new Scalar(random.nextInt(50), random.nextInt(50), random.nextInt(50));
            upDiff = new Scalar(random.nextInt(50), random.nextInt(50), random.nextInt(50));
        }
        // Выполнение операции заливки
        Imgproc.floodFill(srcImage, mask, seedPoint, newVal, new Rect(), loDiff, upDiff, Imgproc.FLOODFILL_FIXED_RANGE);
        // Сохранение результата
        Imgcodecs.imwrite(Paths.get("Images/1/floodFillResult.jpg").toAbsolutePath().toString(), srcImage);
    }

    // Метод для выполнения операций пирамидального преобразования изображения
    public void pyramidOperations(String imagePath, int levels, boolean isDown) {
        Mat srcImage = Imgcodecs.imread(Paths.get(imagePath).toAbsolutePath().toString());
        Mat result = srcImage.clone();
        // Выполнение операций понижения или повышения разрешения
        for (int i = 0; i < levels; i++) {
            if (isDown) {
                Imgproc.pyrDown(result, result);
            } else {
                Imgproc.pyrUp(result, result);
            }
        }
        // Сохранение результата
        Imgcodecs.imwrite(Paths.get(isDown ? "Images/1/pyrDownResult.jpg" : "Images/1/pyrUpResult.jpg").toAbsolutePath().toString(), result);
    }

    // Метод для идентификации прямоугольников на изображении
    public void identifyRectangles(String imagePath, int width, int height) {
        Mat srcImage = Imgcodecs.imread(Paths.get(imagePath).toAbsolutePath().toString());
        Mat grayImage = new Mat();
        // Преобразование изображения в оттенки серого
        Imgproc.cvtColor(srcImage, grayImage, Imgproc.COLOR_BGR2GRAY);
        Mat thresholdImage = new Mat();
        // Применение порогового преобразования
        Imgproc.threshold(grayImage, thresholdImage, 50, 255, Imgproc.THRESH_BINARY);
        ArrayList<MatOfPoint> contours = new ArrayList<>();
        // Поиск контуров
        Imgproc.findContours(thresholdImage, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        // Сортировка контуров по площади
        contours.sort(Collections.reverseOrder(Comparator.comparing(Imgproc::contourArea)));

        int count = 0;
        for (MatOfPoint contour : contours) {
            Rect rect = Imgproc.boundingRect(contour);
            // Вывод информации о найденных прямоугольниках
            System.out.println("Found rectangle: width=" + rect.width + ", height=" + rect.height);
            if (rect.width == width && rect.height == height) {
                count++;
            }
        }
        // Вывод количества прямоугольников заданного размера
        System.out.println("Found " + count + " rectangles of size " + width + "x" + height);
        // Сохранение результата
        Imgcodecs.imwrite(Paths.get("Images/1/rectanglesResult.jpg").toAbsolutePath().toString(), srcImage);
    }
}