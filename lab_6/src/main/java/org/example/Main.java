package org.example;

public class Main {
    public static void main(String[] args) {
        try {
            // Создаем экземпляр вашего API
            ImageAPI api = new ImageAPI(); // Инициализация API
            System.out.println("OpenCV successfully initialized!"); // Вывод успешной инициализации
        } catch (Exception e) {
            e.printStackTrace(); // Вывод ошибки
        }
    }
}




