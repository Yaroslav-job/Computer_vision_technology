package org.example;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();

    static {
        try {
            // Загрузка файла конфигурации из ресурсов
            try (var input = Config.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new IOException("config.properties not found in resources!");
                }
                properties.load(input);
            }
        } catch (IOException e) {
            // Обработка ошибок при загрузке конфигурации
            e.printStackTrace();
        }
    }

    // Получение значения свойства по ключу
    public static String getProp(String key) {
        return properties.getProperty(key);
    }
}

