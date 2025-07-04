package org.example;

import java.io.IOException;
import java.util.Properties;

public class Config {
    // Хранилище свойств конфигурации
    private static Properties properties = new Properties();

    static {
        try {
            // Загрузка ресурса из classpath
            try (var input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new IOException("config.properties not found in resources!");
                }
                properties.load(input); // Загрузка свойств из файла
            }
        } catch (IOException e) {
            e.printStackTrace(); // Вывод ошибки при загрузке конфигурации
        }
    }

    // Метод для получения значения по ключу
    public static String getProp(String key) {
        return properties.getProperty(key);
    }
}

