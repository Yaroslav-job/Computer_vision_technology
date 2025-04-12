package org.example;


public class Main {
    public static void main(String[] args) {
        try {
            // Создаем экземпляр вашего API
            YourApi api = new YourApi();
            System.out.println("OpenCV successfully initialized!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}




