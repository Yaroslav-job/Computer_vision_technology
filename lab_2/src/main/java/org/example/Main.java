package org.example;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            // Установка базового пути для загрузки библиотек
            String basePath = Paths.get("").toAbsolutePath().toString();
            System.setProperty("basePath", basePath);

            // Создание экземпляра API для работы с изображениями
            ImageAPI api = new ImageAPI();
            System.out.println("OpenCV successfully initialized!");
        } catch (Exception e) {
            // Обработка ошибок при инициализации
            e.printStackTrace();
        }
    }
}




